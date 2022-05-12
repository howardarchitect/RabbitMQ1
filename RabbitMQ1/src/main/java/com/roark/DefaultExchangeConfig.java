package com.roark;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultExchangeConfig {

	@Autowired
	private AmqpAdmin amqpAdmin;

	@Value("${rabbitmq.default.queue}")
	private String defaultQueue;

	Queue createQueue() {
		return new Queue(defaultQueue, true, false, false);

	}

	public AmqpTemplate defaultTemplate(ConnectionFactory cf, MessageConverter messageConverter) {

		RabbitTemplate rb = new RabbitTemplate(cf);
		rb.setMessageConverter(messageConverter);
		rb.setRoutingKey(defaultQueue);
		return rb;

	}

	@PostConstruct
	public void init() {
		amqpAdmin.declareQueue(createQueue());
	}
}
