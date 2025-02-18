//给你二叉树的根节点 root ，返回它节点值的 前序 遍历。
//
//
//
// 示例 1：
//
//
//输入：root = [1,null,2,3]
//输出：[1,2,3]
//
//
// 示例 2：
//
//
//输入：root = []
//输出：[]
//
//
// 示例 3：
//
//
//输入：root = [1]
//输出：[1]
//
//
// 示例 4：
//
//
//输入：root = [1,2]
//输出：[1,2]
//
//
// 示例 5：
//
//
//输入：root = [1,null,2]
//输出：[1,2]
//
//
//
//
// 提示：
//
//
// 树中节点数目在范围 [0, 100] 内
// -100 <= Node.val <= 100
//
//
//
//
// 进阶：递归算法很简单，你可以通过迭代算法完成吗？
// Related Topics 栈 树 深度优先搜索 二叉树 👍 863 👎 0

package com.sherlock.easy.chapter0144;

import com.sherlock.utils.TreeNode;

import java.util.*;

/**
 * @Description:
 * @Author: linmuyu
 * @Date: 2022/7/12 16:42
 */
public class Test {

    static List<Integer> list;

    public static void main(String[] args) {
        list = new ArrayList<>();
        TreeNode treeNode = createTreeNode();
//        traverse(treeNode);
        iteration(treeNode);
        System.out.println(list);
    }

    /**
     * 递归算法
     * @param treeNode
     */
    static void traverse(TreeNode treeNode){
        if (treeNode == null) {
            return;
        }else if(treeNode.val != null){
            list.add(treeNode.val);
        }
        traverse(treeNode.left);
        traverse(treeNode.right);
    }

    /**
     * 迭代算法
     * @param treeNode
     */
    static void iteration(TreeNode treeNode){
        Stack<TreeNode> stack = new Stack<>();
        while (treeNode != null){
            if(treeNode.val != null){
                list.add(treeNode.val);
            }
            if (treeNode.left == null) {
                if (treeNode.right == null) {
                    treeNode = stack.isEmpty() ? null : stack.pop().right;
                    continue;
                }
                stack.push(treeNode);
                treeNode = treeNode.right;
                continue;
            }
            stack.push(treeNode);
            treeNode = treeNode.left;
        }
    }


    static TreeNode createTreeNode(){
        Integer[] list = {1,null,2,3,4};
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
