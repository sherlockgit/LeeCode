//给定两个整数数组 inorder 和 postorder ，其中 inorder 是二叉树的中序遍历， postorder 是同一棵树的后序遍历，请你构造并
//返回这颗 二叉树 。
//
//
//
// 示例 1:
//
//
//输入：inorder = [9,3,15,20,7], postorder = [9,15,7,20,3]
//输出：[3,9,20,null,null,15,7]
//
//
// 示例 2:
//
//
//输入：inorder = [-1], postorder = [-1]
//输出：[-1]
//
//
//
//
// 提示:
//
//
// 1 <= inorder.length <= 3000
// postorder.length == inorder.length
// -3000 <= inorder[i], postorder[i] <= 3000
// inorder 和 postorder 都由 不同 的值组成
// postorder 中每一个值都在 inorder 中
// inorder 保证是树的中序遍历
// postorder 保证是树的后序遍历
//
// Related Topics 树 数组 哈希表 分治 二叉树 👍 828 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

package com.sherlock.medium.chapter0106;

import com.sherlock.utils.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: linmuyu
 * @Date: 2022/9/5 10:23
 */
public class Test {

    public static void main(String[] args) {
        int[] postorder = {9,15,7,20,3};
        int[] inorder = {9,3,15,20,7};
        Map<Integer,Integer> map = new HashMap<>();
        for(int i = 0;i < inorder.length; i++){
            map.put(inorder[i],i);
        }
        TreeNode treeNode = buildTree(map,postorder,postorder.length-1,0,inorder,0,inorder.length-1);
    }


    public static TreeNode buildTree(Map<Integer,Integer> map,
                                     int[] postorder, int posStart, int posEnd,
                                     int[] inorder, int inStart, int inEnd){
        if (inStart > inEnd) {
            return null;
        }
        int root = postorder[posStart];
        int index = map.get(root);
        TreeNode treeNode = new TreeNode(root);
        int leftSize = index -  inStart;
        treeNode.left = buildTree(map,postorder, posEnd+leftSize - 1, posEnd, inorder, index - leftSize, index - 1);
        treeNode.right = buildTree(map,postorder, posStart - 1, posEnd+leftSize, inorder, index + 1, inEnd);
        return treeNode;
    }

}
