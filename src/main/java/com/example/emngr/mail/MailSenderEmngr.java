package com.example.emngr.mail;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.example.emngr.utl.EmailConfig;

@Component
public class MailSenderEmngr {

	@Autowired
	EmailConfig emailConfig;
	@Autowired
	TemplateEngine templete;
	private Context context;
	private Message message;
	private String templeteData;
	boolean sentEmail;

	@Value("${app.name}")
	private String appName;
	@Value("${app.adminmaillist}")
	private String adminEmailList;

	public void sendAddExpenseEmail(HashMap<String, String> request, List<String> emailList) {

		try {

			sentEmail = false;
			context = new Context();

			context.setVariable("from", request.get("un"));
			context.setVariable("amount", request.get("exp"));
			context.setVariable("category", request.get("cat"));
			context.setVariable("date", new Date());

			templeteData = templete.process("expense-added", context);

			message = emailConfig.getMessage();
			message.setSubject(appName + ": Added Expense");
			message.setContent(templeteData, "text/html");

			for (String email : emailList) {
				if (null != email) {
					message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
					sentEmail = true;
				}

			}

			if (sentEmail) {

				Transport.send(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendPasswordChangedEmail(String user, String email) {

		try {

			if (null != email && email.length() > 3 && email.contains("@")) {

				context = new Context();

				context.setVariable("user", user);
				context.setVariable("date", new Date());

				templeteData = templete.process("password-change", context);

				message = emailConfig.getMessage();
				message.setSubject(appName + ": Password Change");
				message.setContent(templeteData, "text/html");
				message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
				Transport.send(message);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void sendAdminPasswordResetEmail(String user, String pwd, String email) {

		try {

			if (null != email && email.length() > 3 && email.contains("@")) {

				context = new Context();

				context.setVariable("user", user);
				context.setVariable("pwd", pwd);
				context.setVariable("date", new Date());

				templeteData = templete.process("admin-pwd-reset", context);

				message = emailConfig.getMessage();
				message.setSubject(appName + ": Password Reset");
				message.setContent(templeteData, "text/html");
				message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
				Transport.send(message);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void sendExpenseSummaryMessage(Context context, HashMap<String, String> dateInfo, List<String> emailList) {

		try {
			context.setVariable("date", new Date());

			templeteData = templete.process("expense-summary", context);

			message = emailConfig.getMessage();
			message.setSubject(appName + ": Daily Status (" + dateInfo.get("dy") + "/" + dateInfo.get("mnth") + "/"
					+ dateInfo.get("yr") + ")");
			message.setContent(templeteData, "text/html");

			String mailList[] = adminEmailList.split(",");

			if (null == emailList || emailList.size() < 1) {
				for (String email : mailList) {
					message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
				}
			} else {
				for (String email : emailList) {
					message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
				}
			}

			Transport.send(message);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
