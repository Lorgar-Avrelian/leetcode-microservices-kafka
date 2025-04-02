package lorgar.avrelian.core.service;

import lorgar.avrelian.core.dto.TaskCount;
import lorgar.avrelian.base.model.TaskReport;

public interface CoreService {
    TaskCount getTaskCount();

    TaskReport getTaskReport(int id);
}
