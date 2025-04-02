package lorgar.avrelian.base.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Objects;

@Schema(title = "Task report", description = "Task solving report")
public class TaskReport<M, T> {
    @Schema(title = "Task ID", description = "ID of the LeetCode task", defaultValue = "0")
    private int id;
    @Schema(title = "Task results", description = "Results of task solving tests")
    private List<RunResult<M, T>> results;

    public TaskReport() {
    }

    public TaskReport(int id, List<RunResult<M, T>> results) {
        this.id = id;
        this.results = results;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<RunResult<M, T>> getResults() {
        return results;
    }

    public void setResults(List<RunResult<M, T>> results) {
        this.results = results;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TaskReport<?, ?> report = (TaskReport<?, ?>) o;
        return id == report.id && Objects.equals(results, report.results);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, results);
    }

    @Override
    public String toString() {
        return "TaskReport{" +
                "id=" + id +
                ", results=" + results +
                '}';
    }
}
