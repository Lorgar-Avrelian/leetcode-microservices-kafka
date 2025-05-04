package lorgar.avrelian.task0101.model;

import lorgar.avrelian.base.model.Task;
import lorgar.avrelian.base.model.TestValues;
import lorgar.avrelian.base.model.TreeNode;

public class Task0101 extends Task<TreeNode, Boolean> {
    public Task0101(TestValues<TreeNode, Boolean> testValues) {
        super(testValues);
    }

    @Override
    public Boolean doTask(TreeNode input) {
        return isSymmetric(input);
    }

    private static boolean isSymmetric(TreeNode root) {
        return symmetric(root.left, root.right);
    }

    private static boolean symmetric(TreeNode left, TreeNode right) {
        if (left == null && right == null) return true;
        if (left == null || right == null) return false;
        return left.val == right.val && symmetric(left.left, right.right) && symmetric(left.right, right.left);
    }
}
