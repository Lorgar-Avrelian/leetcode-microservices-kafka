package lorgar.avrelian.core.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lorgar.avrelian.base.model.RunResult;
import lorgar.avrelian.core.dto.Report;
import lorgar.avrelian.core.dto.TaskCount;
import lorgar.avrelian.core.service.CoreService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@RestController
@Tag(name = "Core", description = "Core controller")
public class CoreController {
    private final CoreService coreService;

    public CoreController(@Qualifier(value = "coreServiceKafka") CoreService coreService) {
        this.coreService = coreService;
    }

    @GetMapping(path = "/stat")
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

    @GetMapping
    @Operation(
            summary = "Solved tasks",
            description = "Numbers of solved tasks",
            tags = "Tasks",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = String.class))
                            )
                    )
            }
    )
    public ResponseEntity<String[]> getNumbers() {
        String[] taskNumbers = coreService.getTasksNumbers();
        return ResponseEntity.ok(taskNumbers);
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
                                    schema = @Schema(implementation = Report.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Report.class)
                            )
                    )
            }
    )
    public ResponseEntity<Report> getTask(@RequestParam(name = "id", defaultValue = "1") int id) {
        Report taskReport = coreService.getTaskReport(id);
        if (taskReport != null) {
            return ResponseEntity.ok(taskReport);
        } else {
            Report emptyReport = new Report();
            emptyReport.setId(id);
            RunResult<String, String> emptyResult = new RunResult<>();
            emptyReport.setResults(new ArrayList<>(List.of(emptyResult)));
            return ResponseEntity.badRequest().body(emptyReport);
        }
    }
}
