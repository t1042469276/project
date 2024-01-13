package util.common.lftang3.algorithm;

/**
 * @author ：lftang3
 * @date ：Created in 2022/8/4 9:35
 * @description：排序算法
 * @modified By：
 * @version: 1.0$
 */
public class SortAlgorithmUtil {

    private SortAlgorithmUtil() {
    }

    /**
     * 冒泡排序
     * 1.比较相邻的两个元素
     * 2.每一轮比较都需要换位置
     *
     * @param nums
     * @return
     */
    public static int[] bubblingSort(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length - i; j++) {
                //前一个元素大于后一个元素，交换位置
                if (nums[j - 1] > nums[j]) {
                    int temp = nums[j - 1];
                    nums[j] = nums[j - 1];
                    nums[j - 1] = temp;
                }
            }
        }
        return nums;
    }

    /**
     * 插入排序
     * 1.选择一个元素，和前面的所有元素比较
     * a.插入元素大于比较的元素，插入到该元素的后面
     * b.插入元素小于于比较的元素，该元素和后一个元素交换位置（插入元素的位置）
     * c.插入元素继续和前一个元素比较（循环）
     *
     * @param nums
     * @return
     */
    public static int[] insertSort(int[] nums) {
        int insertNode;
        int j;
        for (int i = 1; i < nums.length; i++) {
            insertNode = nums[i];
            j = i - 1;
            //选择一个节点，和前面的所有元素比较
            while (j >= 0 && insertNode < nums[j]) {
                //插入的元素小于第j个元素,第j个元素往后移动()
                nums[j + 1] = nums[j];
                j--;
            }
            //插入的元素大于第j个元素,直接赋值给下一个元素
            nums[j + 1] = insertNode;
        }
        return nums;
    }

    /**
     * 选择排序
     * 1.按顺序比较
     * 2.每一轮只换一次位置
     * 3.交换位置少
     *
     * @param nums
     * @return
     */
    public static int[] selectSort(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            int point = i;
            // 选择的元素大于第j个元素,交换下标
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[point] > nums[j]) {
                    point = j;
                }
            }
            if (point != i) {
                int temp = nums[i];
                nums[i] = nums[point];
                nums[point] = temp;
            }
        }
        return nums;
    }

    /**
     * 快速排序
     * 1.选第一个值,跟左右两边的值比较
     * a.该值大于左边的值，左指针右移
     * b.该值小于右边的值，右指针左移
     * 2.不符合条件，左右找到的坐标的值交换(小的左边，大的右边)
     * 3.左边的开始快排
     * 4.右边的开始快排
     *
     * @param nums
     * @param start
     * @param end
     */
    public static void quickSort(int[] nums, int start, int end) {
        if (start >= end) {
            return;
        }
        int point = nums[start];
        int left = start;
        int right = end;
        while (left <= right) {
            //第一个值大于左边的值，左指针向右走
            while (left <= right && point > nums[left]) {
                left++;
            }
            //第一个值小于右边的值，
            while (left <= right && point < nums[right]) {
                right--;
            }
            //左指针小于右指针，交换
            if (left <= right) {
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
                left++;
                right--;
            }
        }
        quickSort(nums, start, right);
        quickSort(nums, left, end);
    }

    /**
     * 归并排序
     * 1.将下标拆到不能再拆(对半分)
     * 2.排序
     * 3.临时数组记录临时排序结果
     * 4.
     *
     * @param nums
     * @return
     */
    public static int[] mergeSort(int[] nums) {
        int[] tempArray = new int[nums.length];
        splitIndex(nums, 0, nums.length - 1, tempArray);
        return nums;
    }

    /**
     * 拆分数组（拆分下标），拆到不能拆分
     *
     * @param nums
     * @param start
     * @param end
     * @param tempArray
     * @return
     */
    private static void splitIndex(int[] nums, int start, int end, int[] tempArray) {
        if (start >= end) {
            return;
        }
        int mid = start + (end - start) / 2;
        splitIndex(nums, start, mid, tempArray);
        splitIndex(nums, mid + 1, end, tempArray);
        mergerArray(nums, start, mid, end, tempArray);
    }

    /**
     * 合并数组
     * 1.start->mid之间的元素为左,mid+1->end之间的元素为右，依次做比较，存入小的元素到临时数组
     *
     * @param nums
     * @param start
     * @param mid
     * @param end
     * @param tempArray
     * @return
     */
    private static void mergerArray(int[] nums, int start, int mid, int end, int[] tempArray) {
        int left = start;
        int right = mid + 1;
        int index = start;
        while (left <= mid && right <= end) {
            //左边的值小于右边的值，将左边的值存入临时数组
            if (nums[left] < nums[right]) {
                tempArray[index++] = nums[left++];
            } else {
                tempArray[index++] = nums[right++];
            }
        }
        while (left <= mid) {
            tempArray[index++] = nums[left++];
        }
        while (right <= end) {
            tempArray[index++] = nums[right++];
        }
        for (index = start; index <= end; index++) {
            nums[index] = tempArray[index];
        }
    }
}
