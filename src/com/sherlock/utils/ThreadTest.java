package com.sherlock.utils;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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

    public static void main(String[] args) throws Throwable {
//        callableAndFutureDemo();//callableAndFuture
//        executorCompletionServiceDemo();//executorCompletionService-一个可获取结果的献唱队列
//        interruptDemo();//中断
//        timeRunOneDemo();//中断——不可捕获任务执行过程中的异常
//        timeRunTwoDemo();//中断——可捕获任务执行过程中的异常，但有join限制
//        timeRunThreeDemo();//中断--可捕获任务执行过程中的异常
//        getNextTaskDemo();
        ReaderThreadDemo();//通过改写interrupt方法将非标准的取消操作封装在Thread中

    }

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

//    public void test(){
//        FutureTask
//        RunnableFuture
//    }

}


