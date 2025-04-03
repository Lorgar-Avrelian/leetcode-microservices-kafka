package lorgar.avrelian.task0001.model;

import lorgar.avrelian.base.model.Task;
import lorgar.avrelian.base.model.TestValues;

public class Task0001 extends Task<Input, int[]> {
    public Task0001(TestValues<Input, int[]> testValues) {
        super(testValues);
    }

    @Override
    public int[] doTask(Input input) {
        return twoSum(input.getArray(), input.getTarget());
    }

    private int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = 1; j < nums.length - i; j++) {
                if (nums[i] + nums[i + j] == target) return new int[]{i, i + j};
            }
        }
        return null;
    }
}
