package lorgar.avrelian.task0058.model;

import lorgar.avrelian.base.model.Task;
import lorgar.avrelian.base.model.TestValues;

public class Task0058 extends Task<String, Integer> {

    public Task0058(TestValues<String, Integer> testValues) {
        super(testValues);
    }

    @Override
    public Integer doTask(String input) {
        return lengthOfLastWord(input);
    }

    private static int lengthOfLastWord(String s) {
        String[] split = s.trim().split(" ");
        return split[split.length - 1].length();
    }
}
