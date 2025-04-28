package lorgar.avrelian.task0035.model;

import lorgar.avrelian.base.model.Task;
import lorgar.avrelian.base.model.TestValues;

import java.util.Map;

public class Task0035 extends Task<Map<int[], Integer>, Integer> {
    public Task0035(TestValues<Map<int[], Integer>, Integer> testValues) {
        super(testValues);
    }

    @Override
    public Integer doTask(Map<int[], Integer> input) {
        int[] ints = input.keySet().stream().findFirst().get();
        return searchInsert(ints, input.get(ints));
    }

    private static int searchInsert(int[] nums, int target) {
        if (target < nums[0]) return 0;
        if (target > nums[nums.length - 1]) return nums.length;
        int i = 0;
        while (i < nums.length && nums[i] < target) {
            i++;
        }
        return i;
    }
}
