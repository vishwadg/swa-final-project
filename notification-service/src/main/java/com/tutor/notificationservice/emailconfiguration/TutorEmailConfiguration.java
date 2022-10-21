package com.tutor.notificationservice.emailconfiguration;

import com.tutor.notificationservice.entity.TutorEmail;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@EnableAutoConfiguration
@PropertySource(value = {"classpath:application.properties"})
public class TutorEmailConfiguration {

        @Value("${spring.mail.host}")
        private String mailServerHost;

        @Value("${spring.mail.port}")
        private Integer mailServerPort;

        @Value("${spring.mail.username}")
        private String mailServerUsername;

        @Value("${spring.mail.password}")
        private String mailServerPassword;

        @Value("${spring.mail.properties.mail.smtp.auth}")
        private String mailServerAuth;

        @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
        private String mailServerStartTls;

        @Bean
        public JavaMailSender getJavaMailSender() {
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost(mailServerHost);
            mailSender.setPort(mailServerPort);
            mailSender.setUsername(mailServerUsername);
            mailSender.setPassword(mailServerPassword);
            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.auth", mailServerAuth);
            props.put("mail.smtp.starttls.enable", mailServerStartTls);
            props.put("mail.debug", "true");
            return mailSender;
        }
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    public Map<String, Object>producerConfig()
    {
        // Create a map of a string
        // and object
        Map<String, Object> config = new HashMap<>();

        config.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "127.0.0.1:9092");

        config.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);

        config.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_DOC, JsonDeserializer.class);

        return config;
                //new DefaultKafkaProducerFactory<>(config);
    }
//    @Bean
//    public ConsumerFactory<String, TutorEmail> emailLoadConsumerFactory() {
//        Map<String, Object> config = new HashMap<>();
//        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
//        config.put(ConsumerConfig.GROUP_ID_CONFIG, "clevertap_group");
//        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
//
//        return new DefaultKafkaConsumerFactory<>
//                (config, new StringDeserializer(), new JsonDeserializer<>(TutorEmail.class));
//    }
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, TutorEmail> empPayloadKafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, TutorEmail> factory =
//                new ConcurrentKafkaListenerContainerFactory();
//        factory.setConsumerFactory(emailLoadConsumerFactory());
//        return factory;
//    }
@Bean
public <T> ProducerFactory<String, T> producerFactory(){
    return new DefaultKafkaProducerFactory<>(producerConfig());
}

    @Primary
    @Bean
    public <T> KafkaTemplate<String, T> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }
    }
