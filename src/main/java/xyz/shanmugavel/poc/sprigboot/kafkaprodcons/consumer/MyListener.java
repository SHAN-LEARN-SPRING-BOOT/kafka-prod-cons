package xyz.shanmugavel.poc.sprigboot.kafkaprodcons.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@KafkaListener(groupId ="ShanConsGroup1", topics = "shan-poc-topic-1")
@Slf4j
public class MyListener {


    @KafkaHandler(isDefault = true)
    public void defaultEchoMethod(ConsumerRecord<String, String> message) {
        log.info("Received message>>>>>>>>>{}={}", message.key(), message.value());
        log.info("Headers>>>>>>>>>{}", message.headers());
        message.headers().headers("X-Author").forEach(val -> log.info("Headers >>>>>>>>>X-Author={}", new String(val.value())));
    }
}
