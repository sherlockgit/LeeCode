package com.sherlock.easy;

import com.sherlock.utils.ListNode;

/**
 * @Description:
 * @Author: linmuyu
 * @Date: 2022/8/2 9:13
 */
public class InvertListNode {
    public static void main(String[] args) {
        ListNode listNode = run(createList());
        System.out.println(listNode);
    }

    public static ListNode run(ListNode listNode){
        if (listNode == null) {
            return listNode;
        }
        run(listNode.next);
        return null;
    }

    static ListNode createList(){
        ListNode l1 = new ListNode(1,new ListNode(2,new ListNode(3)));
        return l1;
    }
}
