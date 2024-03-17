package xyz.shanmugavel.poc.sprigboot.kafkaprodcons.config;

import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.lang.NonNull;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Configuration
@Slf4j
public class AppConfiguration {

    @Autowired
    private KafkaConfigProps kafkaConfigProps;

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfigProps.getBootstrapUrl());
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,  kafkaConfigProps.getKeySerializer());
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaConfigProps.getValueSerializer());

        log.info("Kaka config={}", kafkaConfigProps);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(@NonNull ProducerFactory<String, String> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}