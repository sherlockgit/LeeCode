package com.sherlock.utils;
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
//        callableAndFutureDemo();
//        executorCompletionServiceDemo();
//        interruptDemo();
//        timeRunTwoDemo();
        timeRunOneDemo();

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

    /**
     * 外部线程安排中断（不要这么做）
     * @throws Throwable
     */
    public static void timeRunOneDemo() throws Throwable {
        timeRunOne(new Runnable() {
            @Override
            public void run() {
                System.out.println("ok");
            }
        },10,TimeUnit.SECONDS);
    }

    public static void timeRunTwoDemo() throws Throwable {
        timeRunTwo(new Runnable() {
            @Override
            public void run() {
                System.out.println("ok");
            }
        },10,TimeUnit.SECONDS);
    }



    public static void timeRunOne(final Runnable r,long time,TimeUnit timeUnit) throws InterruptedException {
        final Thread thread = Thread.currentThread();
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
        exec.schedule(new Runnable() {
            @Override
            public void run() {
                thread.interrupt();
            }
        },time,timeUnit);
        Thread.sleep(15000);
        r.run();
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



}


