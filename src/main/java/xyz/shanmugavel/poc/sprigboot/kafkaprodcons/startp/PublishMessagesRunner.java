package xyz.shanmugavel.poc.sprigboot.kafkaprodcons.startp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import xyz.shanmugavel.poc.sprigboot.kafkaprodcons.config.KafkaConfigProps;
import xyz.shanmugavel.poc.sprigboot.kafkaprodcons.config.KafkaJsonConfigProps;
import xyz.shanmugavel.poc.sprigboot.kafkaprodcons.model.Greeting;

@Component
@Slf4j
public class PublishMessagesRunner implements CommandLineRunner {

    @Autowired
    private KafkaConfigProps kafkaConfigProps;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

   
    @Autowired
    private KafkaJsonConfigProps kafkaJsonConfigProps;

    @Autowired
    @Qualifier("greetingKafkaTemplate")
    private KafkaTemplate<String, Greeting> kafkaGreetingTemplate;

    @Override
    public void run(String... args) throws Exception {

        publishStringMessage();
        publishGreetingMessage();
    }   

    private void publishStringMessage() {
        log.info("Publishing message");
        String key = LocalDateTime.now().toString();
        String payload = "Hello..." + LocalDateTime.now().toString();

        List<Header> headers = new ArrayList<>();
        headers.add(new RecordHeader("X-Author", "Shanmugavel".getBytes()));
        headers.add(new RecordHeader("X-Version", "1.0".getBytes()));
        Integer patition = null;
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(kafkaConfigProps.getTopic(), patition, key, payload, headers);

        kafkaTemplate.send(producerRecord);

        log.info("Message Published to topic {} with key{} and value {}", kafkaConfigProps.getTopic(), key, payload);
    }

    private void publishGreetingMessage() {
        log.info("Publishing Greeting message");
        String key = LocalDateTime.now().toString();
        Greeting payload = new Greeting();
        payload.setMsg(LocalDateTime.now().toString() +">>> Hello ");
        payload.setName("Shanmugavel");
        List<Header> headers = new ArrayList<>();
        headers.add(new RecordHeader("X-Author", "Shanmugavel".getBytes()));
        headers.add(new RecordHeader("X-Version", "2.0".getBytes()));
        Integer patition = null;
        ProducerRecord<String, Greeting> producerRecord = new ProducerRecord<>(kafkaJsonConfigProps.getTopic(), patition, key, payload, headers);

        kafkaGreetingTemplate.send(producerRecord);
        log.info("Message Published to topic {} with key{} and value {}", kafkaJsonConfigProps.getTopic(), key, payload);
    }

    

    
    
}
