package lorgar.avrelian.base.util;

import lorgar.avrelian.base.model.RunResult;
import lorgar.avrelian.base.model.TestValues;
import lorgar.avrelian.base.service.TaskExecutable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class TaskRunUtil<M, T> {
    private final TaskExecutable<M, T> task;

    public TaskRunUtil(TaskExecutable<M, T> task) {
        this.task = task;
    }

    private T run(M input) {
        return task.doTask(input);
    }

    public List<RunResult<M, T>> doTask(TestValues<M, T> testValues) {
        if (testValues == null) return null;
        AtomicReference<List<RunResult<M, T>>> results = new AtomicReference<>(new ArrayList<>());
        for (M input : testValues.getTestMap().keySet()) {
            RunResult<M, T> result = new RunResult<>();
            result.setInput(input);
            result.setExpected(testValues.getTestMap().get(input));
            result.setActual(run(input));
            results.get().add(result);
        }
        return results.get();
    }
}
