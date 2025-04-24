package lorgar.avrelian.task0437.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.annotation.PostConstruct;
import lorgar.avrelian.base.model.RunResult;
import lorgar.avrelian.base.model.TaskReport;
import lorgar.avrelian.base.model.TestValues;
import lorgar.avrelian.base.service.TaskRunService;
import lorgar.avrelian.base.util.TaskRunUtil;
import lorgar.avrelian.base.util.TopicNameUtil;
import lorgar.avrelian.task0437.model.Task0437;
import lorgar.avrelian.task0437.model.TreeNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Task0437RunService implements TaskRunService<Map<TreeNode, Integer>, Integer> {
    private static final TestValues<Map<TreeNode, Integer>, Integer> testValues = new TestValues<>(
            Map.of(
                    new HashMap<>(Map.of(
                            new TreeNode(10,
                                    new TreeNode(5,
                                            new TreeNode(3,
                                                    new TreeNode(3), new TreeNode(-2)),
                                            new TreeNode(2,
                                                    null, new TreeNode(1))),
                                    new TreeNode(-3,
                                            null, new TreeNode(11))),
                            8
                    )), 3,
                    /*
                    *               10
                    *             /    \
                    *            5     -3
                    *          /   \      \
                    *         3     2      11
                    *       /   \     \
                    *      3     -2    1
                    * */
                    new HashMap<>(Map.of(
                            new TreeNode(5,
                                    new TreeNode(4,
                                            new TreeNode(11, new TreeNode(7), new TreeNode(2)), null),
                                    new TreeNode(8,
                                            new TreeNode(13), new TreeNode(4,
                                            new TreeNode(5), new TreeNode(1)))
                            ),
                            22
                    )),
                    3
                    /*
                     *                5
                     *             /    \
                     *            4      8
                     *          /       /  \
                     *         11      13   4
                     *       /   \        /   \
                     *      7     2      5     1
                     * */
            )
    );
    @Value("${task.number}")
    private int taskNumber;
    private final KafkaSender<String, Object> kafkaSender;
    private final KafkaReceiver<String, Object> kafkaReceiver;

    public Task0437RunService(KafkaSender<String, Object> kafkaSender, KafkaReceiver<String, Object> kafkaReceiver) {
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
    public TaskReport<Map<TreeNode, Integer>, Integer> runTask() {
        Task0437 t = new Task0437(testValues);
        TaskRunUtil<Map<TreeNode, Integer>, Integer> taskRunUtil = new TaskRunUtil<>(t);
        List<RunResult<Map<TreeNode, Integer>, Integer>> runResults = taskRunUtil.doTask(testValues);
        TaskReport<Map<TreeNode, Integer>, Integer> report = new TaskReport<>();
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
