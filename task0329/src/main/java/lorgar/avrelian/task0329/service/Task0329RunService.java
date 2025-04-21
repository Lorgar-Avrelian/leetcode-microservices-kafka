package lorgar.avrelian.task0329.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.annotation.PostConstruct;
import lorgar.avrelian.base.model.RunResult;
import lorgar.avrelian.base.model.TaskReport;
import lorgar.avrelian.base.model.TestValues;
import lorgar.avrelian.base.service.TaskRunService;
import lorgar.avrelian.base.util.TaskRunUtil;
import lorgar.avrelian.base.util.TopicNameUtil;
import lorgar.avrelian.task0329.model.Task0329;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

import java.util.List;
import java.util.Map;

@Service
public class Task0329RunService implements TaskRunService<int[][], Integer> {
    private static final TestValues<int[][], Integer> testValues = new TestValues<>(
            Map.of(
                    new int[][]{{9, 9, 4}, {6, 6, 8}, {2, 1, 1}}, Integer.valueOf(4),
                    new int[][]{{3, 4, 5}, {3, 2, 6}, {2, 2, 1}}, Integer.valueOf(4),
                    new int[][]{{1}}, Integer.valueOf(1),
                    new int[][]{{}}, Integer.valueOf(0),
                    new int[][]{{3, 2, 1}}, Integer.valueOf(3),
                    new int[][]{{3}, {2}, {1}}, Integer.valueOf(3),
                    new int[][]{{9, 8, 7}, {4, 5, 6}, {3, 2, 1}}, Integer.valueOf(9),
                    new int[][]{{10, 8, 5}, {10, 5, 4}}, Integer.valueOf(4),
                    new int[][]{{1, 1}, {1, 1}}, Integer.valueOf(1),
                    new int[][]{
                            {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
                            {19, 18, 17, 16, 15, 14, 13, 12, 11, 10},
                            {20, 21, 22, 23, 24, 25, 26, 27, 28, 29},
                            {39, 38, 37, 36, 35, 34, 33, 32, 31, 30},
                            {40, 41, 42, 43, 44, 45, 46, 47, 48, 49},
                            {59, 58, 57, 56, 55, 54, 53, 52, 51, 50},
                            {60, 61, 62, 63, 64, 65, 66, 67, 68, 69},
                            {79, 78, 77, 76, 75, 74, 73, 72, 71, 70},
                            {80, 81, 82, 83, 84, 85, 86, 87, 88, 89},
                            {99, 98, 97, 96, 95, 94, 93, 92, 91, 90},
                            {100, 101, 102, 103, 104, 105, 106, 107, 108, 109},
                            {119, 118, 117, 116, 115, 114, 113, 112, 111, 110},
                            {120, 121, 122, 123, 124, 125, 126, 127, 128, 129},
                            {139, 138, 137, 136, 135, 134, 133, 132, 131, 130},
                            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                    }, Integer.valueOf(140)
            )
    );
    @Value("${task.number}")
    private int taskNumber;
    private final KafkaSender<String, Object> kafkaSender;
    private final KafkaReceiver<String, Object> kafkaReceiver;

    public Task0329RunService(KafkaSender<String, Object> kafkaSender, KafkaReceiver<String, Object> kafkaReceiver) {
        this.kafkaSender = kafkaSender;
        this.kafkaReceiver = kafkaReceiver;
    }

    @PostConstruct
    public void init() {
        Gson json = new GsonBuilder().create();
        kafkaReceiver.receive()
                .subscribe(
                        record -> {
                            Integer id = json.fromJson(record.value().toString(), Integer.class);
                            if (id != null && id == taskNumber) {
                                runTask();
                            }
                            record.receiverOffset().acknowledge();
                        }
                );
    }

    @Override
    public TaskReport<int[][], Integer> runTask() {
        Task0329 t = new Task0329(testValues);
        TaskRunUtil<int[][], Integer> taskRunUtil = new TaskRunUtil<>(t);
        List<RunResult<int[][], Integer>> runResults = taskRunUtil.doTask(testValues);
        TaskReport<int[][], Integer> report = new TaskReport<>();
        report.setId(taskNumber);
        report.setResults(runResults);
        report.setUpdated(true);
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
        kafkaSender.send(
                        Mono.just(
                                SenderRecord.create(
                                        TopicNameUtil.formatName(taskNumber),
                                        1,
                                        System.currentTimeMillis(),
                                        String.valueOf(report.hashCode()),
                                        report,
                                        null
                                )
                        )
                )
                .subscribe();
        kafkaSender.send(
                        Mono.just(
                                SenderRecord.create(
                                        TopicNameUtil.formatName(taskNumber),
                                        2,
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
