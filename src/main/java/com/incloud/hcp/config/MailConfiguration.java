package com.incloud.hcp.config;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Properties;

@Component("mailConfig")
public class MailConfiguration {

	public JavaMailSender getMailSender(Map<String, Object> smtpMapa) {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		mailSender.setDefaultEncoding("UTF-8");
		mailSender.setHost(smtpMapa.get("host").toString());
		mailSender.setPort(Integer.valueOf(smtpMapa.get("port").toString()));
		mailSender.setUsername(smtpMapa.get("remitentEmail").toString());
		mailSender.setPassword(smtpMapa.get("password").toString());

		
		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.starttls.enable", "true");
		javaMailProperties.put("mail.smtp.auth", "true");
		javaMailProperties.put("mail.transport.protocol", "smtp");
		javaMailProperties.put("mail.debug", "false");

		mailSender.setJavaMailProperties(javaMailProperties);
		return mailSender;
	}

}
