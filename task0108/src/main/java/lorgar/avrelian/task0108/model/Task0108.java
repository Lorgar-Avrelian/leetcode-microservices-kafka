package lorgar.avrelian.task0108.model;

import lorgar.avrelian.base.model.Task;
import lorgar.avrelian.base.model.TestValues;
import lorgar.avrelian.base.model.TreeNode;

public class Task0108 extends Task<int[], TreeNode> {
    public Task0108(TestValues<int[], TreeNode> testValues) {
        super(testValues);
    }

    @Override
    public TreeNode doTask(int[] input) {
        return sortedArrayToBST(input);
    }

    private static TreeNode sortedArrayToBST(int[] nums) {
        return createBinary(nums, 0, nums.length - 1);
    }

    private static TreeNode createBinary(int[] nums, int start, int end) {
        if (start == nums.length - 1) return new TreeNode(nums[nums.length - 1]);
        if (start == end) return null;
        int mid = (start + end) / 2;
        return new TreeNode(nums[mid], createBinary(nums, start, mid), createBinary(nums, mid + 1, end));
    }
}
