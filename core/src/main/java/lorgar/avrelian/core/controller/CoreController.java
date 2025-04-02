package lorgar.avrelian.core.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lorgar.avrelian.core.dto.TaskCount;
import lorgar.avrelian.core.service.CoreService;
import lorgar.avrelian.base.model.TaskReport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Core", description = "Core controller")
public class CoreController {
    private final CoreService coreService;

    public CoreController(@Qualifier(value = "coreServiceKafka") CoreService coreService) {
        this.coreService = coreService;
    }

    @GetMapping
    @Operation(
            summary = "Statistic",
            description = "Total statistics of solved tasks",
            tags = "Tasks",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TaskCount.class)
                            )
                    )
            }
    )
    public ResponseEntity<TaskCount> getTotal() {
        TaskCount taskCount = coreService.getTaskCount();
        return ResponseEntity.ok(taskCount);
    }

    @PostMapping
    @Operation(
            summary = "Task",
            description = "The result of solving a certain task",
            tags = "Tasks",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TaskReport.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TaskReport.class)
                            )
                    )
            }
    )
    public ResponseEntity<TaskReport> getTask(@RequestParam(name = "id", defaultValue = "1") int id) {
        TaskReport taskReport = coreService.getTaskReport(id);
        return ResponseEntity.ok(taskReport);
    }
}
