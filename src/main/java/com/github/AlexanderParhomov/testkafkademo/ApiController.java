package com.github.AlexanderParhomov.testkafkademo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.AlexanderParhomov.testkafkademo.kafka.KafkaService;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final KafkaService kafkaService;

    private ImmutableList<SampleMessage> data = ImmutableList.of(
            new SampleMessage(1, "first testing message"),
            new SampleMessage(2, "second testing message"),
            new SampleMessage(3, "third testing message")
    );

    @Autowired
    public ApiController(KafkaService kafkaService) {this.kafkaService = kafkaService;}

    @PostMapping("/{topic}/produce/sample")
    public HttpStatus produceSample(@PathVariable String topic) {
        kafkaService.produceJson(
                topic,
                toJsonsList(data)
        );

        return HttpStatus.OK;
    }

    @PostMapping("/{topic}/produce")
    public HttpStatus produce(@PathVariable String topic, @RequestBody List<SampleMessage> input) {
        kafkaService.produceJson(
                topic,
                toJsonsList(input)
        );

        return HttpStatus.OK;
    }

    @GetMapping("/{topic}/consume/sample")
    public Collection<String> consumeSample(@PathVariable String topic) {
        return kafkaService.consumeJson(topic, data.size());
    }

    @GetMapping("/{topic}/consume/{userSize}")
    public Collection<String> consumeWithSize(@PathVariable String topic, @PathVariable String userSize) {
        int size = getSize(userSize);

        return kafkaService.consumeJson(topic, size);
    }



    private ObjectMapper mapper = new ObjectMapper();

    private SampleMessage getMessageFromJson(String json) {
        try {
            return mapper.readValue(json, SampleMessage.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int getSize(String size) {
        Objects.requireNonNull(size);

        return Integer.parseInt(size);
    }

    private static List<String> toJsonsList(List<SampleMessage> input) {
        return input.stream()
                    .map(SampleMessage::toJson)
                    .collect(Collectors.toList());
    }
}
