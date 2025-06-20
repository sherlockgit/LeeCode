package com.sherlock.source;

import java.util.HashMap;

/**
 * @Description:
 * @Author: linmuyu
 * @Date: 2025/6/20 16:54
 */
public class HashMapSource {

    public static void main(String[] args) {
        HashMap<Integer,Integer> map = new HashMap<>();
        map.put(1,10);
        map.put(2,10);
        Integer a = 0;


        /*
        hashmap中的数组槽位计算 java.util.HashMap.hash
        (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
        本质是通过将高 16 位信息混合到低 16 位，解决小表长场景下的哈希冲突问题。
        */
        //比如key为“demo”
        String key = "demo";
        //“demo”的hash值
        System.out.println(key.hashCode());
        //“demo”hash值的二进制表示
        System.out.println(Integer.toBinaryString(key.hashCode()));
        //无符号右移运算（将操作数的二进制表示向右移动16位，左侧空位永远补0）
        System.out.println("0000000000000000"+Integer.toBinaryString(key.hashCode() >>> 16));
        //无符号右移运算实际结果与“demo”的hash值进行异或处理（相同为0不同为1）
        /* “^”规则：对应位相同为 0，不同为 1
        0 ^ 0 = 0
        0 ^ 1 = 1
        1 ^ 0 = 1
        1 ^ 1 = 0
        */
        System.out.println(Integer.toBinaryString((key.hashCode()) ^ (key.hashCode() >>> 16)));
        //十进制结果
        System.out.println((key.hashCode()) ^ (key.hashCode() >>> 16));
        int hash = (key.hashCode()) ^ (key.hashCode() >>> 16);
        //(n - 1) & hash 最终槽位，n为数组的长度，本次为16（可以简单理解为取hash二进制的最后4位）
        /* "&"两个操作数的对应位都为 1 时，结果位才为 1；否则为 0
        * 0 & 0 = 0
        * 0 & 1 = 0
        * 1 & 0 = 0
        * 1 & 1 = 1
        * * */
        System.out.println((16 - 1) & hash);


        /*问：假如有一个hashmap（初始16个桶），用户随机存入的key是随机的，
        那么key对应的hash码也应该是随机的（即使不同的key可能对应相同的hash码）。
        那么在这种情况下，key对应的hash码的低位也应该是随机的啊。*/

        /*答:哈希码不是真正的随机*/

    }

}
