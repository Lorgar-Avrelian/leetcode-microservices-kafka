package lorgar.avrelian.task0028.model;

import lorgar.avrelian.base.model.Task;
import lorgar.avrelian.base.model.TestValues;

public class Task0028 extends Task<String[], Integer> {
    public Task0028(TestValues<String[], Integer> testValues) {
        super(testValues);
    }

    @Override
    public Integer doTask(String[] input) {
        return strStr(input[0], input[1]);
    }

    private static int strStr(String haystack, String needle) {
        return haystack.indexOf(needle);
    }
}
