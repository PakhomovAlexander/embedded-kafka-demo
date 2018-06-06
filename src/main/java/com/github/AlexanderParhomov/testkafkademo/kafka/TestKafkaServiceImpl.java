package com.github.AlexanderParhomov.testkafkademo.kafka;

import com.github.embedded.kafka.unit.TestKafka;
import com.github.embedded.kafka.unit.utils.KafkaUtils;
import com.google.common.collect.ImmutableMap;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class TestKafkaServiceImpl implements KafkaService {

    public static final Logger log = LoggerFactory.getLogger(TestKafkaServiceImpl.class);

    private static final Class<StringSerializer> JSON_SERIALIZER_CLASS = StringSerializer.class;
    private static final Class<StringDeserializer> JSON_DESERIALIZER_CLASS = StringDeserializer.class;

    private static final long POLL_TIMEOUT = 100;
    private static final int IDLE_COUNT = 1;


    private TestKafka kafka;
    private KafkaUtils utils;

    @Override
    public void produceJson(String topic, Collection<String> collection) {
        Objects.requireNonNull(kafka, "Start kafka bootstrap server up before producing anything!");

        utils.produce(JSON_SERIALIZER_CLASS, topic, collection);
    }

    @Override
    public Collection<String> consumeJson(String topic, int size) {
        Objects.requireNonNull(kafka, "Start kafka bootstrap server up before consuming anything!");

        return utils.consume(JSON_DESERIALIZER_CLASS, topic, POLL_TIMEOUT, IDLE_COUNT, size);
    }

    @Override
    public void startupKafkaServer() {
        startupKafkaServer(ImmutableMap.of());
    }

    @Override
    public void startupKafkaServer(Map<String, Object> configuration) {
        kafka = new TestKafka(configuration);
        utils = new KafkaUtils(kafka.getConfiguration());

        log.info("TestKafka server started and KafkaUtils are ready to produce/consume a data");
    }

    @Override
    public void shutdownKafkaServer() {
        kafka.close();
        log.info("TestKafka server shut down");
    }
}
