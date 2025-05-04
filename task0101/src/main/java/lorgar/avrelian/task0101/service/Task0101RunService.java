package lorgar.avrelian.task0101.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.annotation.PostConstruct;
import lorgar.avrelian.base.model.RunResult;
import lorgar.avrelian.base.model.TaskReport;
import lorgar.avrelian.base.model.TestValues;
import lorgar.avrelian.base.model.TreeNode;
import lorgar.avrelian.base.service.TaskRunService;
import lorgar.avrelian.base.util.TaskRunUtil;
import lorgar.avrelian.base.util.TopicNameUtil;
import lorgar.avrelian.task0101.model.Task0101;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

import java.util.List;
import java.util.Map;

@Service
public class Task0101RunService implements TaskRunService<TreeNode, Boolean> {
    private static final TestValues<TreeNode, Boolean> testValues = new TestValues<>(
            Map.of(
                    new TreeNode(1,
                            new TreeNode(2,
                                    new TreeNode(3),
                                    new TreeNode(4)),
                            new TreeNode(2,
                                    new TreeNode(4),
                                    new TreeNode(3))),
                    true,
                    /*
                    *       1
                    *     /   \
                    *    2     2
                    *   / \   / \
                    *  3   4 4   3
                    * */
                    new TreeNode(1,
                            new TreeNode(2,
                                    null,
                                    new TreeNode(3)),
                            new TreeNode(2,
                                    null,
                                    new TreeNode(3))),
                    false
                    /*
                    *        1
                    *      /   \
                    *     2     2
                    *      \     \
                    *        3     3
                    * */
            )
    );
    @Value("${task.number}")
    private int taskNumber;
    private final KafkaSender<String, Object> kafkaSender;
    private final KafkaReceiver<String, Object> kafkaReceiver;

    public Task0101RunService(KafkaSender<String, Object> kafkaSender, KafkaReceiver<String, Object> kafkaReceiver) {
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
    public TaskReport<TreeNode, Boolean> runTask() {
        Task0101 t = new Task0101(testValues);
        TaskRunUtil<TreeNode, Boolean> taskRunUtil = new TaskRunUtil<>(t);
        List<RunResult<TreeNode, Boolean>> runResults = taskRunUtil.doTask(testValues);
        TaskReport<TreeNode, Boolean> report = new TaskReport<>();
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
