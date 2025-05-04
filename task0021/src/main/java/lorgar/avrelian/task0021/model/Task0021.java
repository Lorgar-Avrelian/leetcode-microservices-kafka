package lorgar.avrelian.task0021.model;

import lorgar.avrelian.base.model.ListNode;
import lorgar.avrelian.base.model.Task;
import lorgar.avrelian.base.model.TestValues;

public class Task0021 extends Task<ListNode[], ListNode> {
    public Task0021(TestValues<ListNode[], ListNode> testValues) {
        super(testValues);
    }

    @Override
    public ListNode doTask(ListNode[] input) {
        return mergeTwoLists(input[0], input[1]);
    }

    private static ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null && list2 == null) return null;
        if (list1 == null) return list2;
        if (list2 == null) return list1;
        ListNode result = new ListNode();
        ListNode current = result;
        ListNode left = list1;
        ListNode right = list2;
        while (left != null && right != null) {
            if (left.val < right.val) {
                current.val = left.val;
                if (left.next != null) {
                    current.next = new ListNode();
                    current = current.next;
                }
                left = left.next;
            } else {
                current.val = right.val;
                if (right.next != null) {
                    current.next = new ListNode();
                    current = current.next;
                }
                right = right.next;
            }
        }
        if (left != null) current.next = left;
        if (right != null) current.next = right;
        return result;
    }
}
