package com.sherlock.medium.chapter0105;
//给定两个整数数组 preorder 和 inorder ，其中 preorder 是二叉树的先序遍历， inorder 是同一棵树的中序遍历，请构造二叉树并
//返回其根节点。
//
//
//
// 示例 1:
//
//
//输入: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
//输出: [3,9,20,null,null,15,7]
//
//
// 示例 2:
//
//
//输入: preorder = [-1], inorder = [-1]
//输出: [-1]
//
//
//
//
// 提示:
//
//
// 1 <= preorder.length <= 3000
// inorder.length == preorder.length
// -3000 <= preorder[i], inorder[i] <= 3000
// preorder 和 inorder 均 无重复 元素
// inorder 均出现在 preorder
// preorder 保证 为二叉树的前序遍历序列
// inorder 保证 为二叉树的中序遍历序列
//
// Related Topics 树 数组 哈希表 分治 二叉树 👍 1696 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import com.sherlock.utils.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 从前序与中序遍历构造二叉树
 * @Author: linmuyu
 * @Date: 2022/8/18 10:21
 */
public class Test {

    public static void main(String[] args) {
        int[] preorder = {1,2,3};
        int[] inorder = {3,2,1};
        Map<Integer,Integer> map = new HashMap<>();
        for(int i = 0;i < inorder.length; i++){
            map.put(inorder[i],i);
        }
        TreeNode treeNode = buildTree(map,preorder,0,preorder.length-1,inorder,0,inorder.length-1);
        System.out.println(treeNode);
    }

    public static TreeNode buildTree(Map<Integer,Integer> map,
                              int[] preorder,int preStart,int preEnd,
                              int[] inorder,int inStart,int inEnd){
        if (preStart > preEnd) {
            return null;
        }
        int root = preorder[preStart];
        int index = map.get(root);
        int leftSize = index - inStart;
        TreeNode treeNode = new TreeNode(root);
        treeNode.left = buildTree(map,preorder, preStart + 1, preStart + leftSize, inorder, inStart, index - 1);
        treeNode.right = buildTree(map,preorder, preStart + leftSize + 1, preEnd, inorder, index + 1, inEnd);
        return treeNode;
    }

}
