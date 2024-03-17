package xyz.shanmugavel.poc.sprigboot.kafkaprodcons.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import xyz.shanmugavel.poc.sprigboot.kafkaprodcons.model.Greeting;

@Component
@KafkaListener(groupId ="ShanJsonConsGroup1", topics = "shan-poc-json-topic", containerFactory = "greetingKafkaConcurrentListenerContainerFactory")
@Slf4j
public class JsonTopicListener {

    @KafkaHandler(isDefault = true)
    public void defaultEchoMethod(ConsumerRecord<String, Greeting> message) {
        log.info("Received JSON message>>>>>>>>>{}={}", message.key(), message.value());
        log.debug("Headers>>>>>>>>>{}", message.headers());
        message.headers().headers("X-Version").forEach(val -> log.info("Headers >>>>>>>>>X-Version={}", new String(val.value())));
    }

}
