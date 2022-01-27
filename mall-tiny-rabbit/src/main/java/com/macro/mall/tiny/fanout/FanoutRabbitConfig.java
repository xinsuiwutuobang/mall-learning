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
package com.macro.mall.tiny.fanout;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * 发布订阅模式
 * 发布/订阅模式是指同时向多个消费者发送消息的模式（类似广播的形式），
 * 它包含一个生产者、两个消费者、两个队列和一个交换机。
 * 两个消费者同时绑定到不同的队列上去，两个队列绑定到交换机上去，
 * 生产者通过发送消息到交换机，所有消费者接收并消费消息。
 *
 * 场景：
 * 一个操作触发多个动作，每个动作为一个队列
 */
@Configuration
public class FanoutRabbitConfig {
    /**
     * 交换机
     * @return
     */
    @Bean
    public FanoutExchange fanout() {
        return new FanoutExchange("exchange.fanout");
    }

    /**
     * 匿名队列1
     * @return
     */
    @Bean
    public Queue fanoutQueue1() {
        return new AnonymousQueue();
    }

    /**
     * 匿名队列2
     * @return
     */
    @Bean
    public Queue fanoutQueue2() {
        return new AnonymousQueue();
    }

    /**
     * 匿名队列1绑定到交换机
     * @param fanout
     * @param fanoutQueue1
     * @return
     */
    @Bean
    public Binding fanoutBinding1(FanoutExchange fanout, Queue fanoutQueue1) {
        return BindingBuilder.bind(fanoutQueue1).to(fanout);
    }

    /**
     * 匿名队列2绑定到交换机
     * @param fanout
     * @param fanoutQueue1
     * @return
     */
    @Bean
    public Binding fanoutBinding2(FanoutExchange fanout, Queue fanoutQueue2) {
        return BindingBuilder.bind(fanoutQueue2).to(fanout);
    }

    /**
     * 消息接收者
     * @return
     */
    @Bean
    public FanoutReceiver fanoutReceiver() {
        return new FanoutReceiver();
    }

    /**
     * 消息发送者
     * @return
     */
    @Bean
    public FanoutSender fanoutSender() {
        return new FanoutSender();
    }

}
