package com.sherlock.medium;

//给定一个 完美二叉树 ，其所有叶子节点都在同一层，每个父节点都有两个子节点。二叉树定义如下：
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
// 示例 1：
//
//
//
//
//输入：root = [1,2,3,4,5,6,7]
//输出：[1,#,2,3,#,4,5,6,7,#]
//解释：给定二叉树如图 A 所示，你的函数应该填充它的每个 next 指针，以指向其下一个右侧节点，如图 B 所示。序列化的输出按层序遍历排列，同一层节点由
//next 指针连接，'#' 标志着每一层的结束。
//
//
//
//
// 示例 2:
//
//
//输入：root = []
//输出：[]
//
//
//
//
// 提示：
//
//
// 树中节点的数量在 [0, 2¹² - 1] 范围内
// -1000 <= node.val <= 1000
//
//
//
//
// 进阶：
//
//
// 你只能使用常量级额外空间。
// 使用递归解题也符合要求，本题中递归程序占用的栈空间不算做额外的空间复杂度。
//
// Related Topics 树 深度优先搜索 广度优先搜索 链表 二叉树 👍 832 👎 0


import com.sherlock.utils.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

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
public class PaddingPoint {

    public static void main(String[] args) {
        TreeNode treeNode = createTreeNode();
        traverse(treeNode.left,treeNode.right);
    }

    static void traverse(TreeNode treeNode){
        if (treeNode == null) {
            return;
        }
        if (treeNode.left != null) {
            treeNode.left.next = treeNode.right;
            if (treeNode.next != null) {
                treeNode.right.next = treeNode.next.left;
            }
        }
        traverse(treeNode.left);
        traverse(treeNode.right);
    }

    /**
     * 三叉树方式
     * @param left
     * @param right
     */
    static void traverse(TreeNode left,TreeNode right){
        if (left == null || right == null) {
            return;
        }
        left.next = right;
        traverse(left.left,left.right);
        traverse(right.left,right.right);
        traverse(left.right,right.left);
    }

    static TreeNode createTreeNode(){
        Integer[] list = {1,2,5,3,4,5,6,7};
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
