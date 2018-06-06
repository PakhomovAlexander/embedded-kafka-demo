package com.github.AlexanderParhomov.testkafkademo.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;

/**
 * Service the can produce and consume from kafka
 */
public interface KafkaService {

    /**
     * Produce collection of strings with json format
     */
    public void produceJson(String topic, Collection<String> collection);

    /**
     * Consume one time from kafka topic and returns collection of strings in json format
     */
    public Collection<String> consumeJson(String topic, int size);

    /**
     * Start kafka server
     */
    public void startupKafkaServer();

    /**
     * Start kafka server wit custom configuration
     */
    public void startupKafkaServer(Map<String, Object> configuration);

    /**
     * Shutdown kafka bootstrap server and zookeeper server
     */
    public void shutdownKafkaServer();
}
