//给定一棵二叉树，你需要计算它的直径长度。一棵二叉树的直径长度是任意两个结点路径长度中的最大值。这条路径可能穿过也可能不穿过根结点。
//
//
//
// 示例 :
//给定二叉树
//
//           1
//         / \
//        2   3
//       / \
//      4   5
//
//
// 返回 3, 它的长度是路径 [4,2,1,3] 或者 [5,2,1,3]。
//
//
//
// 注意：两结点之间的路径长度是以它们之间边的数目表示。
// Related Topics 树 深度优先搜索 二叉树 👍 1084 👎 0


package com.sherlock.easy;

import com.sherlock.utils.TreeNode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * @Description:
 * @Author: linmuyu
 * @Date: 2022/7/9 16:42
 */
public class DiameterOfBinaryTree {

    static int max = 0;

    public static void main(String[] args) {
        TreeNode treeNode = createTreeNode();
        traverse(treeNode);
        System.out.println(max);
    }

    static int traverse(TreeNode treeNode){
        if (treeNode == null) {
            return 0;
        }
        int leftMax = treeNode.left == null ? 0 : traverse(treeNode.left) + 1;
        int rightMax = treeNode.right == null ? 0 : traverse(treeNode.right) + 1 ;
        max = Math.max(max,leftMax + rightMax);
        return Math.max(leftMax,rightMax);
    }

    static TreeNode createTreeNode(){
        Integer[] list = {1,2,3,4,5};
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


