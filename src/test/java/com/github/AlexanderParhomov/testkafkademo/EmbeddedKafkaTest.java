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
    private static final String TOPIC_NAME = "test-kafka";
    private static final long POLL_TIMEOUT = 100;
    private static final int IDLE_COUNT = 2;

    private List<SampleMessage> messages = ImmutableList.of(
            new SampleMessage(1, "first message"),
            new SampleMessage(2, "second message"),
            new SampleMessage(3, "third message")
    );

    private List<String> jsons = messages.stream()
                                         .map(SampleMessage::toJson)
                                         .collect(Collectors.toList());

    @Rule
    public TestKafkaRule kafkaRule = new TestKafkaRule();

    @Test
    public void messagesProducedAndConsumed() {
        KafkaUtils utils = new KafkaUtils(kafkaRule.getKafka().getConfiguration());

        utils.produce(StringSerializer.class, TOPIC_NAME, jsons);

        Collection<String> consumedMessages = utils.consume(StringDeserializer.class,
                                                            TOPIC_NAME,
                                                            POLL_TIMEOUT, IDLE_COUNT,
                                                            messages.size()
        );

        assertEquals(jsons, consumedMessages);
    }
}
