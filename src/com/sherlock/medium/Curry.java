package com.sherlock.medium;

//给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
//
// 请你将两个数相加，并以相同形式返回一个表示和的链表。
//
// 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
//
//
//
// 示例 1：
//
//
//输入：l1 = [2,4,3], l2 = [5,6,4]
//输出：[7,0,8]
//解释：342 + 465 = 807.
//
//
// 示例 2：
//
//
//输入：l1 = [0], l2 = [0]
//输出：[0]
//
//
// 示例 3：
//
//
//输入：l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
//输出：[8,9,9,9,0,0,0,1]
//
//
//
//
// 提示：
//
//
// 每个链表中的节点数在范围 [1, 100] 内
// 0 <= Node.val <= 9
// 题目数据保证列表表示的数字不含前导零
//
// Related Topics 递归 链表 数学

import com.sherlock.utils.ListNode;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @Description: 两数相加
 * @Author: linmuyu
 * @Date: 2021/11/3 10:44
 */
public class Curry {


    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode l3 = new ListNode();
        ListNode var = l3;
        int v = 0;
        while (l1 != null || l2 != null || v != 0){

            int l1v = l1 == null ? 0 : l1.val;
            int l2v = l2 == null ? 0 : l2.val;
            int sum = l2v+l1v+v;
            v = sum/10;
            l3.val = sum%10;

            boolean hasNext = false;
            if (l1 != null && l1.next != null) {
                l1 = l1.next;
                hasNext = true;
            }else {
                l1 = null;
            }
            if (l2 != null && l2.next != null) {
                l2 = l2.next;
                hasNext = true;
            }else {
                l2 = null;
            }
            if (hasNext || v != 0) {
                l3.next = new ListNode();
                l3 = l3.next;
            }
        }
        return var;
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(2,new ListNode(4,new ListNode(3)));
        ListNode l2 = new ListNode(5,new ListNode(6,new ListNode(4)));
        ListNode l3 = addTwoNumbers(l1,l2);
        do {
            System.out.println(l3.val);
            l3 = l3.next;
        }while (l3 != null);
    }
}
