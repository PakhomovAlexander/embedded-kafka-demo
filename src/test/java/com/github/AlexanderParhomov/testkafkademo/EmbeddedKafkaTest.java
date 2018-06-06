package com.github.AlexanderParhomov.testkafkademo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.embedded.kafka.unit.rule.TestKafkaRule;
import com.github.embedded.kafka.unit.utils.KafkaUtils;
import com.google.common.collect.ImmutableList;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmbeddedKafkaTest {
    List<SampleMessage> messages = ImmutableList.of(
            new SampleMessage(1, "first message"),
            new SampleMessage(2, "second message"),
            new SampleMessage(3, "third message")
    );

    @Rule
    public TestKafkaRule kafkaRule = new TestKafkaRule();

    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void messagesProducedAndConsumed() {
        KafkaUtils utils = new KafkaUtils(kafkaRule.getKafka().getConfiguration());
        utils.produce(
                StringSerializer.class,
                "test-kafka",
                messages.stream()
                        .map(SampleMessage::toJson)
                        .collect(Collectors.toList())
        );

        Collection<String> consumedMessages = utils.consume(StringDeserializer.class,
                                                            "test-kafka",
                                                            100, 2, messages.size()
        );
        List<SampleMessage> messagesFromKafka = consumedMessages.stream()
                                                                .map(this::getMessageFromJson)
                                                                .collect(Collectors.toList());

        assertEquals(messages, messagesFromKafka);
    }

    private SampleMessage getMessageFromJson(String json) {
        try {
            return mapper.readValue(json, SampleMessage.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
