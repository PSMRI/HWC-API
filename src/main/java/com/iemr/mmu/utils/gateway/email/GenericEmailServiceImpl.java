/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.utils.gateway.email;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class GenericEmailServiceImpl implements EmailService {
	JavaMailSender javaMailSender;

	@Autowired
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	@Override
	public void sendEmail(String jsonObject, String template) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		JSONObject requestObj = new JSONObject(jsonObject);
		String to = requestObj.getString("to");
		String from = requestObj.getString("from");
		String subject = requestObj.getString("subject");
		String message = requestObj.getString("message");

		// TODO
		// JSONObject templateObj = new JSONObject(template);

		mailMessage.setTo(to);
		mailMessage.setFrom(from);
		mailMessage.setSubject(subject);
		mailMessage.setText(message);
		javaMailSender.send(mailMessage);

	}

	@Override
	public void sendEmail(String jsonObject) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		JSONObject requestObj = new JSONObject(jsonObject);
		String to = requestObj.getString("to");
		String from = requestObj.getString("from");
		String subject = requestObj.getString("subject");
		String message = requestObj.getString("message");
		mailMessage.setTo(to.split(";"));
		mailMessage.setFrom(from);
		mailMessage.setSubject(subject);
		mailMessage.setText(message);
		javaMailSender.send(mailMessage);

	}

	@Override
	public void sendEmailWithAttachment(String jsonObject, String template) {
		// TODO Auto-generated method stub

	}

	// public static void main(String[] args)
	// {
	// GenericEmailServiceImpl impl = new GenericEmailServiceImpl();
	// JSONObject test = new JSONObject();
	// test.put("to", "vinay.chidambara@wipro.com, vinay.sompur@gmail.com");
	// test.put("from", "vinay.sompur@gmail.com");
	// test.put("subject", "Test email event");
	// test.put("message", "Testing email for demo");
	// impl.sendEmail(test.toString());
	// }

	// public static void main(String[] args) throws Exception
	// {
	// GenericEmailServiceImpl impl = new GenericEmailServiceImpl();
	// JSONObject test = new JSONObject();
	// test.put("to", "vinay.chidambara@wipro.com, vinay.sompur@gmail.com");
	// test.put("from", "vinay.sompur@gmail.com");
	// test.put("subject", "Test email event");
	// test.put("message", "Testing email for demo");
	// SpringApplication.run(GenericEmailServiceImpl.class, args);
	// impl.sendEmail(test.toString());
	// }

}
