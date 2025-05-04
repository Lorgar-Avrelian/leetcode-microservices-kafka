package lorgar.avrelian.base.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@Schema(title = "TreeNode", description = "TreeNode model")
public class TreeNode {
    @Schema(title = "Value", description = "Value")
    public int val;
    @Schema(title = "Left", description = "Left node")
    public TreeNode left;
    @Schema(title = "Right", description = "Right node")
    public TreeNode right;

    public TreeNode() {
    }

    public TreeNode(int val) {
        this.val = val;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TreeNode treeNode = (TreeNode) o;
        return val == treeNode.val && Objects.equals(left, treeNode.left) && Objects.equals(right, treeNode.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(val, left, right);
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "val=" + val +
                ", left=" + left +
                ", right=" + right +
                '}';
    }
}
