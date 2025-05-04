package lorgar.avrelian.base.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "ListNode", description = "ListNode model")
public class ListNode {
    @Schema(title = "Value", description = "Value")
    public int val;
    @Schema(title = "Next", description = "Next node")
    public ListNode next;

    public ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    @Override
    public String toString() {
        return "ListNode {" +
                "val = " + val +
                ", next = " + next +
                " }";
    }
}
