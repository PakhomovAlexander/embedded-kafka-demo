package com.github.AlexanderParhomov.testkafkademo;

import com.github.embedded.kafka.unit.TestKafka;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EmbeddedKafkaTest {
    @Test
    public void kafkaStartAndGetPort() {
        try(TestKafka kafka = new TestKafka()) {
            assertNotNull(kafka.getConfiguration());
        }
    }
}
