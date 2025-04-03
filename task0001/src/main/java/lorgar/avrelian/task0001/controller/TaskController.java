package lorgar.avrelian.task0001.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lorgar.avrelian.base.model.TaskReport;
import lorgar.avrelian.base.service.TaskRunService;
import lorgar.avrelian.task0001.model.Input;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Task № 1", description = "Task № 1 controller")
public class TaskController {
    private final TaskRunService<Input, int[]> taskRunService;

    public TaskController(TaskRunService<Input, int[]> taskRunService) {
        this.taskRunService = taskRunService;
    }

    @GetMapping
    @Operation(
            summary = "Run",
            description = "The result of solving a current task",
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
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Void.class)
                            )
                    )
            }
    )
    public ResponseEntity<TaskReport<Input, int[]>> getResult() {
        TaskReport<Input, int[]> report = taskRunService.runTask();
        return ResponseEntity.ok(report);
    }
}
