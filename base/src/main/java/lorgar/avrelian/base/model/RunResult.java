package lorgar.avrelian.base.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@Schema(title = "Test result", description = "Test run result")
public class RunResult<M, T> {
    @Schema(title = "Input", description = "Input value")
    private M input;
    @Schema(title = "Expected", description = "Expected output value")
    private T expected;
    @Schema(title = "Actual", description = "Actual output value")
    private T actual;

    public RunResult() {
    }

    public RunResult(M input, T expected, T actual) {
        this.input = input;
        this.expected = expected;
        this.actual = actual;
    }

    public M getInput() {
        return input;
    }

    public void setInput(M input) {
        this.input = input;
    }

    public T getExpected() {
        return expected;
    }

    public void setExpected(T expected) {
        this.expected = expected;
    }

    public T getActual() {
        return actual;
    }

    public void setActual(T actual) {
        this.actual = actual;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RunResult<?, ?> runResult = (RunResult<?, ?>) o;
        return Objects.equals(input, runResult.input) && Objects.equals(expected, runResult.expected) && Objects.equals(actual, runResult.actual);
    }

    @Override
    public int hashCode() {
        return Objects.hash(input, expected, actual);
    }

    @Override
    public String toString() {
        return "RunResult{" +
                "input=" + input +
                ", expected=" + expected +
                ", actual=" + actual +
                '}';
    }
}
