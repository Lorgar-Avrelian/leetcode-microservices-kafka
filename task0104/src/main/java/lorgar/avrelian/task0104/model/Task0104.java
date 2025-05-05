package lorgar.avrelian.task0104.model;

import lorgar.avrelian.base.model.Task;
import lorgar.avrelian.base.model.TestValues;
import lorgar.avrelian.base.model.TreeNode;

public class Task0104 extends Task<TreeNode, Integer> {
    public Task0104(TestValues<TreeNode, Integer> testValues) {
        super(testValues);
    }

    @Override
    public Integer doTask(TreeNode input) {
        return maxDepth(input);
    }

    private static int maxDepth(TreeNode root) {
        return travel(root, 0);
    }

    private static int travel(TreeNode node, int depth) {
        if (node == null) return depth;
        return Math.max(travel(node.left, depth + 1), travel(node.right, depth + 1));
    }
}
