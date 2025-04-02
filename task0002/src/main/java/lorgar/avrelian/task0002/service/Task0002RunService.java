package lorgar.avrelian.task0002.service;

import lorgar.avrelian.base.model.RunResult;
import lorgar.avrelian.base.model.TaskReport;
import lorgar.avrelian.base.model.TestValues;
import lorgar.avrelian.base.service.TaskRunService;
import lorgar.avrelian.base.util.TaskRunUtil;
import lorgar.avrelian.base.util.TopicNameUtil;
import lorgar.avrelian.task0002.model.Task0002;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

import java.util.List;
import java.util.Map;

@Service
public class Task0002RunService implements TaskRunService<int[][], int[]> {
    private static final TestValues<int[][], int[]> testValues = new TestValues<>(
            Map.of(
                    new int[][]{{2, 4, 3}, {5, 6, 4}}, new int[]{7, 0, 8},
                    new int[][]{{0}, {0}}, new int[]{0},
                    new int[][]{{9, 9, 9, 9, 9, 9, 9}, {9, 9, 9, 9}}, new int[]{8, 9, 9, 9, 0, 0, 0, 1}
            )
    );
    @Value("${task.number}")
    private int taskNumber;
    private final KafkaSender<String, Object> kafkaSender;

    public Task0002RunService(KafkaSender<String, Object> kafkaSender) {
        this.kafkaSender = kafkaSender;
    }

    @Override
    public TaskReport<int[][], int[]> runTask() {
        Task0002 t = new Task0002(testValues);
        TaskRunUtil<int[][], int[]> taskRunUtil = new TaskRunUtil<>(t);
        List<RunResult<int[][], int[]>> runResults = taskRunUtil.doTask(testValues);
        TaskReport<int[][], int[]> report = new TaskReport<>();
        report.setId(taskNumber);
        report.setResults(runResults);
        kafkaSender.send(
                        Mono.just(
                                SenderRecord.create(
                                        TopicNameUtil.formatName(taskNumber),
                                        0,
                                        System.currentTimeMillis(),
                                        String.valueOf(report.hashCode()),
                                        report,
                                        null
                                )
                        )
                )
                .subscribe();
        return report;
    }
}
