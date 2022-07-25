package com.sherlock.medium;

import com.sherlock.utils.TreeNode;

import java.util.LinkedList;
import java.util.Queue;
//给定一个二叉树
//
//
//struct Node {
//  int val;
//  Node *left;
//  Node *right;
//  Node *next;
//}
//
// 填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将 next 指针设置为 NULL。
//
// 初始状态下，所有 next 指针都被设置为 NULL。
//
//
//
// 进阶：
//
//
// 你只能使用常量级额外空间。
// 使用递归解题也符合要求，本题中递归程序占用的栈空间不算做额外的空间复杂度。
//
//
//
//
// 示例：
//
//
//
//
//输入：root = [1,2,3,4,5,null,7]
//输出：[1,#,2,3,#,4,5,7,#]
//解释：给定二叉树如图 A 所示，你的函数应该填充它的每个 next 指针，以指向其下一个右侧节点，如图 B 所示。序列化输出按层序遍历顺序（由 next 指
//针连接），'#' 表示每层的末尾。
//
//
//
// 提示：
//
//
// 树中的节点数小于 6000
// -100 <= node.val <= 100
//
//
//
//
//
//
// Related Topics 树 深度优先搜索 广度优先搜索 链表 二叉树 👍 610 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
/*
// Definition for a Node.
class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }
};
*/
/**
 * @Description:
 * @Author: linmuyu
 * @Date: 2022/7/25 15:28
 */
public class PaddingPointPlus {

    public static void main(String[] args) {
        TreeNode treeNode = createTreeNode();
        traverse(treeNode);
        System.out.println(treeNode);
    }

    static void traverse(TreeNode treeNode){
        if (treeNode == null) {
            return;
        }
        if (treeNode.left != null) {
            if (treeNode.right != null) {
                treeNode.left.next = treeNode.right;
                TreeNode tmp = treeNode.next;
                if (treeNode != null && treeNode.val != null &&  treeNode.val == 7) {
                    System.out.println("Xx");
                }
                while (tmp != null &&  treeNode.right.next == null){
                    treeNode.right.next = tmp.left.val != null ? tmp.left : tmp.right.val != null ? tmp.right : null;
                    tmp = tmp.next;
                }
            }else {
                TreeNode tmp = treeNode.next;
                while (tmp != null &&  treeNode.left.next == null){
                    treeNode.left.next = tmp.left != null ? tmp.left : tmp.right;
                    tmp = tmp.next;
                }
            }
        }else {
            if (treeNode.right != null) {
                TreeNode tmp = treeNode.next;
                while (tmp != null &&  treeNode.right.next == null){
                    treeNode.right.next = tmp.left != null ? tmp.left : tmp.right;
                    tmp = tmp.next;
                }
            }
        }
        traverse(treeNode.right);
        traverse(treeNode.left);
    }

    static TreeNode createTreeNode(){
        Integer[] list = {2,1,3,0,7,9,1,2,null,1,0,null,null,8,8,null,null,null,null,7};
        TreeNode treeNode = new TreeNode(list[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(treeNode);
        int i = 1;
        while (list.length > i){
            while (!queue.isEmpty()&&list.length > i){
                TreeNode n = queue.remove();
                n.left = new TreeNode(list[i]);i++;
                queue.offer(n.left);
                if (list.length > i) {
                    n.right = new TreeNode(list[i]);i++;
                    queue.offer(n.right);
                }
            }
        }
        return treeNode;
    }

}
