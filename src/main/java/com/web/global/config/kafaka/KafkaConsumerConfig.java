package com.web.global.config.kafaka;

import com.web.coupon.dto.IssueCouponRequest;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
public class KafkaConsumerConfig {
    @Bean
    public ConsumerFactory<String, IssueCouponRequest> consumerFactory() {
        Map<String, Object> config = new HashMap<>();

        //서버의 정보 추가
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        //그룹ID 정보 추가
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_1");

        //KeySerailizer 정보 추가
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        //ValueSerailizer 정보 추가
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(
                config,
                new StringDeserializer(),
                new JsonDeserializer<>(IssueCouponRequest.class) // spring-kafka 패키지
        );
    }

    //토픽으로 부터 메세지를 전달 받기 위한 카프카 리스너
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, IssueCouponRequest> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, IssueCouponRequest> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
