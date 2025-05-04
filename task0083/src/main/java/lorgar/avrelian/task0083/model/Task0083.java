package lorgar.avrelian.task0083.model;

import lorgar.avrelian.base.model.ListNode;
import lorgar.avrelian.base.model.Task;
import lorgar.avrelian.base.model.TestValues;

public class Task0083 extends Task<ListNode, ListNode> {
    public Task0083(TestValues<ListNode, ListNode> testValues) {
        super(testValues);
    }

    @Override
    public ListNode doTask(ListNode input) {
        return deleteDuplicates(input);
    }

    private static ListNode deleteDuplicates(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode current = head;
        while (current.next != null) {
            if (current.val == current.next.val) {
                current.next = current.next.next;
            } else {
                current = current.next;
            }
        }
        return head;
    }
}
