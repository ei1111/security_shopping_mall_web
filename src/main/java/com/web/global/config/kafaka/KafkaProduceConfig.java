package com.web.global.config.kafaka;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaProduceConfig {

    //Producr 인스턴스를 생성하는데 필요한 설정값 설정
    @Bean
    public ProducerFactory<String, Long> producerFactory() {
        Map<String, Object> config = new HashMap<>();

        //서버의 정보 추가
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        //KeySerailizer 정보 추가
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        //ValueSerailizer 정보 추가
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, LongSerializer.class);

        return new DefaultKafkaProducerFactory<>(config);
    }

    // 카프카 topic에 데이터를 전송할 kafka 템플릿 생성
    // 카프카 템플릿을 이용하여 topic에 데이터를 전송할 producer를 만들어야 한다.
    @Bean
    public KafkaTemplate<String, Long> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
