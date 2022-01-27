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
package com.macro.mall.tiny.direct;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * 路由模式
 * 路由模式是可以根据路由键选择性给多个消费者发送消息的模式，
 * 它包含一个生产者、两个消费者、两个队列和一个交换机。
 * 两个消费者同时绑定到不同的队列上去，两个队列通过路由键绑定到交换机上去，
 * 生产者发送消息到交换机，交换机通过路由键转发到不同队列，队列绑定的消费者接收并消费消息。
 */
@Configuration
public class DirectRabbitConfig {

    /**
     * 交换机
     * @return
     */
    @Bean
    public DirectExchange direct() {
        return new DirectExchange("exchange.direct");
    }

    /**
     * 匿名队列1
     * @return
     */
    @Bean
    public Queue directQueue1() {
        return new AnonymousQueue();
    }

    /**
     * 匿名队列2
     * @return
     */
    @Bean
    public Queue directQueue2() {
        return new AnonymousQueue();
    }

    /**
     * 队列1绑定到交换机且指定路由1
     * @param direct
     * @param directQueue1
     * @return
     */
    @Bean
    public Binding directBinding1a(DirectExchange direct, Queue directQueue1) {
        return BindingBuilder.bind(directQueue1).to(direct).with("orange");
    }

    /**
     * 队列1绑定到交换机且指定路由2
     * @param direct
     * @param directQueue1
     * @return
     */
    @Bean
    public Binding directBinding1b(DirectExchange direct, Queue directQueue1) {
        return BindingBuilder.bind(directQueue1).to(direct).with("black");
    }

    /**
     * 队列2绑定到交换机且指定路由3
     * @param direct
     * @param directQueue2
     * @return
     */
    @Bean
    public Binding directBinding2a(DirectExchange direct, Queue directQueue2) {
        return BindingBuilder.bind(directQueue2).to(direct).with("green");
    }

    /**
     * 队列2绑定到交换机且指定路由4
     * @param direct
     * @param directQueue2
     * @return
     */
    @Bean
    public Binding directBinding2b(DirectExchange direct, Queue directQueue2) {
        return BindingBuilder.bind(directQueue2).to(direct).with("black");
    }

    /**
     * 消息接受者
     * @return
     */
    @Bean
    public DirectReceiver receiver() {
        return new DirectReceiver();
    }


    /**
     * 消息发送者
     * @return
     */
    @Bean
    public DirectSender directSender() {
        return new DirectSender();
    }

}
