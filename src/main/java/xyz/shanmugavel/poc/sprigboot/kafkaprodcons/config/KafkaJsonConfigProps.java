package xyz.shanmugavel.poc.sprigboot.kafkaprodcons.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "app.kafka.config.json-poc")
@Data
public class KafkaJsonConfigProps {
    private String bootstrapUrl;
    private String keySerializer;
    private String valueSerializer;
    private String topic;
    private String consumerGroup;
    private String offset;
    private String keyDeserializer;
    private String valueDeserializer;
}
