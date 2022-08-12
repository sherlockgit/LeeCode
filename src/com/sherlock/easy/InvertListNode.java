package com.sherlock.easy;

import com.sherlock.utils.ListNode;

/**
 * @Description:
 * @Author: linmuyu
 * @Date: 2022/8/2 9:13
 */
public class InvertListNode {
    public static void main(String[] args) {
        ListNode node = null;
        ListNode result = run(createList(),node);
        System.out.println(node);
    }

    public static ListNode run(ListNode listNode,ListNode result){
        if (listNode == null) {
            return null;
        }
        ListNode node = run(listNode.next,result);
        if (node == null) {
            return listNode;
        }else {
            result = new ListNode(listNode.val);
            result.next = node;
            return result;
        }
    }

    static ListNode createList(){
        ListNode l1 = new ListNode(1,new ListNode(2,new ListNode(3)));
        return l1;
    }
}
