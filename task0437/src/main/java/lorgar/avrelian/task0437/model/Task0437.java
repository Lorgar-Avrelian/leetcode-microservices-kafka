package lorgar.avrelian.task0437.model;

import lorgar.avrelian.base.model.Task;
import lorgar.avrelian.base.model.TestValues;

import java.util.*;

public class Task0437 extends Task<Map<TreeNode, Integer>, Integer> {
    public Task0437(TestValues<Map<TreeNode, Integer>, Integer> testValues) {
        super(testValues);
    }

    @Override
    public Integer doTask(Map<TreeNode, Integer> input) {
        TreeNode root = input.keySet().stream().findFirst().get();
        Integer targetSum = input.get(root);
        return pathSum(root, targetSum);
    }

    private static int pathSum(TreeNode root, int targetSum) {
        if (root == null) return 0;
        return calculateSumCount(root, new HashMap<>(Map.of(0L, 1)), 0L, targetSum);
    }

    private static int calculateSumCount(TreeNode node, Map<Long, Integer> differenceMap, long currentSum, int targetSum) {
        if (node == null) return 0;
        currentSum += node.val;
        int count = differenceMap.getOrDefault(currentSum - targetSum, 0);
        differenceMap.put(currentSum, differenceMap.getOrDefault(currentSum, 0) + 1);
        count += calculateSumCount(node.left, differenceMap, currentSum, targetSum) + calculateSumCount(node.right, differenceMap, currentSum, targetSum);
        differenceMap.put(currentSum, differenceMap.get(currentSum) - 1);
        return count;
    }
}
