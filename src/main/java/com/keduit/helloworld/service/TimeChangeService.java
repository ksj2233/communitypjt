package com.keduit.helloworld.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public interface TimeChangeService {
	
	final int SEC = 60;
	final int MIN = 60;
	final int HOUR = 24;
	final int DAY = 30;
	final int MONTH = 12;

	default String calculateTime(LocalDateTime date) {
		long curTime = System.currentTimeMillis();
		Instant instant = date.atZone(ZoneId.systemDefault()).toInstant();
		Date time = Date.from(instant);
		long regTime = time.getTime();
		long diffTime = (curTime - regTime) / 1000;
		String msg = null;
		if (diffTime < SEC) {
			// sec
			msg = diffTime + "초 전";
		} else if ((diffTime /= SEC) < MIN) {
			// min
			msg = diffTime + "분 전";
		} else if ((diffTime /= MIN) < HOUR) {
			// hour
			msg = (diffTime) + "시간 전";
		} else if ((diffTime /= HOUR) < DAY) {
			// day
			msg = (diffTime) + "일 전";
		} else if ((diffTime /= DAY) < MONTH) {
			// day
			msg = (diffTime) + "달 전";
		} else {
			msg = (diffTime) + "년 전";
		}
		return msg;
	}
}
