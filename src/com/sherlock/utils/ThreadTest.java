package com.sherlock.utils;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.math.BigInteger;
import java.net.Socket;
import java.util.concurrent.*;


/**
 * @Description:
 * @Author: linmuyu
 * @Date: 2022/10/15 11:36
 */
public class ThreadTest {
    public static final String TYPE_MD5 = "md5";
    public static final String TYPE_SHA1 = "sha1";

    /**
     * callable
     * Future
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void callableAndFutureDemo() throws ExecutionException, InterruptedException {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        Future<String> future = exec.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(10000);
//                throw new RuntimeException("error");
                return "ok";
            }
        });

//        future.cancel(true); //取消任务
        System.out.println(future.get());
    }

    /**
     * ExecutorService
     * ExecutorCompletionService
     * BlockingQueue
     * poll()
     * take()
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public static void executorCompletionServiceDemo() throws InterruptedException, ExecutionException {
        ExecutorService exce = Executors.newSingleThreadExecutor();
        CompletionService<String> completionService = new ExecutorCompletionService<String>(exce);
        for(int i = 0; i<3; i++){

            completionService.submit(new Callable() {
                @Override
                public Object call() throws Exception {
                    Thread.sleep(2000);
                    return "OK:"+Thread.currentThread();
                }
            });
        }
        Thread.sleep(1000);
        for (int i = 0; i<3; i++){
//            System.out.println(completionService.take().get());
//            System.out.println(completionService.take().get());//会阻塞
            System.out.println(completionService.poll().get());//不会阻塞，线程未完成则抛出异常
        }
        Thread.currentThread().interrupt();
    }

    /**
     * interrupt//线程中断
     */
    public static void interruptDemo(){
        ExecutorService exec = Executors.newSingleThreadExecutor();
        exec.execute(new Runnable() {
            @Override
            public void run() {
                Thread.currentThread().interrupt();
                while (true){
                    try {
                        System.out.println(Thread.currentThread().isInterrupted());
                        Thread.sleep(10000);
                        Thread.interrupted();
                    } catch (InterruptedException e) {
                        System.out.println("线程中断");
                    }
                }
            }
        });
    }

    /***********************************取消，中断****************************************/

    /**
     * 外部线程安排中断（不要这么做,虽然能捕获异常）
     * @throws Throwable
     */
    public static void timeRunOneDemo() throws Throwable {
        timeRunOne(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (true){
                    System.out.println(i++);
                }
            }
        },1, TimeUnit.SECONDS);
    }

    public static void timeRunOne(final Runnable r,long time,TimeUnit timeUnit) {
        final Thread thread = Thread.currentThread();
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
        exec.schedule(new Runnable() {
            @Override
            public void run() {
                thread.interrupt();
            }
        },time,timeUnit);
        r.run();
    }

    /**
     * 通过join方法来做支持 确定是抛出异常时不知道是不算join超时抛出
     * @throws Throwable
     */
    public static void timeRunTwoDemo() throws Throwable {
        timeRunTwo(new Runnable() {
            @Override
            public void run() {
                System.out.println("ok");
            }
        },10, TimeUnit.SECONDS);
    }

    public static void timeRunTwo(final Runnable r,long time,TimeUnit timeUnit) throws Throwable {

        class RethrowableTask implements Runnable{
            private volatile Throwable t;
            @Override
            public void run() {
                try {
                    r.run();
                }catch (Throwable t){
                    this.t =t ;
                }
            }
            void rethrow() throws Throwable {
                if (t != null) {
                    throw t;
                }
            }
        }

        RethrowableTask task = new RethrowableTask();
        final Thread taskThread = new Thread(task);
        taskThread.start();
        ScheduledExecutorService cancelExec = Executors.newScheduledThreadPool(1);
        cancelExec.schedule(new Runnable() {
            @Override
            public void run() {
                taskThread.interrupt();
            }
        },time,timeUnit);
        taskThread.join(timeUnit.toMillis(time));
        task.rethrow();
    }

    /**
     * 正确的可获取异常的中断
     */
    public static void timeRunThreeDemo() throws InterruptedException {
        timeRunThree(new Callable() {
            @Override
            public String call() {
                System.out.println("ok");
//                    Thread.sleep(2000);
                throw new RuntimeException("Error test");

            }}, 1, TimeUnit.SECONDS);
    }

    public static void timeRunThree(Callable r,long timeout,TimeUnit timeUnit) throws InterruptedException{
        ExecutorService exec = Executors.newSingleThreadExecutor();
        Future future = exec.submit(r);
        try {
            Object o = future.get(timeout,timeUnit);
            System.out.println(o);
        } catch (TimeoutException e) {
            System.out.println("TimeoutException");
            e.printStackTrace();
        } catch (ExecutionException e) {
            System.out.println("ExecutionException");
            e.printStackTrace();
        }finally {
            System.out.println("cancel");
            future.cancel(true);
        }
    }

    /**
     * 任务的取消
     * @throws InterruptedException
     */
    public static void getNextTaskDemo() throws InterruptedException {

        class GetNextTaskTask implements Runnable{
            @Override
            public void run() {
                BlockingQueue queue = new ArrayBlockingQueue<String>(10);
                queue.add("1");
                queue.add("2");
                queue.add("3");
                System.out.println(getNextTask(queue));
                System.out.println(getNextTask(queue));
                System.out.println(getNextTask(queue));
                System.out.println(getNextTask(queue));
                System.out.println("OK");
            }

        }

        GetNextTaskTask task = new GetNextTaskTask();
        final Thread taskThread = new Thread(task);
        taskThread.start();
        taskThread.interrupt();
    }

    public static String getNextTask(BlockingQueue<String> queue){
        boolean interrupted = false;
        try {
            while (true){
                try {
                    return queue.take();
                }catch (InterruptedException e){
                    System.out.println("nterrupted:"+Thread.currentThread().isInterrupted());
                    interrupted = true;
                    queue.add("4");
                }
            }
        }finally {
            if (interrupted) {
                Thread.currentThread().interrupt();
                System.out.println("nterrupted:"+Thread.currentThread().isInterrupted());

            }
        }
    }


    /**
     * 通过改写interrupt方法将非标准的取消操作封装在Thread中
     */
    public static void ReaderThreadDemo() {
        class ReaderThread extends Thread {
            private final Socket socket;
            private final InputStream in;

            @Override
            public void interrupt() {
                try {
                    socket.close();
                } catch (IOException e) {
                } finally {
                    super.interrupt();
                }
            }

            @Override
            public void run() {
                try {
                    byte[] buf = new byte[2];
                    while (true) {
                        int count = in.read(buf);
                        if (count < 0) {
                            break;
                        }
                    }
                } catch (IOException io) {

                }

            }

            ReaderThread(Socket socket, InputStream in) {
                this.socket = socket;
                this.in = in;
            }
        }

        new ReaderThread(new Socket(), new ByteArrayInputStream(new byte[2])).run();

    }

    /**
     * 毒丸对象
     */
    public static void poisonPill() throws InterruptedException {


        class Posion {

            private final BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
            private final String poison = "poison";
            private final Consumer consumer = new Consumer();
            private final Producer producer = new Producer();


            public void cancel(){
                producer.interrupt();
            }

            public void start(){
                consumer.start();
                producer.start();
            }

            class Producer extends Thread{
                @Override
                public void run() {
                    int i = 1;
                    while (true) {
                        try {
                            Thread.sleep(1000);
                            queue.put(String.valueOf(i++));
                        } catch (InterruptedException e) {
                            System.out.println("Interrupt中断");
                            try {
                                queue.put(poison);//放入毒丸对象，消费者收到毒丸时退出线程
                                break;
                            } catch (InterruptedException er) {
                                e.printStackTrace();
                            }
                        }
                    }

                }
            }

            /**
             * 消费者
             */
            class Consumer extends Thread{
                @Override
                public void run() {
                    while (true){
                        try {
                            String value = queue.take();
                            System.out.println(value);
                            if (value.equals(poison)) {
                                break;
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }

        Posion posion = new Posion();
        posion.start();
        Thread.sleep(10000);
        posion.cancel();

    }

    /**
     * Runtime
     * @throws IOException
     * @throws InterruptedException
     */
    public static void runTime() throws IOException, InterruptedException {
        Process p = Runtime.getRuntime().exec("java -version");
        boolean res = p.waitFor(10, TimeUnit.SECONDS);
        if(!res) {
            System.out.println("Time out");
        }
        InputStream inputStream = p.getInputStream();
        byte[] data = new byte[1024];
        String result = "";
        while(inputStream.read(data) != -1) {
            result += new String(data,"GBK");
        }
        if (result == "") {
            InputStream errorStream = p.getErrorStream();
            while(errorStream.read(data) != -1) {
                result += new String(data,"GBK");
            }
        }
    }


    /*==================================线程池的使用=======================================*/

    /**
     * threadLocal
     * @throws InterruptedException
     */
    public static void threadLocal() throws InterruptedException {
        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        ExecutorService exec = Executors.newSingleThreadExecutor();
        exec.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(threadLocal.get());
                threadLocal.set("OK");
                System.out.println(threadLocal.get());
            }
        });
        Thread.sleep(1000);
        exec.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(threadLocal.get());//继承了上次任务的ThreadLocal副本
                threadLocal.remove();//使threadLocal的什么周期受限于任务的生命周期而不是线程的生命周期
                System.out.println(threadLocal.get());
            }
        });
        exec.shutdown();
    }

    /**
     * 饥饿死锁
     * 线程池队中的任务相互依赖，导致资源不足，会出现饥饿死锁
     */
    public static void ThreadDeadLock() {

        class Demo {
            ExecutorService exec = Executors.newSingleThreadExecutor();

            class Demo1 implements Callable<String>{

                @Override
                public String call() throws Exception {
                    System.out.println("step1");
                    Future<String> t1,t2;
                    t1 = exec.submit(new Callable<String>() {
                        @Override
                        public String call() throws Exception {
                            System.out.println("step2");
                            Thread.sleep(2000);
                            return "ok";
                        }
                    });
                    t2 = exec.submit(new Callable<String>() {
                        @Override
                        public String call() throws Exception {
                            System.out.println("step3");
                            Thread.sleep(3000);
                            return "ok";
                        }
                    });
                    return t1.get()+t2.get();
                }
            }
        }
        Demo demo = new Demo();
        demo.exec.submit(demo.new Demo1());
    }

    /**
     * 虚拟机可用的cpu核心数
     */
    public static void availableProcessors(){
        System.out.println(Runtime.getRuntime().availableProcessors());
    }

    public static void main(String[] args) throws Throwable {
//        callableAndFutureDemo();//callableAndFuture
//        executorCompletionServiceDemo();//executorCompletionService-一个可获取结果的献唱队列
//        interruptDemo();//中断
//        timeRunOneDemo();//中断——不可捕获任务执行过程中的异常
//        timeRunTwoDemo();//中断——可捕获任务执行过程中的异常，但有join限制
//        timeRunThreeDemo();//中断--可捕获任务执行过程中的异常
//        getNextTaskDemo();
//        ReaderThreadDemo();//通过改写interrupt方法将非标准的取消操作封装在Thread中
//        poisonPill();//毒丸对象
//        runTime();//runTime
//        threadLocal();//threadLocal
//        ThreadDeadLock();//饥饿死锁
//        availableProcessors();//虚拟机可用的cpu核心数
//        threadNum();//线程池数量设计——cpu核心数*cpu使用率*(1+任务等待时间/任务计算时间)
        test();
    }


    /**
     * 线程池数量设计——cpu核心数*cpu使用率*(1+任务等待时间/任务计算时间)
     */
    public static void threadNum(){
        ThreadMXBean tmbean = ManagementFactory.getThreadMXBean();
        tmbean.setThreadContentionMonitoringEnabled(true);
        tmbean.setThreadCpuTimeEnabled(true);
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 20 ; i++){
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    BigInteger bigInteger = new BigInteger("1");
                    for (int i = 0; i <= 100 ;i++){
                        bigInteger = bigInteger.nextProbablePrime();
                    }
                    System.out.println("ok");
                }
            });
        }
        try {
            Thread.currentThread().join(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long[] allThread = tmbean.getAllThreadIds();
        System.out.println(Thread.currentThread().getName());
        if (allThread.length > 0) {
            for (long threadId : allThread) {
                ThreadInfo info = tmbean.getThreadInfo(threadId);
                System.out.println("线程id:" + info.getThreadId() +
                        " ;线程名称:" + info.getThreadName() +
                        " ;线程状态:" + info.getThreadState() +
                        " ;cpu计算时间:" + tmbean.getThreadCpuTime(threadId) + //cpu时间
                        " ;等待时间:" + (info.getWaitedTime() //线程状态为WAITING or TIMED_WAITING的时间
                        + info.getBlockedTime())); //线程状态为BLOCKED时间
            }
        }
        exec.shutdown();
    }

    private static void threadPoolExecutor(){
        ExecutorService singleExecutor = Executors.newSingleThreadExecutor();
        Thread.UncaughtExceptionHandler;

    }

    public static void test() throws InterruptedException {

    }


}


