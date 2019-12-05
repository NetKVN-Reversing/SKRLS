package org.jemiahlabs.skrls.gui.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggerFormatService {
	public String getMessage(String title, String message, String typeMessage) {
		String dateTime = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now());
		return String.format("%s [%s] %s - %s", dateTime, typeMessage, title, message);
	}
}
