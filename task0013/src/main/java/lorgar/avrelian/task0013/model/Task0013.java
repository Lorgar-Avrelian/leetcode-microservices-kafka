package lorgar.avrelian.task0013.model;

import lorgar.avrelian.base.model.Task;
import lorgar.avrelian.base.model.TestValues;

import java.util.HashMap;
import java.util.Map;

public class Task0013 extends Task<String, Integer> {
    private static final char[] chars = new char[]{'I', 'V', 'X', 'L', 'C', 'D', 'M'};
    private static final char[] threeTimesChars = new char[]{'I', 'X', 'C', 'M'};

    public Task0013(TestValues<String, Integer> testValues) {
        super(testValues);
    }

    @Override
    public Integer doTask(String input) {
        return romanToInt(input);
    }

    private int romanToInt(String s) {
        if (s == null) return -1;
        if (s.isBlank()) return 0;
        String upperS = s.toUpperCase();
        Map<Character, Integer> charCount = new HashMap<>();
        for (char c : chars) {
            charCount.put(c, 0);
        }
        for (int i = 0; i < upperS.length(); i++) {
            switch (upperS.charAt(i)) {
                case 'I':
                case 'X':
                case 'C':
                case 'M':
                    if (charCount.get(upperS.charAt(i)) == 3) return -1;
                    charCount.put(upperS.charAt(i), charCount.get(upperS.charAt(i)) + 1);
                    if (i > 0 && upperS.charAt(i) != upperS.charAt(i - 1)) {
                        for (char threeTimesChar : threeTimesChars) {
                            if (threeTimesChar != upperS.charAt(i)) {
                                charCount.put(threeTimesChar, 0);
                            }
                        }
                    }
                    break;
                case 'V':
                case 'L':
                case 'D':
                    if (charCount.get(upperS.charAt(i)) == 1) return -1;
                    charCount.put(upperS.charAt(i), charCount.get(upperS.charAt(i)) + 1);
                    for (char threeTimesChar : threeTimesChars) {
                        charCount.put(threeTimesChar, 0);
                    }
                    break;
                default:
                    return -1;
            }
        }
        int n = upperS.length();
        int i = 0;
        int result = 0;
        int previous = Integer.MAX_VALUE;
        while (i < n) {
            int current;
            switch (upperS.charAt(i)) {
                case 'I':
                    current = 1;
                    break;
                case 'V':
                    current = 5;
                    break;
                case 'X':
                    current = 10;
                    break;
                case 'L':
                    current = 50;
                    break;
                case 'C':
                    current = 100;
                    break;
                case 'D':
                    current = 500;
                    break;
                case 'M':
                    current = 1000;
                    break;
                default:
                    return -1;
            }
            if (previous < current) {
                result += current - 2 * previous;
            } else {
                result += current;
            }
            previous = current;
            i++;
        }
        return result;
    }
}
