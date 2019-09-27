package com.example.emngr.cntrlr;


import java.util.HashMap;

import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.example.emngr.mongorepoimpl.NotUsedRepoImpl;
import com.example.emngr.utl.EmailConfig;

@RestController
public class NotUsedController {
	@Autowired
	NotUsedRepoImpl repo;

	@Autowired
	EmailConfig emailConfig;
	
	@Autowired
	TemplateEngine templete;

	@Value("${spring.mail.username}")
	private String email;

	

	@RequestMapping(value = "/addcategory")
	public String addCategory(@RequestBody HashMap<String, String> request) {

		repo.AddCategory(request.get("cat"));

		return "SUCCESS";
	}

	@RequestMapping(value = "/getmail")
	public String getMailid() throws Exception {

		Context context = new Context();
		context.setVariable("name", "vikas");
		String templeteData = templete.process("forgot-password", context);

		Message msg = emailConfig.getMessage();

		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
		msg.setSubject("e-mngr");
		msg.setContent(templeteData, "text/html");
		

	


		Transport.send(msg);

		return email;
	}

}
