package com.roark;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectExchangeConfig {

	@Autowired
	private AmqpAdmin amqpAdmin;

	@Value("${rabbitmq.direct.queue-1}")
	private String dq1;

	@Value("${rabbitmq.direct.queue-2}")
	private String dq2;

	@Value("${rabbitmq.direct.exchange}")
	private String de;

	@Value("${rabbitmq.direct.routing-key-2}")
	private String rk2;

	@Value("${rabbitmq.direct.routing-key-1}")
	private String rk1;

	Queue createQueue1() {
		return new Queue(dq1, true, false, false);
	}
	Queue createQueue2() {
		return new Queue(dq2, true, false, false);
	}
	DirectExchange directExchange() {
		return new DirectExchange(de, true, false);
	}
	Binding db1() {
		return BindingBuilder
				.bind(createQueue1())
				.to(directExchange())
				.with(rk1);
	}
	Binding db2() {
		return BindingBuilder
				.bind(createQueue2())
				.to(directExchange())
				.with(rk2);
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
		
		amqpAdmin.declareExchange(directExchange());
		amqpAdmin.declareBinding(db1());
		amqpAdmin.declareBinding(db2());
	}
}
