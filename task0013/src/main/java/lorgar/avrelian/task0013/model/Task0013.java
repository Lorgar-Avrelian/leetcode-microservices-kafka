package lorgar.avrelian.task0013.model;

import lorgar.avrelian.base.model.Task;
import lorgar.avrelian.base.model.TestValues;

import java.util.HashMap;
import java.util.Map;

public class Task0013 extends Task<String, Integer> {
    private static final char[] chars = new char[]{'I', 'V', 'X', 'L', 'C', 'D', 'M'};
    private static final char[] threeTimesChars = new char[]{'I', 'X', 'C', 'M'};
    private static final Map<Character, Integer> charValue = new HashMap<>(Map.of(
            'I', 1,
            'V', 5,
            'X', 10,
            'L', 50,
            'C', 100,
            'D', 500,
            'M', 1000
    ));

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
        String romanNumber = s.toUpperCase();
        Map<Character, Integer> charCount = new HashMap<>();
        for (char c : chars) {
            charCount.put(c, 0);
        }
        for (int i = 0; i < romanNumber.length(); i++) {
            switch (romanNumber.charAt(i)) {
                case 'I':
                case 'X':
                case 'C':
                case 'M':
                    if (charCount.get(romanNumber.charAt(i)) == 3) return -1;
                    charCount.put(romanNumber.charAt(i), charCount.get(romanNumber.charAt(i)) + 1);
                    if (i > 0 && romanNumber.charAt(i) != romanNumber.charAt(i - 1)) {
                        for (char threeTimesChar : threeTimesChars) {
                            if (threeTimesChar != romanNumber.charAt(i)) {
                                charCount.put(threeTimesChar, 0);
                            }
                        }
                    }
                    break;
                case 'V':
                case 'L':
                case 'D':
                    if (charCount.get(romanNumber.charAt(i)) == 1) return -1;
                    charCount.put(romanNumber.charAt(i), charCount.get(romanNumber.charAt(i)) + 1);
                    for (char threeTimesChar : threeTimesChars) {
                        charCount.put(threeTimesChar, 0);
                    }
                    break;
                default:
                    return -1;
            }
        }
        int result = 0;
        int i = 0;
        int previous = Integer.MAX_VALUE;
        while (i < romanNumber.length()) {
            int current = charValue.get(romanNumber.charAt(i));
            if (previous < current) {
                result += current - previous * 2;
            } else {
                result += current;
            }
            previous = current;
            i++;
        }
        return result;
    }
}
