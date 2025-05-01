package lorgar.avrelian.task0067.model;

import lorgar.avrelian.base.model.Task;
import lorgar.avrelian.base.model.TestValues;

public class Task0067 extends Task<String[], String> {
    public Task0067(TestValues<String[], String> testValues) {
        super(testValues);
    }

    @Override
    public String doTask(String[] input) {
        return addBinary(input[0], input[1]);
    }

    private static String addBinary(String a, String b) {
        if (a.length() < b.length()) return addBinary(b, a);
        StringBuilder sb = new StringBuilder();
        int aIndex = a.length() - 1, bIndex = b.length() - 1;
        int carry = 0;
        int sum;
        while (aIndex >= 0 && bIndex >= 0) {
            sum = (a.charAt(aIndex--) - '0') + (b.charAt(bIndex--) - '0') + carry;
            sb.insert(0, sum % 2);
            carry = sum / 2;
        }
        while (aIndex >= 0) {
            sum = a.charAt(aIndex--) - '0' + carry;
            sb.insert(0, sum % 2);
            carry = sum / 2;
        }
        if (carry > 0) {
            sb.insert(0, carry);
        }
        return sb.toString();
    }
}
