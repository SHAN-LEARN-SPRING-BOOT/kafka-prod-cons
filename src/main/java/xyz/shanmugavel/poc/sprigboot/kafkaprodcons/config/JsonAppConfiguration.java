package xyz.shanmugavel.poc.sprigboot.kafkaprodcons.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.lang.NonNull;

import lombok.extern.slf4j.Slf4j;
import xyz.shanmugavel.poc.sprigboot.kafkaprodcons.model.Greeting;

@Configuration
@Slf4j
public class JsonAppConfiguration {

    @Autowired
    private KafkaJsonConfigProps kafkaJsonConfigProps;

    @Bean("greetingProducerFactory")
    public ProducerFactory<String, Greeting> greetingProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaJsonConfigProps.getBootstrapUrl());
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,  kafkaJsonConfigProps.getKeySerializer());
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaJsonConfigProps.getValueSerializer());

        log.info("Kafka GreetingProducer config={}", kafkaJsonConfigProps);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean("greetingKafkaTemplate")
    public KafkaTemplate<String, Greeting> greetingKafkaTemplate(@Qualifier("greetingProducerFactory") @NonNull ProducerFactory<String, Greeting> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean("greetingConsumerFactory")
    public ConsumerFactory<String, Greeting> greetingConsumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        //Bootstrap server is read fom spring.kafka.bootstrap-servers
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaJsonConfigProps.getBootstrapUrl());
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaJsonConfigProps.getConsumerGroup());
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaJsonConfigProps.getKeyDeserializer());
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaJsonConfigProps.getValueDeserializer());
        configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaJsonConfigProps.getOffset());
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "xyz.shanmugavel.poc.sprigboot.kafkaprodcons.model");
        log.info("Kafka Greeting Consumer Config={}", configProps);
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean("greetingKafkaConcurrentListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, Greeting>  greetingKafkaConcurrentListenerContainerFactory (@Qualifier("greetingConsumerFactory") ConsumerFactory<String, Greeting> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, Greeting> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory; 
    }
}
