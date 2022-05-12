package com.roark;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class RabbitMq1Application {

	@Value("${spring.rabbitmq.host}")
	private String host;

	@Value("${spring.rabbitmq.port}")
	private Integer port;

	@Value("${spring.rabbitmq.virtual-host}")
	private String virtualHost;

	@Value("${spring.rabbitmq.username}")
	private String username;

	@Value("${spring.rabbitmq.password}")
	private String password;

	public static void main(String[] args) {
		SpringApplication.run(RabbitMq1Application.class, args);
	}

	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory ccf = new CachingConnectionFactory();
		ccf.setUsername(username);
		ccf.setPassword(password);
		ccf.setHost(host);
		ccf.setVirtualHost(virtualHost);
		ccf.setPort(port);
		return ccf;

	}

	@Bean
	public MessageConverter messageConverter() {
		ObjectMapper om = new ObjectMapper().findAndRegisterModules();
		return new Jackson2JsonMessageConverter(om);
	}

}
