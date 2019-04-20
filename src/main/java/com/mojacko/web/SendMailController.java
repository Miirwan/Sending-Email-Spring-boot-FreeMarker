package com.mojacko.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mojacko.domain.User;
import com.mojacko.service.SendEmailService;

@RestController
public class SendMailController {
	
	@Autowired
	SendEmailService emailService;
	
	@PostMapping("/sendEmail")
	public String sendEmail(User user) {
		return emailService.sendEmail(user);
	}

}
