package lorgar.avrelian.task0066.model;

import lorgar.avrelian.base.model.Task;
import lorgar.avrelian.base.model.TestValues;

public class Task0066 extends Task<int[], int[]> {

    public Task0066(TestValues<int[], int[]> testValues) {
        super(testValues);
    }

    @Override
    public int[] doTask(int[] input) {
        return plusOne(input);
    }

    private static int[] plusOne(int[] digits) {
        for (int i = digits.length - 1; i >= 0; i--) {
            if (digits[i] < 9) {
                digits[i]++;
                return digits;
            }
            digits[i] = 0;
        }
        int[] newDigits = new int[digits.length + 1];
        newDigits[0] = 1;
        return newDigits;
    }
}
