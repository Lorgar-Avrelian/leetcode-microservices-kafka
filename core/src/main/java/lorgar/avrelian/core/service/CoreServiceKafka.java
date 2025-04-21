package lorgar.avrelian.core.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.annotation.PostConstruct;
import lorgar.avrelian.base.model.RunResult;
import lorgar.avrelian.core.config.KafkaConfig;
import lorgar.avrelian.core.dto.Report;
import lorgar.avrelian.core.dto.TaskCount;
import lorgar.avrelian.base.model.TaskReport;
import lorgar.avrelian.core.mapper.ReportMapper;
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
public class CoreServiceKafka implements CoreService {
    private static final Map<Integer, TaskReport> reports = new HashMap<>();
    @Value("${spring.kafka.core-topic.name}")
    private String coreTopic;
    @Value("${tasks}")
    private String tasks;
    private final KafkaSender<String, Object> kafkaSender;
    private final KafkaReceiver<String, Object> kafkaReceiver;
    private final ReportMapper reportMapper;

    public CoreServiceKafka(KafkaSender<String, Object> kafkaSender, KafkaReceiver<String, Object> kafkaReceiver, ReportMapper reportMapper) {
        this.kafkaSender = kafkaSender;
        this.kafkaReceiver = kafkaReceiver;
        this.reportMapper = reportMapper;
    }

    @PostConstruct
    public void init() {
        Gson json = new GsonBuilder().create();
        kafkaReceiver.receive()
                .subscribe(
                        record -> {
                            TaskReport report = json.fromJson(record.value().toString(), TaskReport.class);
                            System.out.println("Received: " + report);
                            reports.put(report.getId(), report);
                            record.receiverOffset().acknowledge();
                        }
                );
    }

    @Override
    public String[] getTasksNumbers() {
        String s = tasks.trim().replace(" ", "");
        return s.split(",");
    }

    @Override
    public TaskCount getTaskCount() {
        String s = tasks.trim().replace(" ", "");
        String[] split = s.split(",");
        int count = split.length;
        int[] tasks = new int[count];
        int success = 0;
        int fail = 0;
        for (int i = 0; i < count; i++) {
            tasks[i] = Integer.parseInt(split[i]);
            if (reports.containsKey(tasks[i])) {
                TaskReport report = reports.get(tasks[i]);
                List<RunResult> results = report.getResults();
                for (int j = 0; j < results.size(); j++) {
                    if (!results.get(j).getActual().equals(results.get(j).getExpected())) {
                        fail++;
                        break;
                    }
                }
            } else {
                getTaskReport(tasks[i]);
                i--;
            }
        }
        success = count - fail;
        return new TaskCount(count, success, fail);
    }

    @Override
    public Report getTaskReport(int id) {
        if (KafkaConfig.doneTasks.contains(id)) {
            Integer message = Integer.valueOf(id);
            kafkaSender.send(
                            Mono.just(
                                    SenderRecord.create(
                                            coreTopic,
                                            0,
                                            System.currentTimeMillis(),
                                            String.valueOf(message.hashCode()),
                                            message,
                                            null
                                    )
                            )
                    )
                    .subscribe();
            kafkaSender.send(
                            Mono.just(
                                    SenderRecord.create(
                                            coreTopic,
                                            1,
                                            System.currentTimeMillis(),
                                            String.valueOf(message.hashCode()),
                                            message,
                                            null
                                    )
                            )
                    )
                    .subscribe();
            kafkaSender.send(
                            Mono.just(
                                    SenderRecord.create(
                                            coreTopic,
                                            2,
                                            System.currentTimeMillis(),
                                            String.valueOf(message.hashCode()),
                                            message,
                                            null
                                    )
                            )
                    )
                    .subscribe();
            TaskReport report = reports.get(id);
            while (report == null || !report.isUpdated()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                report = reports.get(id);
            }
            reports.get(id).setUpdated(false);
            return reportMapper.taskReportToReport(report);
        } else {
            return null;
        }
    }
}
