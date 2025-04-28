package lorgar.avrelian.task0027.model;

import lorgar.avrelian.base.model.Task;
import lorgar.avrelian.base.model.TestValues;

import java.util.Map;

public class Task0027 extends Task<Map<int[], Integer>, Integer> {
    public Task0027(TestValues<Map<int[], Integer>, Integer> testValues) {
        super(testValues);
    }

    @Override
    public Integer doTask(Map<int[], Integer> input) {
        int[] ints = input.keySet().stream().findFirst().get();
        return removeElement(ints, input.get(ints));
    }

    private static int removeElement(int[] nums, int val) {
        if (nums == null || nums.length == 0) return 0;
        int i = 0;
        int j = 1;
        while (j < nums.length) {
            if (nums[i] != val) {
                i++;
            }
            nums[i] = nums[j];
            j++;
        }
        if (nums[i] != val) {
            j = i + 1;
            while (j < nums.length) {
                nums[j] = 0;
                j++;
            }
            return i + 1;
        } else {
            j = i;
            while (j < nums.length) {
                nums[j] = 0;
                j++;
            }
            return i;
        }
    }
}
