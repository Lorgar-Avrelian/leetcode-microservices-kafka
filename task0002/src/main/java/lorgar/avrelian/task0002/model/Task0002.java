package lorgar.avrelian.task0002.model;

import lorgar.avrelian.base.model.Task;
import lorgar.avrelian.base.model.TestValues;

import java.util.ArrayList;
import java.util.List;

public class Task0002 extends Task<int[][], int[]> {
    static boolean overflow = false;
    public Task0002(TestValues<int[][], int[]> testValues) {
        super(testValues);
    }

    @Override
    public int[] doTask(int[][] input) {
        ListNode[] nodes = arrayToListNodes(input);
        ListNode listNode = addTwoNumbers(nodes[0], nodes[1]);
        return listNodesToArray(listNode);
    }

    private int[] listNodesToArray(ListNode listNode) {
        List<Integer> list = new ArrayList<>();
        ListNode current = listNode;
        while (current.next != null) {
            list.add(current.val);
            current = current.next;
        }
        list.add(current.val);
        int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    private ListNode[] arrayToListNodes(int[][] input) {
        ListNode[] nodes = new ListNode[input.length];
        for (int i = 0; i < input.length; i++) {
            ListNode node = new ListNode();
            int[] ints = input[i];
            for (int j = ints.length - 1; j >= 0; j--) {
                if (j != ints.length - 1) {
                    ListNode newNode = new ListNode(ints[j]);
                    newNode.next = node;
                    node = newNode;
                } else {
                    node.val = ints[j];
                }
            }
            nodes[i] = node;
        }
        return nodes;
    }

    private static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 != null && l2 != null) {
            int l1Val = l1.val;
            int l2Val = l2.val;
            ListNode l1Next = l1.next;
            ListNode l2Next = l2.next;
            int sum = 0;
            if (overflow) {
                sum = l1Val + l2Val + 1;
                overflow = false;
            } else {
                sum = l1Val + l2Val;
            }
            if (sum > 9) {
                overflow = true;
                sum = sum % 10;
            }
            if (l1Next != null || l2Next != null) {
                return new ListNode(sum, addTwoNumbers(l1Next, l2Next));
            } else {
                if (overflow) {
                    overflow = false;
                    return new ListNode(sum, new ListNode(1));
                } else {
                    return new ListNode(sum);
                }
            }
        } else if (l2 == null && l1 != null) {
            int l1Val = l1.val;
            ListNode l1Next = l1.next;
            int sum = 0;
            if (overflow) {
                sum = l1Val + 1;
                overflow = false;
            } else {
                sum = l1Val;
            }
            if (sum > 9) {
                overflow = true;
                sum = sum % 10;
            }
            if (l1Next != null) {
                return new ListNode(sum, addTwoNumbers(l1Next, null));
            } else {
                if (overflow) {
                    overflow = false;
                    return new ListNode(sum, new ListNode(1));
                } else {
                    return new ListNode(sum);
                }
            }
        } else if (l1 == null && l2 != null) {
            int l2Val = l2.val;
            ListNode l2Next = l2.next;
            int sum = 0;
            if (overflow) {
                sum = l2Val + 1;
                overflow = false;
            } else {
                sum = l2Val;
            }
            if (sum > 9) {
                overflow = true;
                sum = sum % 10;
            }
            if (l2Next != null) {
                return new ListNode(sum, addTwoNumbers(null, l2Next));
            } else {
                if (overflow) {
                    overflow = false;
                    return new ListNode(sum, new ListNode(1));
                } else {
                    return new ListNode(sum);
                }
            }
        } else {
            if (overflow) {
                overflow = false;
                return new ListNode(1);
            }
            return null;
        }
    }
}
