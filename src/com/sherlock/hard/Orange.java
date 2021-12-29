//给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的 中位数 。
//
//
//
// 示例 1：
//
//
//输入：nums1 = [1,3], nums2 = [2]
//输出：2.00000
//解释：合并数组 = [1,2,3] ，中位数 2
//
//
// 示例 2：
//
//
//输入：nums1 = [1,2], nums2 = [3,4]
//输出：2.50000
//解释：合并数组 = [1,2,3,4] ，中位数 (2 + 3) / 2 = 2.5
//
//
// 示例 3：
//
//
//输入：nums1 = [0,0], nums2 = [0,0]
//输出：0.00000
//
//
// 示例 4：
//
//
//输入：nums1 = [], nums2 = [1]
//输出：1.00000
//
//
// 示例 5：
//
//
//输入：nums1 = [2], nums2 = []
//输出：2.00000
//
//
//
//
// 提示：
//
//
// nums1.length == m
// nums2.length == n
// 0 <= m <= 1000
// 0 <= n <= 1000
// 1 <= m + n <= 2000
// -106 <= nums1[i], nums2[i] <= 106
//
//
//
//
// 进阶：你能设计一个时间复杂度为 O(log (m+n)) 的算法解决此问题吗？
// Related Topics 数组 二分查找 分治
// 👍 4624 👎 0

package com.sherlock.hard;

/**
 * @Description:
 * @Author: linmuyu
 * @Date: 2021/11/5 16:32
 */
public class Orange {
    // todo
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int l1 = nums1.length;
        int l2 = nums2.length;
        int l12 = nums1.length + nums2.length;

        int m = l12%2 == 0 ? l12/2-1 : l12/2;
        int l1Left = 0;
        int l1Right = 0;

        int l2Left = 0;
        int l2Right = 0;

        int r1 = l1;
        int r2 = l2;
        do {
            int v1 = l1 - l1Left - l1Right;
            int v2 = l2 - l2Left - l2Right;
            r1 = r1+l1Left-l1Right;
            r2 = r2+l2Left-l2Right;
            double var1 = r1%2 == 0 ? r1 == 0 ? 0 : (double)(nums1[r1/2-1] + nums1[r1/2])/2 : nums1[r1/2];
            double var2 = r2%2 == 0 ? r2 == 0 ? 0 : (double)(nums2[r2/2-1] + nums2[r2/2])/2 : nums2[r2/2];
            if (var1 < var2) {
                l1Left = v1%2 == 0 ? v1/2-1+l1Left : v1/2+l1Left;
                l2Right = l2%2 == 0 ? l2/2-1+l2Right : l2/2+l2Right;
            }else if (var1 > var2) {
                l1Right = v1%2 == 0 ? v1/2-1+l1Right : v1/2+l1Right;
                l2Left = v2%2 == 0 ? v2/2-1+l2Left : v2/2+l2Left;
            }
        } while (m != l1Left + l2Left || m != l1Right + l2Right);



        return 0;
    }

    public static void main(String[] args) {
        int[] nums1 = {1,2,3,4,9,10,11};
        int[] nums2 = {6,7,8,12,13};
        System.out.println(findMedianSortedArrays(nums1,nums2));
    }

}
