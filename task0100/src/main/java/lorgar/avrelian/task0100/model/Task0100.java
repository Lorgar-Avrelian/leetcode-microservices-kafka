package lorgar.avrelian.task0100.model;

import lorgar.avrelian.base.model.Task;
import lorgar.avrelian.base.model.TestValues;
import lorgar.avrelian.base.model.TreeNode;

public class Task0100 extends Task<TreeNode[], Boolean> {
    public Task0100(TestValues<TreeNode[], Boolean> testValues) {
        super(testValues);
    }

    @Override
    public Boolean doTask(TreeNode[] input) {
        return isSameTree(input[0], input[1]);
    }

    private static boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;
        return p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
}
