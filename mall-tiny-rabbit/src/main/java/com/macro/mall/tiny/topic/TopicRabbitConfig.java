/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.macro.mall.tiny.topic;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * 通配符模式
 * 通配符模式是可以根据路由键匹配规则选择性给多个消费者发送消息的模式，
 * 它包含一个生产者、两个消费者、两个队列和一个交换机。
 * 两个消费者同时绑定到不同的队列上去，两个队列通过路由键匹配规则绑定到交换机上去，
 * 生产者发送消息到交换机，交换机通过路由键匹配规则转发到不同队列，队列绑定的消费者接收并消费消息。
 *
 * *：只能匹配一个单词；
 * #：可以匹配零个或多个单词。
 */
@Configuration
public class TopicRabbitConfig {

    /**
     * 交换机
     * @return
     */
    @Bean
    public TopicExchange topic() {
        return new TopicExchange("exchange.topic");
    }

    /**
     * 匿名队列1
     * @return
     */
    @Bean
    public Queue topicQueue1() {
        return new AnonymousQueue();
    }

    /**
     * 匿名队列2
     * @return
     */
    @Bean
    public Queue topicQueue2() {
        return new AnonymousQueue();
    }

    /**
     * 队列1绑定到交换机且指定通配符路由1
     * @param topic
     * @param topicQueue1
     * @return
     */
    @Bean
    public Binding topicBinding1a(TopicExchange topic, Queue topicQueue1) {
        return BindingBuilder.bind(topicQueue1).to(topic).with("*.orange.*");
    }

    /**
     * 队列1绑定到交换机且指定通配符路由2
     * @param topic
     * @param topicQueue1
     * @return
     */
    @Bean
    public Binding topicBinding1b(TopicExchange topic, Queue topicQueue1) {
        return BindingBuilder.bind(topicQueue1).to(topic).with("*.*.rabbit");
    }

    /**
     * 队列2绑定到交换机且指定通配符路由3
     * @param topic
     * @param topicQueue2
     * @return
     */
    @Bean
    public Binding topicBinding2a(TopicExchange topic, Queue topicQueue2) {
        return BindingBuilder.bind(topicQueue2).to(topic).with("lazy.#");
    }

    /**
     * 消息接收
     * @return
     */
    @Bean
    public TopicReceiver topicReceiver() {
        return new TopicReceiver();
    }

    /**
     * 消息发送
     * @return
     */
    @Bean
    public TopicSender topicSender() {
        return new TopicSender();
    }

}
