package xyz.shanmugavel.poc.sprigboot.kafkaprodcons.consumer;

import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@KafkaListener(groupId ="ShanConsGroup1", topics = "shan-poc-topic-1")
@Slf4j
public class MyListener {


    @KafkaHandler(isDefault = true)
    public void defaultEchoMethod(Object message) {
        log.info("Received message>>>>>>>>>{}", message);
    }
}
