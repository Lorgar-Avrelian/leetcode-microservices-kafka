package lorgar.avrelian.task0020.model;

import lorgar.avrelian.base.model.Task;
import lorgar.avrelian.base.model.TestValues;

import java.util.Stack;

public class Task0020 extends Task<String, Boolean> {
    public Task0020(TestValues<String, Boolean> testValues) {
        super(testValues);
    }

    @Override
    public Boolean doTask(String input) {
        return isValid(input);
    }

    private static boolean isValid(String s) {
        if (s == null || s.isEmpty() || s.length() % 2 != 0) return false;
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            switch (c) {
                case '{':
                case '[':
                case '(':
                    stack.push(c);
                    break;
                case '}':
                    if (stack.isEmpty() || !stack.pop().equals('{')) return false;
                    break;
                case ']':
                    if (stack.isEmpty() || !stack.pop().equals('[')) return false;
                    break;
                case ')':
                    if (stack.isEmpty() || !stack.pop().equals('(')) return false;
                    break;
                default:
                    return false;
            }
        }
        return stack.isEmpty();
    }
}
