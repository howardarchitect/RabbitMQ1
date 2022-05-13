package com.roark;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicExchangeConfig {

	@Autowired
	private AmqpAdmin amqpAdmin;

	@Value("${rabbitmq.topic.queue-1}")
	private String tq1;

	@Value("${rabbitmq.topic.queue-2}")
	private String tq2;
	
	@Value("${rabbitmq.topic.queue-3}")
	private String tq3;


	@Value("${rabbitmq.topic.exchange}")
	private String de;

	@Value("${rabbitmq.topic.pattern-1}")
	private String p1;

	@Value("${rabbitmq.topic.pattern-2}")
	private String p2;
	@Value("${rabbitmq.topic.pattern-3}")
	private String p3;

	Queue createQueue1() {
		return new Queue(tq1, true, false, false);
	}
	Queue createQueue2() {
		return new Queue(tq2, true, false, false);
	}
	Queue createQueue3() {
		return new Queue(tq3, true, false, false);
	}
	TopicExchange directExchange() {
		return new TopicExchange(de, true, false);
	}
	Binding db1() {
		return BindingBuilder
				.bind(createQueue1())
				.to(directExchange())
				.with(p1);
	}
	Binding db2() {
		return BindingBuilder
				.bind(createQueue2())
				.to(directExchange())
				.with(p2);
	}
	Binding db3() {
		return BindingBuilder
				.bind(createQueue3())
				.to(directExchange())
				.with(p3);
	}
	public AmqpTemplate defaultTemplate(ConnectionFactory cf, MessageConverter messageConverter) {

		RabbitTemplate rb = new RabbitTemplate(cf);
		rb.setMessageConverter(messageConverter);
		rb.setExchange(de);
		return rb;

	}

	@PostConstruct
	public void init() {
		amqpAdmin.declareQueue(createQueue1());
		amqpAdmin.declareQueue(createQueue2());
		amqpAdmin.declareQueue(createQueue3());
		
		amqpAdmin.declareExchange(directExchange());
		amqpAdmin.declareBinding(db1());
		amqpAdmin.declareBinding(db2());
		amqpAdmin.declareBinding(db3());
	}
}
