package com.sherlock.utils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @Description:
 * @Author: linmuyu
 * @Date: 2022/10/15 11:36
 */
public class ThreadTest {
    public static final String TYPE_MD5 = "md5";
    public static final String TYPE_SHA1 = "sha1";

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        callableAndFutureDemo();
        executorCompletionServiceDemo();
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
    }



}


