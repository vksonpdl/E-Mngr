package com.example.emngr.cntrlrimpl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.emngr.cntrlr.AdminController;
import com.example.emngr.mail.MailSenderEmngr;
import com.example.emngr.mongocoll.UserCollection;
import com.example.emngr.mongorepoimpl.CredentialRepoImpl;
import com.example.emngr.utl.UtilImpl;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

@RestController
public class AdminControllerImpl implements AdminController {

	@Autowired
	CredentialRepoImpl credential;
	@Autowired
	MailSenderEmngr mailsender;
	private UtilImpl util;
	private BasicDBObject responseObject;

	@Override
	@RequestMapping("/admin/pwdreset")
	public BasicDBObject resetAllPasswords(HttpSession session, @RequestParam(required = false) String un)
			throws Exception {
		util = new UtilImpl();
		responseObject = new BasicDBObject();
		BasicDBList usersList = new BasicDBList();
		if (session.getAttribute("un") != null && session.getAttribute("rl") != null
				&& session.getAttribute("rl").toString().equals("admin")) {

			
			if (null == un || un.toLowerCase().equals("all")) {

				List<UserCollection> uns = credential.getUsersCollection();
				for (UserCollection un_col : uns) {

					String usrn = un_col.getUn();
					String pwd = util.getRandomPassword(10);

					String pw_enc = util.encryptPassword(pwd, usrn);
					credential.updatePassword(usrn, pw_enc, true);
					mailsender.sendAdminPasswordResetEmail(usrn, pwd, un_col.getEmail());
					usersList.add(usrn);
				}

			} else {
				String pwd = util.getRandomPassword(10);
				String pw_enc = util.encryptPassword(pwd, un.toLowerCase());

				credential.updatePassword(un.toLowerCase(), pw_enc, true);
				mailsender.sendAdminPasswordResetEmail(un.toLowerCase(), pwd,
						credential.getEmailbyUn(un.toLowerCase()));
				usersList.add(un.toLowerCase());

			}

			responseObject.append("status", "success");
			responseObject.append("message", "success");
			responseObject.append("uns", usersList);
		}

		else {
			responseObject.append("status", "fail");
			responseObject.append("message", "You are not Authorized to do this operation");

		}
		return responseObject;
	}

}
