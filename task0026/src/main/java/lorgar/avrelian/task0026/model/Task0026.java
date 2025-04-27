package lorgar.avrelian.task0026.model;

import lorgar.avrelian.base.model.Task;
import lorgar.avrelian.base.model.TestValues;

public class Task0026 extends Task<int[], Integer> {
    public Task0026(TestValues<int[], Integer> testValues) {
        super(testValues);
    }

    @Override
    public Integer doTask(int[] input) {
        return removeDuplicates(input);
    }

    private static int removeDuplicates(int[] nums) {
        int i = 0;
        int j = 1;
        while (j < nums.length) {
            if (nums[i] != nums[j]) {
                i++;
                nums[i] = nums[j];
            }
            j++;
        }
        return i + 1;
    }
}
