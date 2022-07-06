//给定一个二叉树，找出其最大深度。
//
// 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
//
// 说明: 叶子节点是指没有子节点的节点。
//
// 示例：
//给定二叉树 [3,9,20,null,null,15,7]，
//
//     3
//   / \
//  9  20
//    /  \
//   15   7
//
// 返回它的最大深度 3 。
// Related Topics 树 深度优先搜索 广度优先搜索 二叉树 👍 1281 👎 0

package com.sherlock.easy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @Description:
 * @Author: linmuyu
 * @Date: 2022/7/4 14:19
 */
public class DeepTree {

    public static void main(String[] args) {
        TreeNode treeNode = createTreeNode();
        System.out.println(createTreeNode());
    }

    static TreeNode createTreeNode(){
        Integer[] list = {3,9,20,null,null,15,7};
        TreeNode treeNode = new TreeNode(list[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(treeNode);
        int i = 1;
        while (list.length > i){
            while (!queue.isEmpty()&&list.length > i){
                TreeNode n = queue.remove();
                n.left = new TreeNode(list[i]);i++;
                n.right = new TreeNode(list[i]);i++;
                queue.offer(n.left);
                queue.offer(n.right);
            }
        }
        return treeNode;
    }

}


class TreeNode {
    Integer val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(Integer val) { this.val = val; }
    TreeNode(Integer val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
