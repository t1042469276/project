package util.common.lftang3.algorithm;

import java.util.*;

/**
 * @author ：lftang3
 * @date ：Created in 2022/8/1 19:49
 * @description：简单算法
 * @modified By：
 * @version: 1.0$
 */
public class SimpleAlgorithmUtil {

    private SimpleAlgorithmUtil() {}

    /**
     * 数组必须升序
     * 二分查找
     *
     * @param nums
     * @param target
     * @return
     */
    public static int search(int[] nums, int target) {
        if (nums == null) {
            return -1;
        }
        int start = 0;
        int end = nums.length - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] < target) {
                start = mid + 1;
            } else if (nums[mid] > target) {
                end = mid - 1;
            } else {
                return mid;
            }
        }
        return start;
    }

    /**
     * 数组元素平方后排序算法
     *
     * @param nums
     * @return
     */
    public static int[] sortedSquares(int[] nums) {
        if (nums == null) {
            return new int[0];
        }
        int[] news = new int[nums.length];
        news[0] = nums[0] * nums[0];
        for (int i = 1; i < nums.length; i++) {
            int value = nums[i] * nums[i];
            for (int j = i - 1; j >= 0; j--) {
                if (value < news[j]) {
                    news[j + 1] = news[j];
                    news[j] = value;
                } else if (value >= news[j]) {
                    news[j + 1] = value;
                    break;
                }
            }
        }
        return news;
    }

    /**
     * k个位置开始转轮
     * 输入: nums = [1,2,3,4,5,6,7], k = 3
     * 输出: [5,6,7,1,2,3,4]
     * 解释:
     * 向右轮转 1 步: [7,1,2,3,4,5,6]
     * 向右轮转 2 步: [6,7,1,2,3,4,5]
     * 向右轮转 3 步: [5,6,7,1,2,3,4]
     * @param nums  輸入數組
     * @param k     轉輪坐標
     */
    public static void rotate1(int[] nums, int k) {
        if (nums == null) {
            return;
        }
        k %= nums.length;
        if (k == 0) {
            return;
        }
        flipArray(nums, 0, nums.length - 1);
        flipArray(nums, 0, k - 1);
        flipArray(nums, k, nums.length - 1);
    }

    /**
     * 数组转轮
     * @param nums
     * @param k
     */
    public static void rotate2(int[] nums, int k) {
        if (nums == null) {
            return;
        }
        k %= nums.length;
        if (k == 0) {
            return;
        }
        int[] news = Arrays.copyOf(nums, nums.length);
        int index = 0;
        for (int i = 0; i < news.length; i++) {
            if (i + k < news.length) {
                nums[i + k] = news[i];
            } else {
                nums[index] = news[i];
                index++;
            }
        }
    }

    /**
     *  翻转数组算法
     * 指定坐标内的数组元素翻转
     * 示例：
     *  [1,2,3,4,5,6,7] , 2,5
     *  [1,2,6,5,4,3,7]
     * @param nums
     * @param startIndex
     * @param endIndex
     */
    public static void flipArray(int[] nums, int startIndex, int endIndex) {
        if (nums.length <= endIndex) {
            return;
        }
        while (startIndex < endIndex) {
            int temp = nums[startIndex];
            nums[startIndex] = nums[endIndex];
            nums[endIndex] = temp;
            startIndex++;
            endIndex--;
        }
    }

    public static int removeDuplicates(int[] nums) {
        Map<Integer,Integer> map = new HashMap<>();
        for(int i=0;i<nums.length;i++){
            map.put(nums[i],i);
        }
        Set<Integer> integers = map.keySet();
        int index = 0;
        for (Integer integer : integers) {
            nums[index] = integer;
        }
        return nums.length;
    }

}
