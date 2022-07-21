//给你一棵二叉树的根节点 root ，翻转这棵二叉树，并返回其根节点。
//
//
//
// 示例 1：
//
//
//
//
//输入：root = [4,2,7,1,3,6,9]
//输出：[4,7,2,9,6,3,1]
//
//
// 示例 2：
//
//
//
//
//输入：root = [2,1,3]
//输出：[2,3,1]
//
//
// 示例 3：
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
// 树中节点数目范围在 [0, 100] 内
// -100 <= Node.val <= 100
//
// Related Topics 树 深度优先搜索 广度优先搜索 二叉树 👍 1346 👎 0

package com.sherlock.easy;

import com.sherlock.utils.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Description:
 * @Author: linmuyu
 * @Date: 2022/7/14 16:00
 */
public class InvertTree {


    public static void main(String[] args) {
        TreeNode treeNode = createTreeNode();
        System.out.println(treeNode);
    }

    static TreeNode traverse(TreeNode treeNode){
        if (treeNode == null) {
            return treeNode;
        }
        TreeNode tmp = treeNode.left;
        treeNode.left = treeNode.right;
        treeNode.right = tmp;
        traverse(treeNode.left);
        traverse(treeNode.right);
        return treeNode;
    }

    static TreeNode createTreeNode(){
        Integer[] list = {4,2,7,1,3,6,9};
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
