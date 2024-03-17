package xyz.shanmugavel.poc.sprigboot.kafkaprodcons.startp;

import java.time.LocalDateTime;

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
        kafkaTemplate.send(kafkaConfigProps.getTopic(), key, payload);
        log.info("Message Published to topic {} with key{} and value {}", kafkaConfigProps.getTopic(), key, payload);
    }

}
