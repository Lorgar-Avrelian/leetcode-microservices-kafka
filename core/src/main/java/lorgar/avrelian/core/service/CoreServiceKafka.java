package lorgar.avrelian.core.service;

import lorgar.avrelian.core.dto.TaskCount;
import lorgar.avrelian.base.model.TaskReport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

@Service
public class CoreServiceKafka implements CoreService {
    @Value("${spring.kafka.core-topic.name}")
    private String coreTopic;
    private final KafkaSender<String, Object> kafkaSender;

    public CoreServiceKafka(KafkaSender<String, Object> kafkaSender) {
        this.kafkaSender = kafkaSender;
    }

    @Override
    public TaskCount getTaskCount() {
        return new TaskCount();
    }

    @Override
    public TaskReport getTaskReport(int id) {
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
        return taskReport;
    }
}
