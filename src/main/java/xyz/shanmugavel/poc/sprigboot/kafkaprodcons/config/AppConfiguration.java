package xyz.shanmugavel.poc.sprigboot.kafkaprodcons.config;

import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
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

        log.info("Kafka Producer config={}", kafkaConfigProps);
        return new DefaultKafkaProducerFactory<>(configProps);
    }


    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        //Bootstrap server is read fom spring.kafka.bootstrap-servers
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConfigProps.getConsumerGroup());
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaConfigProps.getKeySerializer());
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaConfigProps.getValueSerializer());
        configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaConfigProps.getOffset());
        log.info("Kafka Consumer Config={}", configProps);
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String>  kafkaConcurrentListenerContainerFactory (ConsumerFactory<String, String> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory; 
    }

    
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(@NonNull ProducerFactory<String, String> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }


}
