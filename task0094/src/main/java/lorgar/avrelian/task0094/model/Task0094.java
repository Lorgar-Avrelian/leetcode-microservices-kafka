package lorgar.avrelian.task0094.model;

import lorgar.avrelian.base.model.Task;
import lorgar.avrelian.base.model.TestValues;
import lorgar.avrelian.base.model.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class Task0094 extends Task<TreeNode, List<Integer>> {
    public Task0094(TestValues<TreeNode, List<Integer>> testValues) {
        super(testValues);
    }

    @Override
    public List<Integer> doTask(TreeNode input) {
        return inorderTraversal(input);
    }

    private static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        travel(root, result);
        return result;
    }

    private static void travel(TreeNode node, List<Integer> result) {
        if (node == null) return;
        travel(node.left, result);
        result.add(node.val);
        travel(node.right, result);
    }
}
