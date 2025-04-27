package lorgar.avrelian.task0009.model;

import lorgar.avrelian.base.model.Task;
import lorgar.avrelian.base.model.TestValues;

public class Task0009 extends Task<Integer, Boolean> {
    public Task0009(TestValues<Integer, Boolean> testValues) {
        super(testValues);
    }

    @Override
    public Boolean doTask(Integer input) {
        return isPalindrome(input);
    }

    private static boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        } else {
            int original = x;
            int reversed = 0;
            while (original != 0) {
                reversed = reversed * 10 + original % 10;
                original = original / 10;
            }
            return reversed == x;
        }
    }
}
