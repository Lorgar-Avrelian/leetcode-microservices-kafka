package lorgar.avrelian.task0003.model;

import lorgar.avrelian.base.model.Task;
import lorgar.avrelian.base.model.TestValues;

import java.util.ArrayDeque;

public class Task0003 extends Task<String, Integer> {
    public Task0003(TestValues<String, Integer> testValues) {
        super(testValues);
    }

    @Override
    public Integer doTask(String input) {
        return lengthOfLongestSubstring(input);
    }

    private static Integer lengthOfLongestSubstring(String s) {
        ArrayDeque<Character> queue = new ArrayDeque<>();
        int result = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (queue.contains(ch)) {
                while (!queue.isEmpty() && queue.peek() != ch) {
                    queue.remove();
                }
                queue.remove();
            }
            queue.add(ch);
            result = Math.max(result, queue.size());
        }
        return result;
    }
}
