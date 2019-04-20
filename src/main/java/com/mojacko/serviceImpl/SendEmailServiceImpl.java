package com.mojacko.serviceImpl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.mojacko.domain.User;
import com.mojacko.service.SendEmailService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service 
public class SendEmailServiceImpl implements SendEmailService{

	@Autowired
	private JavaMailSender sender;

	@Autowired
	private Configuration config;

	@Value("${spring.mail.username}")
	private String from;

	@Override
	public String sendEmail(User user) {
		String msg ="";
		MimeMessage message = sender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			Map<String, Object> model = new HashMap<>();
			model.put("Name", user.getName());
			model.put("location", user.getCity());
			
			Template t = config.getTemplate("mail-template.ftl");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

			helper.setTo(user.getEmail());
			helper.setText(html, true);
			helper.setSubject("");
			helper.setFrom(this.from);
			
			sender.send(message);

			msg = "SUCCESS";

		} catch (MessagingException | IOException | TemplateException e) {
			msg = "FAILURE";
		}
		return msg;
	}


}
