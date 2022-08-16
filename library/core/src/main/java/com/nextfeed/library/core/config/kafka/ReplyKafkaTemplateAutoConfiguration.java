package com.nextfeed.library.core.config.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

import javax.annotation.PostConstruct;
import java.util.UUID;


@RequiredArgsConstructor
@Configuration
public class ReplyKafkaTemplateAutoConfiguration {

    private final ConfigurableListableBeanFactory beanFactory;
    private final ApplicationContext applicationContext;
    private final ReplyConfigProperties replyConfigProperties;


    private <K, V, R> ReplyingKafkaTemplate replyingKafkaTemplate(String groupId, String replyTopic, ProducerFactory<K, V> pf, ConcurrentKafkaListenerContainerFactory<K, R> factory) {
        ConcurrentMessageListenerContainer<K, R> replyContainer = factory.createContainer(replyTopic);
        replyContainer.getContainerProperties().setMissingTopicsFatal(false);
        replyContainer.getContainerProperties().setGroupId(groupId);
        return new ReplyingKafkaTemplate<>(pf, replyContainer);
    }

    private  <K, R> KafkaTemplate<K, R> replyTemplate(ProducerFactory<K, R> pf, ConcurrentKafkaListenerContainerFactory<K, R> factory) {
        KafkaTemplate<K, R> kafkaTemplate = new KafkaTemplate<>(pf);
        factory.getContainerProperties().setMissingTopicsFatal(false);
        factory.setReplyTemplate(kafkaTemplate);
        return kafkaTemplate;
    }

    private  <K, V, R> String randomUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    private void registerReplyKafkaTemplate(String beanName, String groupId, String replyTopic){
        var producerFactory = applicationContext.getBean(ProducerFactory.class);
        var containerFactory = applicationContext.getBean(ConcurrentKafkaListenerContainerFactory.class);

        beanFactory.registerSingleton(beanName, this.replyingKafkaTemplate(groupId, replyTopic, producerFactory, containerFactory));
        beanFactory.registerSingleton(randomUUID(), this.replyTemplate(producerFactory, containerFactory));
    }

    @PostConstruct
    public void init(){
        if (replyConfigProperties.getRelays() != null){
            for (var relay: replyConfigProperties.getRelays().values()) {
                registerReplyKafkaTemplate(relay.getBeanName(), relay.getGroupId(), relay.getReplyTopic());
            }
        }
    }


}
