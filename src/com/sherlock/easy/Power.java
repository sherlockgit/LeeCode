//编写一个函数来查找字符串数组中的最长公共前缀。
//
// 如果不存在公共前缀，返回空字符串 ""。
//
//
//
// 示例 1：
//
//
//输入：strs = ["flower","flow","flight"]
//输出："fl"
//
//
// 示例 2：
//
//
//输入：strs = ["dog","racecar","car"]
//输出：""
//解释：输入不存在公共前缀。
//
//
//
// 提示：
//
//
// 1 <= strs.length <= 200
// 0 <= strs[i].length <= 200
// strs[i] 仅由小写英文字母组成
//
// Related Topics 字符串
// 👍 1941 👎 0

package com.sherlock.easy;

/**
 * @Description:
 * @Author: linmuyu
 * @Date: 2021/12/29 16:37
 */
public class Power {

    public static String with(String s,String result){
        if (s.startsWith(result) || result.equals("")) {
            return result;
        }
        return with(s,result.substring(0,result.length()-1));
    }

    public static String longestCommonPrefix(String[] strs) {
        String result = null;
        for (String s : strs){
            if (result == null) {
                result = s;
                continue;
            }
            if (!s.startsWith(result)) {
                result = with(s,result);
            }
            if (result.equals("")) {
                return result;
            }
        }
       return result;
    }

    public static void main(String[] args) {
        String[] s = new String[]{"dog","racecar","car"};
        System.out.println(longestCommonPrefix(s));
    }
}
