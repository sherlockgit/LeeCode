package com.sherlock.easy;

import com.sherlock.utils.ListNode;

/**
 * @Description:
 * @Author: linmuyu
 * @Date: 2022/8/11 14:35
 */
public class ReverseList {

    public static ListNode result;

    public static ListNode temp;

    public static void main(String[] args) {
        ListNode node = createList();
        run(node);
        System.out.println(result);
    }

//    public static ListNode run(ListNode listNode,ListNode top){
//        if (listNode == null || listNode.next == null) {
//            return top;
//        }
//        ListNode temp = listNode.next;
//        listNode.next = temp.next;
//        temp.next = top;
//        top = temp;
//        return run(listNode,top);
//    }

    public static ListNode run(ListNode listNode){
        if (listNode == null) {
            return null;
        }
        ListNode node = run(listNode.next);
        if (node == null) {
            result = new ListNode(listNode.val);
            temp = result;
            return result;
        }else {
            temp.next = new ListNode(listNode.val);
            temp = temp.next;
            return temp;
        }
    }

    static ListNode createList(){
        ListNode l1 = new ListNode(1,new ListNode(2,new ListNode(3)));
        return l1;
    }

}
