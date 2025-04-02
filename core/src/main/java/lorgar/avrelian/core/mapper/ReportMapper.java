package lorgar.avrelian.core.mapper;

import lorgar.avrelian.base.model.TaskReport;
import lorgar.avrelian.core.dto.Report;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReportMapper {
    @Mapping(target = "id", source = "taskReport.id")
    @Mapping(target = "results", source = "taskReport.results")
    Report taskReportToReport(TaskReport taskReport);
}
