package com.hro.core.crabbitmq.controller;

import com.hro.core.crabbitmq.service.MsgProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/msgpull")
public class MsgPullController {

    @Autowired
    private MsgProducer producer;

    @RequestMapping(value = "/topic", method = RequestMethod.POST)
    public String pullTopicMsg(@RequestParam String routingKey, @RequestParam String msg) {
        String resp = producer.sendByTopic(routingKey, msg);
        return "消息发送结果："+ resp;
    }
}
