package com.github.AlexanderParhomov.testkafkademo;

import com.github.AlexanderParhomov.testkafkademo.kafka.KafkaService;
import com.github.AlexanderParhomov.testkafkademo.kafka.TestKafkaServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {

    @Bean(destroyMethod = "shutdownKafkaServer")
    public KafkaService kafkaService() {
        KafkaService kafkaService = new TestKafkaServiceImpl();
        kafkaService.startupKafkaServer();

        return kafkaService;
    }
}

