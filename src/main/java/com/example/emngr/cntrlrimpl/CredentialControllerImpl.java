package com.example.emngr.cntrlrimpl;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.emngr.cntrlr.CredentialController;
import com.example.emngr.mail.MailSenderEmngr;
import com.example.emngr.mongocoll.UserCollection;
import com.example.emngr.mongorepoimpl.CredentialRepoImpl;
import com.example.emngr.utl.UtilImpl;

@RestController
public class CredentialControllerImpl implements CredentialController {

	private HashMap<String, String> respon;
	private UtilImpl util;
	private UserCollection user;
	@Autowired
	CredentialRepoImpl credentialRepo;
	@Autowired
	MailSenderEmngr mailSender;

	@Override
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public HashMap<String, String> registerUser(@RequestBody HashMap<String, String> request) {
		util = new UtilImpl();
		respon = new HashMap<String, String>();
		try {
			request.put("pwd", util.encryptPassword(request.get("pwd").toString(), request.get("un").toString()));

			credentialRepo.createCredential(request);
			respon.put("rmessage", "SUCCESS");
		} catch (Exception e) {
			respon.put("rmessage", "EXCEPTION");
			respon.put("rexception", e.toString());

		}

		return respon;
	}

	@Override
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public HashMap<String, String> login(@RequestBody HashMap<String, String> request, HttpSession session) {

		respon = new HashMap<String, String>();
		util = new UtilImpl();
		try {

			request.put("pwd", util.encryptPassword(request.get("pwd").toString(), request.get("un").toString()));

			user = credentialRepo.validateCredential(request);
			if (null != user) {

				session.setAttribute("un", user.getUn());
				session.setAttribute("rl", user.getRl());
				session.setAttribute("pwddef", user.getPwddef());

				respon.put("rmessage", "SUCCESS");
			} else {

				respon.put("rmessage", "FAIL");
			}

		} catch (Exception e) {

			respon.put("rmessage", "EXCEPTION");
			respon.put("rexception", e.toString());
		}

		return respon;
	}

	@RequestMapping(value = "/getsession", method = RequestMethod.GET)
	public HashMap<String, String> getSessionInfo(HttpSession session) {

		respon = new HashMap<String, String>();
		if (session.getAttribute("un") != null && session.getAttribute("rl") != null) {
			respon.put("rmessage", "SUCCESS");
			respon.put("un", session.getAttribute("un").toString());
			respon.put("rl", session.getAttribute("rl").toString());

			if (session.getAttribute("pwddef").equals(true)) {
				respon.put("pwddef", "Y");
			} else {
				respon.put("pwddef", "N");
			}

		} else {
			respon.put("rmessage", "FAIL");

		}

		return respon;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public HashMap<String, String> logout(HttpSession session) {
		respon = new HashMap<String, String>();
		try {
			session.invalidate();
			respon.put("rmessage", "SUCCESS");
		} catch (Exception e) {
			respon.put("rmessage", "EXCEPTION");
			respon.put("rexception", e.toString());
		}
		return respon;

	}

	@Override
	@RequestMapping(value = "/changepassword", method = RequestMethod.POST)
	public HashMap<String, String> changePassword(@RequestBody HashMap<String, String> request, HttpSession session) {

		respon = new HashMap<String, String>();
		try {

			if ((null == request.get("pwd") || null == request.get("new_pwd") || null == request.get("new_pwd2"))
					|| (request.get("pwd").toString().trim().length() < 6
							|| request.get("new_pwd").toString().trim().length() < 6
							|| request.get("new_pwd2").toString().trim().length() < 6)) {

				respon.put("rmessage", "INV_E_DATA");

			} else if (session.getAttribute("un") != null) {
				util = new UtilImpl();

				String un = session.getAttribute("un").toString();
				String old_pwd = util.encryptPassword(request.get("pwd").toString(), un);
				String new_pwd = util.encryptPassword(request.get("new_pwd").toString(), un);
				String new_pwd2 = util.encryptPassword(request.get("new_pwd2").toString(), un);

				if (!new_pwd.equals(new_pwd2)) {
					respon.put("rmessage", "INV_S_PWD");
				} else {
					request.remove("pwd");
					request.put("pwd", old_pwd);
					request.put("un", session.getAttribute("un").toString());
					user = credentialRepo.validateCredential(request);
					if (null == user) {
						respon.put("rmessage", "INV_PWD");
					} else if (old_pwd.equals(new_pwd)) {
						respon.put("rmessage", "INV_N_PWD");
					} else {
						credentialRepo.updatePassword(un, new_pwd, false);
						respon.put("rmessage", "SUCCESS");
						mailSender.sendPasswordChangedEmail(un, credentialRepo.getEmailbyUn(un));
						session.invalidate();

					}
				}

			} else {
				respon.put("rmessage", "INV_UN");

			}

		} catch (Exception e) {
			respon.put("rmessage", "EXCEPTION");
			respon.put("rexception", e.toString());
			e.printStackTrace();

		}
		return respon;
	}
}
