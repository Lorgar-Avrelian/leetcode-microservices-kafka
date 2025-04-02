package lorgar.avrelian.base.model;

import lorgar.avrelian.base.service.TaskExecutable;
import lorgar.avrelian.base.util.TaskRunUtil;

import java.util.List;

public abstract class Task<K, V> implements TaskExecutable<K, V> {
    public TestValues<K, V> testValues;

    public Task(TestValues<K, V> testValues) {
        this.testValues = testValues;
    }

    public void setTestValues(TestValues<K, V> testValues) {
        this.testValues = testValues;
    }

    public List<RunResult<K, V>> getRunResults() {
        TaskRunUtil<K, V> runUtil = new TaskRunUtil<>(this);
        return runUtil.doTask(this.testValues);
    }
}
