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

import cn.hutool.core.thread.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.util.StopWatch;

/**
 * Created by macro on 2020/5/19.
 */
public class TopicReceiver {

	private static final Logger LOGGER = LoggerFactory.getLogger(TopicReceiver.class);

	@RabbitListener(queues = "#{topicQueue1.name}")
	public void receive1(String in){
		receive(in, 1);
	}

	@RabbitListener(queues = "#{topicQueue2.name}")
	public void receive2(String in){
		receive(in, 2);
	}

	/**
	 * 消费者从自己绑定的匿名队列中获取消息，由于该消费者可以从两个队列中获取并消费消息，
	 * 可以看做两个消费者，名称分别为instance 1和instance 2；
	 *
	 * 生产者往队列中发送包含不同路由键的消息，instance 1和instance 2分别获取到了匹配的消息。
	 *
	 * @param in
	 * @param receiver
	 */
	public void receive(String in, int receiver){
		StopWatch watch = new StopWatch();
		watch.start();
		LOGGER.info("instance {} [x] Received '{}'", receiver, in);
		doWork(in);
		watch.stop();
		LOGGER.info("instance {} [x] Done in {}s", receiver, watch.getTotalTimeSeconds());
	}

	private void doWork(String in){
		for (char ch : in.toCharArray()) {
			if (ch == '.') {
				ThreadUtil.sleep(1000);
			}
		}
	}

}
