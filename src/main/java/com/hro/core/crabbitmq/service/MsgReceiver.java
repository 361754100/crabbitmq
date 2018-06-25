package com.hro.core.crabbitmq.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消息接受者
 */
@Component
public class MsgReceiver {

    private static Logger logger = LoggerFactory.getLogger(MsgReceiver.class);

    @RabbitListener(queues = "test.queue1")
    public String processQueue1(String msg) {
        logger.info("接收到来自test.queue1的消息 msg={}", msg);
        return "Queue1 Ack:"+ msg;
    }

    @RabbitListener(queues = "test.queue2")
    public String processQueue2(String msg) {
        logger.info("接收到来自test.queue2的消息 msg={}", msg);
        return "Queue2 Ack:"+ msg;
    }

}
