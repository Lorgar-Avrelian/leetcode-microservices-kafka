package lorgar.avrelian.task0001.model;

import java.util.Arrays;
import java.util.Objects;

public class Input {
    private int[] array;
    private int target;

    public Input() {
    }

    public Input(int[] array, int target) {
        this.array = array;
        this.target = target;
    }

    public int[] getArray() {
        return array;
    }

    public void setArray(int[] array) {
        this.array = array;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Input input = (Input) o;
        return target == input.target && Objects.deepEquals(array, input.array);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(array), target);
    }

    @Override
    public String toString() {
        return "Input{" +
                "array=" + Arrays.toString(array) +
                ", target=" + target +
                '}';
    }
}
