package com.example.notificationservice.config;

import com.example.notificationservice.kafka.KafkaEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfigNotification {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ConsumerFactory<String, KafkaEvent> consumerFactoryBooking() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "car-group");
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "com.example.notificationservice.kafka");
        configProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE, KafkaEvent.class.getName());
        configProps.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);

        return new DefaultKafkaConsumerFactory<>(
                configProps,
                new StringDeserializer(),
                new JsonDeserializer<>(KafkaEvent.class)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, KafkaEvent> kafkaListenerContainerFactoryBooking() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryBooking());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, KafkaEvent> consumerFactoryCar() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "car-group");
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "com.example.notificationservice.kafka");
        configProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE, KafkaEvent.class.getName());
        configProps.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);

        return new DefaultKafkaConsumerFactory<>(
                configProps,
                new StringDeserializer(),
                new JsonDeserializer<>(KafkaEvent.class)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, KafkaEvent> kafkaListenerContainerFactoryCar() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryCar());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, KafkaEvent> consumerFactoryPayment() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "car-group");
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "com.example.notificationservice.kafka");
        configProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE, KafkaEvent.class.getName());
        configProps.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);

        return new DefaultKafkaConsumerFactory<>(
                configProps,
                new StringDeserializer(),
                new JsonDeserializer<>(KafkaEvent.class)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, KafkaEvent> kafkaListenerContainerFactoryPayment() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryPayment());
        return factory;
    }
}
