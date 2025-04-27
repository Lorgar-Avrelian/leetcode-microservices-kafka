package lorgar.avrelian.task0014.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.annotation.PostConstruct;
import lorgar.avrelian.base.model.RunResult;
import lorgar.avrelian.base.model.TaskReport;
import lorgar.avrelian.base.model.TestValues;
import lorgar.avrelian.base.service.TaskRunService;
import lorgar.avrelian.base.util.TaskRunUtil;
import lorgar.avrelian.base.util.TopicNameUtil;
import lorgar.avrelian.task0014.model.Task0014;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

import java.util.List;
import java.util.Map;

@Service
public class Task0014RunService implements TaskRunService<String[], String> {
    private static final TestValues<String[], String> testValues = new TestValues<>(
            Map.of(
                    new String[] {"flower","flow","flight"}, "fl",
                    new String[] {"dog","racecar","car"}, ""
            )
    );
    @Value("${task.number}")
    private int taskNumber;
    private final KafkaSender<String, Object> kafkaSender;
    private final KafkaReceiver<String, Object> kafkaReceiver;

    public Task0014RunService(KafkaSender<String, Object> kafkaSender, KafkaReceiver<String, Object> kafkaReceiver) {
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
    public TaskReport<String[], String> runTask() {
        Task0014 t = new Task0014(testValues);
        TaskRunUtil<String[], String> taskRunUtil = new TaskRunUtil<>(t);
        List<RunResult<String[], String>> runResults = taskRunUtil.doTask(testValues);
        TaskReport<String[], String> report = new TaskReport<>();
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
