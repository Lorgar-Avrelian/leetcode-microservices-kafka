package lorgar.avrelian.task0001.model;

import lorgar.avrelian.base.model.Task;
import lorgar.avrelian.base.model.TestValues;

import java.util.Map;

public class Task0001 extends Task<Map<int[], Integer>, int[]> {
    public Task0001(TestValues<Map<int[], Integer>, int[]> testValues) {
        super(testValues);
    }

    @Override
    public int[] doTask(Map<int[], Integer> input) {
        int[] ints = input.keySet().stream().findFirst().get();
        return twoSum(ints, input.get(ints));
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
