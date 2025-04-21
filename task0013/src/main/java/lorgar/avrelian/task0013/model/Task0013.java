package lorgar.avrelian.task0013.model;

import lorgar.avrelian.base.model.Task;
import lorgar.avrelian.base.model.TestValues;

import java.util.HashMap;
import java.util.Map;

public class Task0013 extends Task<String, Integer> {
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
        if (!romanNumber.matches("^M{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$")) return -1;
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
