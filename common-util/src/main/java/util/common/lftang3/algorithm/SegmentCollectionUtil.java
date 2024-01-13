package util.common.lftang3.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SegmentCollectionUtil {
    /**
     * 拆分list
     *
     * @param list       数据源
     * @param elementSum 每个小块元素个数
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> segmentList(List<T> list, int elementSum) {
        if (list.size() <= elementSum) {
            return Collections.singletonList(list);
        }
        List<List<T>> result = new ArrayList<>();
        int count = list.size() / elementSum + 1;
        List<T> segment = new ArrayList<>();
        int start = 0;
        int end = elementSum;
        while (start < end) {
            if (list.size() == start) {
                break;
            }
            T t = list.get(start);
            segment.add(t);
            if (start == end - 1 && count > 0) {
                count--;
                end += list.size() - end > elementSum ? elementSum : list.size() - end;
                result.add(segment);
                segment = new ArrayList<>();
            }
            start++;
        }
        return result;
    }
}
