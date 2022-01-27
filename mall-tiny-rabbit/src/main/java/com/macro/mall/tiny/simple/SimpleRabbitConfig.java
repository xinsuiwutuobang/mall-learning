/*
 * Copyright 2015-2018 the original author or authors.
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
package com.macro.mall.tiny.simple;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * 简单模式
 * 简单模式是最简单的消息模式，它包含一个生产者、一个消费者和一个队列。
 * 生产者向队列里发送消息，消费者从队列中获取消息并消费
 */
@Configuration
public class SimpleRabbitConfig {

	/**
	 * 必须先声明队列，否则报错
	 * Failed to declare queue(s):[simple.hello1]
	 * @return
	 */
	@Bean
	public Queue hello() {
		return new Queue("simple.hello");
	}

	/**
	 * 消息发送者，集成RabbitTemplate
	 * @return
	 */
	@Bean
	public SimpleSender simpleSender(){
		return new SimpleSender();
	}

	/**
	 * 消息发送者，集成 RabbitListener RabbitHandler
	 * @return
	 */
	@Bean
	public SimpleReceiver simpleReceiver(){
		return new SimpleReceiver();
	}

}
