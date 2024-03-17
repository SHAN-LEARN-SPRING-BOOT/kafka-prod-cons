package xyz.shanmugavel.poc.sprigboot.kafkaprodcons.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "app.kafka.config.poc")
@Data
public class KafkaConfigProps {

    private String bootstrapUrl;
    private String keySerializer;
    private String valueSerializer;
    private String topic;
    private String consumerGroup;
}
