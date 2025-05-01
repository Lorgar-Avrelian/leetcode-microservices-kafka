package lorgar.avrelian.task0069.model;

import lorgar.avrelian.base.model.Task;
import lorgar.avrelian.base.model.TestValues;

public class Task0069 extends Task<Integer, Integer> {
    public Task0069(TestValues<Integer, Integer> testValues) {
        super(testValues);
    }

    @Override
    public Integer doTask(Integer input) {
        return mySqrt(input);
    }

    private static int mySqrt(int x) {
        if (x == 0) return 0;
        if (x < 4) return 1;
        int left = 2;
        int right = x / 2;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (x / mid == mid) return mid;
            else if (x / mid < mid) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return right;
    }
}
