package com.sherlock.easy;

/**
 * @Description:
 * @Author: linmuyu
 * @Date: 2021/12/29 14:30
 */
public class Throat {

    public static boolean isPalindrome(int x) {
        if (x < 0) return false;
        String s = String.valueOf(x);
        for (int i = 0; i < s.length()/2 ; i++){
            if (s.charAt(i) != s.charAt(s.length()-i-1)) {
                return false;
            }
        }
        return true;

//        if(x<0)
//            return false;
//        int rem=0,y=0;
//        int quo=x;
//        System.out.println("rem:"+rem+" y:"+y+" quo:"+quo);
//        while(quo!=0){
//            rem=quo%10;
//            y=y*10+rem;
//            quo=quo/10;
//            System.out.println("rem:"+rem+" y:"+y+" quo:"+quo);
//        }
//        return y==x;
    }

    public static void main(String[] args) {
        System.out.println(isPalindrome(12321));
    }
}
