package com.roark;

import java.time.LocalDateTime;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

	@Autowired
	private AmqpTemplate defaultExchange;

	@PostMapping("default")
	public ResponseEntity<?> sendMessageWithDefaultExchange() {

		QueueObject queueObject = new QueueObject("default", LocalDateTime.now());
		defaultExchange.convertAndSend(queueObject);
		return ResponseEntity.ok(true);

	}

}
