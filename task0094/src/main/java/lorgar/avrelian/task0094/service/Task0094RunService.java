package lorgar.avrelian.task0094.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.annotation.PostConstruct;
import lorgar.avrelian.base.model.RunResult;
import lorgar.avrelian.base.model.TaskReport;
import lorgar.avrelian.base.model.TestValues;
import lorgar.avrelian.base.service.TaskRunService;
import lorgar.avrelian.base.util.TaskRunUtil;
import lorgar.avrelian.base.util.TopicNameUtil;
import lorgar.avrelian.task0094.model.Task0094;
import lorgar.avrelian.task0094.model.TreeNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class Task0094RunService implements TaskRunService<TreeNode, List<Integer>> {
    private static final TestValues<TreeNode, List<Integer>> testValues = new TestValues<>(
            Map.of(
                    new TreeNode(1, null, new TreeNode(2, new TreeNode(3), null)), new ArrayList<>(List.of(1, 3, 2)),
                    /*
                     *  1
                     *    \
                     *      2
                     *    /
                     *  3
                     * */
                    new TreeNode(1,
                            new TreeNode(2,
                                    new TreeNode(4),
                                    new TreeNode(5,
                                            new TreeNode(6),
                                            new TreeNode(7))),
                            new TreeNode(3,
                                    null,
                                    new TreeNode(8,
                                            new TreeNode(9),
                                            null))),
                    new ArrayList<>(List.of(4, 2, 6, 5, 7, 1, 3, 9, 8)),
                    /*
                     *             1
                     *         /       \
                     *       2           3
                     *     /   \          \
                     *    4      5         8
                     *         /   \      /
                     *        6     7    9
                     * */
                    new TreeNode(), new ArrayList<>(),
                    new TreeNode(1), new ArrayList<>(List.of(1))
            )
    );
    @Value("${task.number}")
    private int taskNumber;
    private final KafkaSender<String, Object> kafkaSender;
    private final KafkaReceiver<String, Object> kafkaReceiver;

    public Task0094RunService(KafkaSender<String, Object> kafkaSender, KafkaReceiver<String, Object> kafkaReceiver) {
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
    public TaskReport<TreeNode, List<Integer>> runTask() {
        Task0094 t = new Task0094(testValues);
        TaskRunUtil<TreeNode, List<Integer>> taskRunUtil = new TaskRunUtil<>(t);
        List<RunResult<TreeNode, List<Integer>>> runResults = taskRunUtil.doTask(testValues);
        TaskReport<TreeNode, List<Integer>> report = new TaskReport<>();
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
