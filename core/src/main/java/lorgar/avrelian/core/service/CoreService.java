package lorgar.avrelian.core.service;

import lorgar.avrelian.core.dto.Report;
import lorgar.avrelian.core.dto.TaskCount;

public interface CoreService {
    TaskCount getTaskCount();

    Report getTaskReport(int id);
}
