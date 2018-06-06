package com.github.AlexanderParhomov.testkafkademo;

import com.github.AlexanderParhomov.testkafkademo.kafka.KafkaService;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    KafkaService kafkaService;

    ImmutableList<SampleMessage> data = ImmutableList.of(
            new SampleMessage(1, "first message"),
            new SampleMessage(2, "second message"),
            new SampleMessage(3, "third message")
    );


    @PostMapping("/produce/all")
    public void produceAll() {
        kafkaService.produceJson("test-topic", data.stream()
                                                   .map(SampleMessage::toJson)
                                                   .collect(Collectors.toList()));
    }

    @GetMapping("/consume/all")
    public Collection<String> consumeAll() {
        return kafkaService.consumeJson("test-topic", data.size());
    }
}
