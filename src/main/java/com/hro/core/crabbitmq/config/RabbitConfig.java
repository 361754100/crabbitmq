package com.hro.core.crabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    // 队列1
    @Bean
    public Queue queue1() {
        /**
         * 队列名：Test Queue1
         * 持久化：true
         */
        return new Queue("test.queue1", true);
    }

    // 队列2
    @Bean
    public Queue queue2() {
        /**
         * 队列名：Test Queue2
         * 持久化：true
         */
        return new Queue("test.queue2", true);
    }

    /**
     * topic 交换器通过模式匹配分配消息的路由键属性，将路由键和某个模式进行匹配，此时队列需要绑定到一个模式上。
     * 它将路由键和绑定键的字符串切分成单词，这些单词之间用点隔开。
     * 它同样也会识别两个通配符：符号“#”和符号“”。#匹配0个或多个单词，匹配不多不少一个单词。
     * @return
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topicExchange");
    }

    /**
     * direct 交换器
     * 消息中的路由键（routing key）如果和 Binding 中的 binding key 一致， 交换器就将消息发到对应的队列中。
     * 路由键与队列名完全匹配，如果一个队列绑定到交换机要求路由键为“dog”，则只转发 routing key 标记为“dog”的消息，不会转发“dog.puppy”，也不会转发“dog.guard”等等。
     * 它是完全匹配、单播的模式。
     * @return
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("directExchange");
    }


    /**
     * 每个发到 fanout 类型交换器的消息都会分到所有绑定的队列上去。
     * fanout 交换器不处理路由键，只是简单的将队列绑定到交换器上，每个发送到交换器的消息都会被转发到与该交换器绑定的所有队列上。
     * 很像子网广播，每台子网内的主机都获得了一份复制的消息。fanout 类型转发消息是最快的。
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    /**
     * 把消息队列跟交换器绑定，设置路由键
     * @return
     */
    @Bean
    public Binding binding1() {
        String routingKey = "test.queue1";
        return BindingBuilder.bind(queue1()).to(topicExchange()).with(routingKey);
    }

    /**
     * 把消息队列跟交换器绑定，设置路由键
     * @return
     */
    @Bean
    public Binding binding2() {
        String routingKey = "test.queue#";
        return BindingBuilder.bind(queue2()).to(topicExchange()).with(routingKey);
    }

}
