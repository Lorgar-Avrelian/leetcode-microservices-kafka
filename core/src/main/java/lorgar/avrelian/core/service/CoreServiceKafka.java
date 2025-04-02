package lorgar.avrelian.core.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.annotation.PostConstruct;
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
import java.util.Map;

@Service
public class CoreServiceKafka implements CoreService {
    private static final Map<Integer, TaskReport> reports = new HashMap<>();
    @Value("${spring.kafka.core-topic.name}")
    private String coreTopic;
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
                            reports.put(report.getId(), report);
                            record.receiverOffset().acknowledge();
                        }
                );
    }

    @Override
    public TaskCount getTaskCount() {
        return new TaskCount();
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
