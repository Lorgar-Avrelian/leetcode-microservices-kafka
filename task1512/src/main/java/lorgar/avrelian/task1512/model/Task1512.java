package lorgar.avrelian.task1512.model;

import lorgar.avrelian.base.model.Task;
import lorgar.avrelian.base.model.TestValues;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Task1512 extends Task<int[], Integer> {
    public Task1512(TestValues<int[], Integer> testValues) {
        super(testValues);
    }

    @Override
    public Integer doTask(int[] input) {
        return numIdenticalPairs(input);
    }

    public int numIdenticalPairs(int[] nums) {
        if (nums == null || nums.length < 2) return 0;
        int count = 0;
        Map<Integer, Integer> countMap = new HashMap<>();
        Set<Integer> numSet = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (numSet.contains(num)) {
                countMap.put(num, countMap.get(num) + 1);
            } else {
                numSet.add(num);
                countMap.put(num, 0);
            }
        }
        for (Integer i : numSet) {
            count += (countMap.get(i) * (countMap.get(i) + 1)) / 2;
        }
        return count;
    }
}
