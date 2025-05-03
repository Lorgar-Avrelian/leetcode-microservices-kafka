package lorgar.avrelian.task0070.model;

import lorgar.avrelian.base.model.Task;
import lorgar.avrelian.base.model.TestValues;

public class Task0070 extends Task<Integer, Integer> {
    public Task0070(TestValues<Integer, Integer> testValues) {
        super(testValues);
    }

    @Override
    public Integer doTask(Integer input) {
        return climbStairs(input);
    }

    private static int climbStairs(int n) {
        switch (n) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            default:
                int prev = 2;
                int prevPrev = 1;
                int cur = prev + prevPrev;
                int i = 2;
                while (i != n) {
                    cur = prev + prevPrev;
                    prevPrev = prev;
                    prev = cur;
                    i++;
                }
                return cur;
        }
    }
}
