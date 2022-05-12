package com.roark;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class QueueObject {

	public QueueObject(String string, LocalDateTime now) {
		// TODO Auto-generated constructor stub
	}
	private String type;
	private LocalDateTime time;

}
