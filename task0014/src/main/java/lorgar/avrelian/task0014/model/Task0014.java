package lorgar.avrelian.task0014.model;

import lorgar.avrelian.base.model.Task;
import lorgar.avrelian.base.model.TestValues;

public class Task0014 extends Task<String[], String> {
    public Task0014(TestValues<String[], String> testValues) {
        super(testValues);
    }

    @Override
    public String doTask(String[] input) {
        return longestCommonPrefix(input);
    }

    private static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) return "";
        int shortestLength = Integer.MAX_VALUE;
        String shortestWord = "";
        for (int i = 0; i < strs.length; i++) {
            if (strs[i].length() < shortestLength) {
                shortestLength = strs[i].length();
                shortestWord = strs[i];
            }
        }
        StringBuilder sb = new StringBuilder(shortestWord);
        while (!sb.isEmpty()) {
            boolean found = true;
            for (String str : strs) {
                if (!str.startsWith(sb.toString())) {
                    found = false;
                    break;
                }
            }
            if (found) {
                break;
            } else {
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        return sb.toString();
    }
}
