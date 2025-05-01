package lorgar.avrelian.task0069.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.annotation.PostConstruct;
import lorgar.avrelian.base.model.RunResult;
import lorgar.avrelian.base.model.TaskReport;
import lorgar.avrelian.base.model.TestValues;
import lorgar.avrelian.base.service.TaskRunService;
import lorgar.avrelian.base.util.TaskRunUtil;
import lorgar.avrelian.base.util.TopicNameUtil;
import lorgar.avrelian.task0069.model.Task0069;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

import java.util.List;
import java.util.Map;

@Service
public class Task0069RunService implements TaskRunService<Integer, Integer> {
    private static final TestValues<Integer, Integer> testValues = new TestValues<>(
            Map.of(
                    4, 2,
                    8, 2,
                    2147395600, 46340
            )
    );
    @Value("${task.number}")
    private int taskNumber;
    private final KafkaSender<String, Object> kafkaSender;
    private final KafkaReceiver<String, Object> kafkaReceiver;

    public Task0069RunService(KafkaSender<String, Object> kafkaSender, KafkaReceiver<String, Object> kafkaReceiver) {
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
    public TaskReport<Integer, Integer> runTask() {
        Task0069 t = new Task0069(testValues);
        TaskRunUtil<Integer, Integer> taskRunUtil = new TaskRunUtil<>(t);
        List<RunResult<Integer, Integer>> runResults = taskRunUtil.doTask(testValues);
        TaskReport<Integer, Integer> report = new TaskReport<>();
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
