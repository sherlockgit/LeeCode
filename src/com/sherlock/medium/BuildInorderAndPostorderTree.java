package com.sherlock.medium;

import com.sherlock.utils.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: linmuyu
 * @Date: 2022/9/5 10:23
 */
public class BuildInorderAndPostorderTree {

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
