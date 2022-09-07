//给定两个整数数组，preorder 和 postorder ，其中 preorder 是一个具有 无重复 值的二叉树的前序遍历，postorder 是同一棵
//树的后序遍历，重构并返回二叉树。
//
// 如果存在多个答案，您可以返回其中 任何 一个。
//
//
//
// 示例 1：
//
//
//
//
//输入：preorder = [1,2,4,5,3,6,7], postorder = [4,5,2,6,7,3,1]
//输出：[1,2,3,4,5,6,7]
//
//
// 示例 2:
//
//
//输入: preorder = [1], postorder = [1]
//输出: [1]
//
//
//
//
// 提示：
//
//
// 1 <= preorder.length <= 30
// 1 <= preorder[i] <= preorder.length
// preorder 中所有值都 不同
// postorder.length == preorder.length
// 1 <= postorder[i] <= postorder.length
// postorder 中所有值都 不同
// 保证 preorder 和 postorder 是同一棵二叉树的前序遍历和后序遍历
//
// Related Topics 树 数组 哈希表 分治 二叉树 👍 273 👎 0
package com.sherlock.medium;

import com.sherlock.utils.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: linmuyu
 * @Date: 2022/9/6 11:18
 */
public class BuildPreorderAndPostorderTree {

    public static void main(String[] args) {
        int[] preorder = {2,1};
        int[] postorder = {1,2};

        Map<Integer,Integer> map = new HashMap<>();
        for(int i = 0;i < postorder.length; i++){
            map.put(postorder[i],i);
        }
        TreeNode treeNode = buildTree(map,preorder,0,preorder.length-1,postorder,0,postorder.length-1);
        System.out.println(treeNode);
    }


    public static TreeNode buildTree(Map<Integer,Integer> map,
                                     int[] preorder, int preStart, int preEnd,
                                     int[] postorder, int posStart, int posEnd
                                     ){
        if (posStart > posEnd) {
            return null;
        }
        int root = preorder[preStart];
        TreeNode treeNode = new TreeNode(root);
        if (posStart == posEnd) {
            return treeNode;
        }
        int index = map.get(preorder[preStart+1]);
        int leftSize = index - posStart + 1;
        treeNode.left = buildTree(map,preorder, preStart + 1, preStart + leftSize, postorder, index-leftSize+1, index);
        treeNode.right = buildTree(map,preorder, preStart + leftSize + 1, preEnd, postorder, index + 1, posEnd-1);
        return treeNode;
    }

}
