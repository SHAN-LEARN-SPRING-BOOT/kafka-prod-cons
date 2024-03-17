package xyz.shanmugavel.poc.sprigboot.kafkaprodcons.startp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import xyz.shanmugavel.poc.sprigboot.kafkaprodcons.config.KafkaConfigProps;

@Component
@Slf4j
public class PublishMessagesRunner implements CommandLineRunner {

    @Autowired
    private KafkaConfigProps kafkaConfigProps;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void run(String... args) throws Exception {
        log.info("Publishing message");
        String key = LocalDateTime.now().toString();
        String payload = "Hello..." + LocalDateTime.now().toString();
        kafkaTemplate.send(createProducerRecord(key, payload));
        log.info("Message Published to topic {} with key{} and value {}", kafkaConfigProps.getTopic(), key, payload);
    }   

    private ProducerRecord<String, String> createProducerRecord(String key, String payload) {
        List<Header> headers = new ArrayList<>();
        headers.add(new RecordHeader("X-Author", "Shanmugavel".getBytes()));
        headers.add(new RecordHeader("X-Version", "1.0".getBytes()));
        Integer patition = null;

        return new ProducerRecord<>(kafkaConfigProps.getTopic(), patition, key, payload, headers);
    }
    
}
