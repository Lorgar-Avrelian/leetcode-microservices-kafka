package lorgar.avrelian.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@Schema(title = "Total count", description = "Total count of tasks solved")
public class TaskCount {
    @Schema(title = "Total", description = "Total count of tasks", defaultValue = "0")
    private int total;

    @Schema(title = "Success", description = "Total count of successfully completed tasks", defaultValue = "0")
    private int success;

    @Schema(title = "Failed", description = "Total count of failed tasks", defaultValue = "0")
    private int fail;

    public TaskCount() {
    }

    public TaskCount(int total, int success, int fail) {
        this.total = total;
        this.success = success;
        this.fail = fail;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFail() {
        return fail;
    }

    public void setFail(int fail) {
        this.fail = fail;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TaskCount count = (TaskCount) o;
        return total == count.total && success == count.success && fail == count.fail;
    }

    @Override
    public int hashCode() {
        return Objects.hash(total, success, fail);
    }

    @Override
    public String toString() {
        return "TaskCount{" +
                "total=" + total +
                ", success=" + success +
                ", fail=" + fail +
                '}';
    }
}
