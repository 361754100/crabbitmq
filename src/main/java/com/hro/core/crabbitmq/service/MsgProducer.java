package com.hro.core.crabbitmq.service;

import com.hro.core.crabbitmq.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

/**
 * 消息生产者
 */
@Component
public class MsgProducer implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback{

    private static Logger logger = LoggerFactory.getLogger(MsgProducer.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String s) {
        if(ack) {
            logger.info("消息发送成功:{}", correlationData);
        } else {
            logger.info("消息发送失败:{}", s);
        }
    }

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        logger.info("发送失败:{}", message.getMessageProperties().getCorrelationId());
    }

    /**
     * 发送 topic 类型的消息
     * @param routingKey
     * @param msg
     */
    public String sendByTopic(String routingKey, String msg) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        logger.info("开始发送消息: routingKey={}, msg={}", new Object[]{routingKey, msg});
        Object resp = rabbitTemplate.convertSendAndReceive("topicExchange", routingKey, msg, correlationData);
        logger.info("结束消息发送：routingKey={}, msg={}", new Object[]{routingKey, msg});
        logger.info("消费者响应：resp={}", resp);
        return StringUtils.toString(resp);
    }

}
