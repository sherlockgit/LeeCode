package com.sherlock.easy;

import java.util.*;
import java.util.concurrent.*;

/**
 * @Description:
 * @Author: linmuyu
 * @Date: 2022/2/24 15:28
 */
public class test {


    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        List<Integer> list = new ArrayList<>();
//        for (int i = 1;i <= 50;i++){
//            list.add(i);
//        }
//        ExecutorService zyEquipmentAddExecutor = new ThreadPoolExecutor(10,20,1, TimeUnit.MILLISECONDS,new ArrayBlockingQueue<Runnable>(20));
//        for (Integer i : list){
//            zyEquipmentAddExecutor.submit(new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println(i);
//                    try {
//                        Thread.sleep(10000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        }
//        zyEquipmentAddExecutor.isShutdown();
//        System.out.println("OK");

        Old old = new Old(0,null,"a");
        Old old1 = new Old(1,0,"a");
        Old old2 = new Old(2,1,"a");
        Old old3 = new Old(3,2,"a");
        List<Old> list = new ArrayList<>();
        list.add(old);
        list.add(old1);
        list.add(old2);
        list.add(old3);
        Map<Integer,Node> nodeMap = new HashMap<>();
        Node endNode;
        for (Old o : list){
            if (nodeMap.get(o.pid) == null) {
                endNode = new Node(o);
                nodeMap.put(o.id,endNode);
            }else {
                endNode = new Node(o);
                nodeMap.get(o.pid).chi = endNode;
            }
        }



    }

}

class Node  {
    Integer id;
    Integer pid;
    String name;
    Node chi;
    Node(Old old){
        this.id = old.id;
        this.pid = old.pid;;
        this.name = old.name;;
    }

}

class Old  {
    Integer id;
    Integer pid;
    String name;
    Old(Integer id,Integer pid,String name){
        this.id = id;
        this.pid = pid;
        this.name = name;
    }
}


