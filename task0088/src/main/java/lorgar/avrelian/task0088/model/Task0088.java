package lorgar.avrelian.task0088.model;

import lorgar.avrelian.base.model.Task;
import lorgar.avrelian.base.model.TestValues;

public class Task0088 extends Task<Object[], int[]> {
    public Task0088(TestValues<Object[], int[]> testValues) {
        super(testValues);
    }

    @Override
    public int[] doTask(Object[] input) {
        return merge((int[]) input[0], (Integer) input[1], (int[]) input[2], (Integer) input[3]);
    }

    private static int[] merge(int[] nums1, int m, int[] nums2, int n) {
        int[] merged = new int[m + n];
        int i = 0, j = 0, k = 0;
        while (i < m && j < n) {
            if (nums1[i] <= nums2[j]) {
                merged[k++] = nums1[i++];
            } else {
                merged[k++] = nums2[j++];
            }
        }
        while (i < m) {
            merged[k++] = nums1[i++];
        }
        while (j < n) {
            merged[k++] = nums2[j++];
        }
        System.arraycopy(merged, 0, nums1, 0, merged.length);
        return nums1;
    }
}
