package lorgar.avrelian.base.util;

import lorgar.avrelian.base.model.RunResult;
import lorgar.avrelian.base.model.TaskReport;

import java.util.Arrays;
import java.util.List;

public class ConsoleUtil {
    public static void print(TaskReport<int[][], int[]> report) {
        List<RunResult<int[][], int[]>> results = report.getResults();
        for (int i = 0; i < results.size(); i++) {
            RunResult<int[][], int[]> runResult = results.get(i);
            int[][] input = runResult.getInput();
            System.out.println("Input:");
            for (int j = 0; j < input.length; j++) {
                System.out.println(Arrays.toString(input[j]));
            }
            System.out.println("Expected:");
            int[] expected = results.get(i).getExpected();
            System.out.println(Arrays.toString(expected));
            System.out.println("Actual:");
            int[] actual = results.get(i).getActual();
            System.out.println(Arrays.toString(actual));
            System.out.println();
        }
    }
}
