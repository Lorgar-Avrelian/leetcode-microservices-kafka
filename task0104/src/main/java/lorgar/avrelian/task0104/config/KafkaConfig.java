package lorgar.avrelian.task0104.config;

import lorgar.avrelian.base.util.TopicNameUtil;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.TopicConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.support.serializer.JsonSerializer;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class KafkaConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String servers;
    @Value("${task.number}")
    private int taskNumber;
    @Value("${topics}")
    private List<String> topics;

    @Bean
    public NewTopic coreTopic() {
        return TopicBuilder
                .name(TopicNameUtil.formatName(taskNumber))
                .partitions(3)
                .replicas(1)
                .config(
                        TopicConfig.RETENTION_MS_CONFIG, String.valueOf(Duration.ofDays(1).toMillis())
                )
                .build();
    }

    @Bean
    public SenderOptions<String, Object> kafkaSenderOptions() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return SenderOptions.create(props);
    }

    @Bean
    public KafkaSender<String, Object> kafkaSender() {
        return KafkaSender.create(kafkaSenderOptions());
    }

    @Bean
    public Map<String, Object> consumerProperties() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, TopicNameUtil.formatName(taskNumber));
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put("spring.json.trusted.packages", "*");
        return props;
    }

    @Bean
    public ReceiverOptions<String, Object> receiverOptions() {
        ReceiverOptions<String, Object> receiverOptions = ReceiverOptions
                .create(consumerProperties());
        return receiverOptions
                .subscription(topics)
                .addAssignListener(
                        partitions -> System.out.println("assigned partitions: " + partitions)
                )
                .addRevokeListener(
                        partitions -> System.out.println("revoked partitions: " + partitions)
                );
    }

    @Bean
    public KafkaReceiver<String, Object> kafkaReceiver() {
        return KafkaReceiver.create(receiverOptions());
    }
}
