//给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
//
// 有效字符串需满足：
//
//
// 左括号必须用相同类型的右括号闭合。
// 左括号必须以正确的顺序闭合。
//
//
//
//
// 示例 1：
//
//
//输入：s = "()"
//输出：true
//
//
// 示例 2：
//
//
//输入：s = "()[]{}"
//输出：true
//
//
// 示例 3：
//
//
//输入：s = "(]"
//输出：false
//
//
// 示例 4：
//
//
//输入：s = "([)]"
//输出：false
//
//
// 示例 5：
//
//
//输入：s = "{[]}"
//输出：true
//
//
//
// 提示：
//
//
// 1 <= s.length <= 104
// s 仅由括号 '()[]{}' 组成
//
// Related Topics 栈 字符串
// 👍 2854 👎 0

package com.sherlock.easy;

import java.util.Stack;

/**
 * @Description:
 * @Author: linmuyu
 * @Date: 2021/12/30 16:57
 */
public class Brackets {

    public static String change(String s){
        switch (s) {
            case "}":
                return "{";
            case ")":
                return "(";
            case "]":
                return "[";
        }
        return null;
    }

    public static boolean isValid(String s) {
        String right = "";
        for(int i = s.length()-1;i >= 0;i--){
            String left = String.valueOf(s.charAt(i));
            if (right.equals("")) {
                right = change(left);
                if (right == null) {
                    return false;
                }
            }else {
                if (right.equals(left)) {
                    right = "";
                }else {
                    if (!left.equals(right.substring(0,1))) {
                        if (change(left) == null) {
                            return false;
                        }
                        right =  change(left) + right;
                    }else {
                        right = right.substring(1);
                    }
                }
            }
        }
        return right.equals("");
//        Stack<Character>stack = new Stack<Character>();
//        for(char c: s.toCharArray()){
//            if(c=='(')stack.push(')');
//            else if(c=='[')stack.push(']');
//            else if(c=='{')stack.push('}');
//            else if(stack.isEmpty()||c!=stack.pop())return false;
//        }
//        return stack.isEmpty();
    }

    public static void main(String[] args) {
        System.out.println(isValid("{[}]"));
    }

}
