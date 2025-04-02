package lorgar.avrelian.base.service;

import lorgar.avrelian.base.model.TaskReport;

public interface TaskRunService<M, T> {
    TaskReport<M, T> runTask();
}
