package com.github.AlexanderParhomov.testkafkademo;

import com.github.AlexanderParhomov.testkafkademo.kafka.KafkaService;
import com.github.AlexanderParhomov.testkafkademo.kafka.TestKafkaServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {

    @Bean(destroyMethod = "shutdownKafkaServer")
    public KafkaService igniteClient() {
        KafkaService kafkaService = new TestKafkaServiceImpl();
        kafkaService.startupKafkaServer();

        return kafkaService;
    }
}

