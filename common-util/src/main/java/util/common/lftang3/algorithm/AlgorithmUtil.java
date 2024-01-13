package util.common.lftang3.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：lftang3
 * @date ：Created in 2022/7/6 11:38
 * @description：简单算法工具(leetcode)
 * @modified By：
 * @version: 1.0$
 */
public class AlgorithmUtil {

    private AlgorithmUtil() {
    }

    /**
     * 判断是否是回文数  如：12321
     *
     * @param num
     * @return
     */
    public static boolean isPalindrome(int num) {
        String str = num + "";
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length / 2; i++) {
            char start = chars[i];
            char end = chars[chars.length - i - 1];
            if (start != end) {
                return false;
            }
        }
        return true;
    }

    /**
     * 罗马文字转数字
     *
     * @param s
     * @return
     */
    static Map<Character, Integer> map = new HashMap<>();

    static {
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);
    }

    public static int romanToInt(String s) {
        if (s == null) {
            return 0;
        }
        char[] chars = s.toCharArray();
        int number = 0;
        for (int i = 0; i < chars.length; i++) {
            char start = chars[i];
            if (i == chars.length - 1) {
                number += map.get(start);
                continue;
            }
            char next = chars[i + 1];
            if (map.get(start) >= map.get(next)) {
                number += map.get(start);
            } else {
                number += map.get(next) - map.get(start);
                i++;
            }
        }
        return number;
    }

    /**
     * 获取数组中相同的前缀
     *
     * @param strS
     * @return
     */
    public static String longestCommonPrefix(String[] strS) {
        if (strS.length <= 0) {
            return "";
        }
        String start = strS[0];
        char[] chars = start.toCharArray();
        Map<Integer, Character> map = new HashMap<>();
        Map<Integer, Character> result = new HashMap<>();
        int index = 0;
        for (char aChar : chars) {
            map.put(index, aChar);
            index++;
        }
        for (int i = 1; i < strS.length; i++) {
            String str = strS[i];
            char[] strChar = str.toCharArray();
            for (int j = 0; j < map.size(); j++) {
                Character character = map.get(j);
                char c = strChar[j];
                if (character != c) {
                    map.clear();
                    break;
                }
                result.put(j, character);
            }
            map.putAll(result);
            result.clear();
        }
        StringBuilder sb = new StringBuilder();
        for (Character value : map.values()) {
            sb.append(value);
        }
        return sb.toString();
    }

    /**
     * 判断源字符串中是否存在目标字符串
     *
     * @param source
     * @param target
     * @return
     */
    public static int existString(String source, String target) {
        if ("".equals(source) || source == null) {
            return -1;
        }
        if ("".equals(target) || target == null) {
            return -1;
        }
        char[] sourceChars = source.toCharArray();
        char[] targetChars = target.toCharArray();
        return existString(sourceChars, 0, sourceChars.length, targetChars, 0, targetChars.length);
    }

    /**
     * 双指针算法
     * 查询源数组中是否存在另一个数组
     *
     * @param sourceChars  集合
     * @param sourcePoint  开始坐标
     * @param sourceLength 数组长度
     * @param targetChars  目标数组
     * @param targetPoint  目标数组开始坐标
     * @param targetLength 目标数组长度
     * @return
     */
    private static int existString(char[] sourceChars, int sourcePoint, int sourceLength, char[] targetChars, int targetPoint, int targetLength) {
        boolean flag = false;
        int index = 0;
        int count = 0;
        int max = sourceLength - targetLength + sourcePoint;
        // 源字符串剩余长度大于目标字符串长度
        while (max >= sourcePoint && targetPoint < targetChars.length) {
            if (sourceChars[sourcePoint] != targetChars[targetPoint]) {
                sourcePoint++;
                targetPoint = 0;
                flag = false;
                index = 0;
                count = 0;
                continue;
            }
            if (index == 0 && count == 0) {
                index = sourcePoint;
                count++;
            }
            sourcePoint++;
            targetPoint++;
            flag = true;
        }
        return flag ? index : -1;
    }
}
