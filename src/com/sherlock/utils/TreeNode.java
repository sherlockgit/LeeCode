package com.sherlock.utils;

/**
 * @Description:
 * @Author: linmuyu
 * @Date: 2022/7/9 16:52
 */
public class TreeNode {
    public Integer val;
    public TreeNode left;
    public TreeNode right;
    public TreeNode next;
    public TreeNode() {}
    public TreeNode(Integer val) { this.val = val; }
    public TreeNode(Integer val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
