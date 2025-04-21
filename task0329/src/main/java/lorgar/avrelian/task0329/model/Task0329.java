package lorgar.avrelian.task0329.model;

import lorgar.avrelian.base.model.Task;
import lorgar.avrelian.base.model.TestValues;

import java.util.Arrays;
import java.util.stream.Stream;

public class Task0329 extends Task<int[][], Integer> {
    private static final int[][] directions = {
            {0, 1},     // right
            {1, 0},     // up
            {0, -1},    // left
            {-1, 0}     // down
    };

    public Task0329(TestValues<int[][], Integer> testValues) {
        super(testValues);
    }

    @Override
    public Integer doTask(int[][] input) {
        return longestIncreasingPath(input);
    }

    public int longestIncreasingPath(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return 0;
        if (matrix.length == 1 && matrix[0].length == 1 ||
                Stream.of(matrix).flatMapToInt(Arrays::stream).distinct().count() == 1) return 1;
        int height = matrix.length;
        int width = matrix[0].length;
        int size = height * width;
        int[][] rememberedLengths = new int[height][width];
        int result = 1;
        for (int i = 0; i < size; i++) {
            int length = getLongest(matrix, height, width, i / width, i % width, rememberedLengths);
            result = Math.max(length, result);
        }
        return result;
    }

    private static int getLongest(int[][] matrix, int height, int width, int i, int j, int[][] rememberedLengths) {
        if (rememberedLengths[i][j] > 0) return rememberedLengths[i][j];
        int result = 0;
        for (int[] direction : directions) {
            int x = i + direction[0];
            int y = j + direction[1];
            if (x >= 0 && y >= 0 && x < height && y < width && matrix[x][y] > matrix[i][j]) {
                result = Math.max(result, getLongest(matrix, height, width, x, y, rememberedLengths));
            }
        }
        rememberedLengths[i][j] = result + 1;
        return result + 1;
    }
}
