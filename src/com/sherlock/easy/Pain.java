//将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
//
//
//
// 示例 1：
//
//
//输入：l1 = [1,2,4], l2 = [1,3,4]
//输出：[1,1,2,3,4,4]
//
//
// 示例 2：
//
//
//输入：l1 = [], l2 = []
//输出：[]
//
//
// 示例 3：
//
//
//输入：l1 = [], l2 = [0]
//输出：[0]
//
//
//
//
// 提示：
//
//
// 两个链表的节点数目范围是 [0, 50]
// -100 <= Node.val <= 100
// l1 和 l2 均按 非递减顺序 排列
//
// Related Topics 递归 链表
// 👍 2116 👎 0

package com.sherlock.easy;

import com.sherlock.utils.ListNode;

/**
 * @Description:
 * @Author: linmuyu
 * @Date: 2021/12/31 15:36
 */
public class Pain {

    public static ListNode add(ListNode list1, ListNode list2,ListNode result){
        if (list1 == null && list2 == null) {
            return result;
        }
        if (list1 == null) {
            result.next = list2;
            return add(null,list2.next,result.next);
        }
        if (list2 == null) {
            result.next = list1;
            return add(list1.next,null,result.next);
        }

        if (list1.val >= list2.val) {
            result.next = list2;
            return add(list1,list2.next,result.next);
        }else {
            result.next = list1;
            return add(list1.next,list2,result.next);
        }
    }

    public static ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode result = new ListNode();
        add(list1,list2,result);
        return result.next;
    }

    public static void main(String[] args) {


        ListNode var1 = new ListNode(1);
        ListNode var2 = new ListNode(2);
        ListNode list1 = var1;
        ListNode list2 = var2;
        int i = 3;
        while (i > 0){
            var1.next = new ListNode(var1.val+1);
            var2.next = new ListNode(var2.val+1);
            var1 = var1.next;
            var2 = var2.next;
            i--;
        }
        ListNode result = mergeTwoLists(list1,list2);
        while (result != null){
            System.out.println(result.val);
            result = result.next;
        }
    }


}


