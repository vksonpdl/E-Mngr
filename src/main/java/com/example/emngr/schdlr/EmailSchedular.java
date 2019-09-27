package com.example.emngr.schdlr;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.example.emngr.mail.MailSenderEmngr;
import com.example.emngr.mongocoll.MonthExpenseCummaryCollection;
import com.example.emngr.mongorepo.CompleteSummaryRepo;
import com.example.emngr.mongorepoimpl.CredentialRepoImpl;
import com.example.emngr.utl.EmailConfig;
import com.example.emngr.utl.UtilImpl;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

@Component
public class EmailSchedular {

	private static Logger log = LoggerFactory.getLogger(EmailSchedular.class);

	@Autowired
	EmailConfig emailConfig;
	@Autowired
	TemplateEngine templete;
	@Autowired
	CredentialRepoImpl credentialRepo;
	@Autowired
	MailSenderEmngr mailSender;

	@Autowired
	CompleteSummaryRepo summaryRepo;
	private List<MonthExpenseCummaryCollection> monthSummary;
	private UtilImpl util;
	private BasicDBObject response_obj;
	boolean sentEmail = false;
	int userscount;
	float te;

	@Scheduled(cron = "0 59 23 * * *")
	public void mailSchedular() {
		try {

			log.info("Schedular mailSchedular() started @ " + new Date());

			response_obj = new BasicDBObject();
			BasicDBObject obj;
			BasicDBList objList = new BasicDBList();
			util = new UtilImpl();
			userscount = 0;
			te = 0;

			HashMap<String, String> dateInfo = util.getThisMonth();
			monthSummary = summaryRepo.getCurrentMonthSummary(dateInfo.get("mnth").toString(),
					dateInfo.get("yr").toString());
			Iterator<MonthExpenseCummaryCollection> iterator = monthSummary.iterator();
			MonthExpenseCummaryCollection summary;
			while (iterator.hasNext()) {
				summary = iterator.next();
				obj = new BasicDBObject();
				obj.append("un", summary.getUn());
				obj.append("ab_p", summary.getAb_paid());
				obj.append("ab_np", summary.getAb_ntb_paid());
				obj.append("e_p", summary.getE_paid());
				obj.append("e_np", summary.getE_ntb_paid());
				obj.append("t_p", summary.getAb_paid() + summary.getE_paid());
				obj.append("t_np", summary.getAb_ntb_paid() + summary.getE_ntb_paid());

				objList.add(obj);
				te = te + summary.getE_paid();
				userscount++;

			}

			response_obj.append("rmessage", "SUCCESS");
			response_obj.append("mnth", dateInfo.get("mnth").toString());
			response_obj.append("yr", dateInfo.get("yr").toString());
			response_obj.append("dat", objList);

			Context context = new Context();
			context.setVariable("date", new Date());
			context.setVariable("ae", te / userscount);
			context.setVariable("te", te);
			context.setVariable("data", response_obj);
			context.setVariable("title", "This Month Summary So Far");

			mailSender.sendExpenseSummaryMessage(context, dateInfo, null);

		} catch (Exception e) {
			log.error("EXCEPTION: " + e.toString());
			e.printStackTrace();
		}
	}

	@Scheduled(cron = "0 0 7 1 * *")
	public void getPrevMnthSummarySchedular() {
		try {

			log.info("Schedular mailSchedular() started @ " + new Date());

			response_obj = new BasicDBObject();
			BasicDBObject obj;
			BasicDBList objList = new BasicDBList();
			util = new UtilImpl();
			userscount = 0;
			te = 0;

			HashMap<String, String> dateInfo = util.getPreviuosMonth();
			monthSummary = summaryRepo.getCurrentMonthSummary(dateInfo.get("mnth").toString(),
					dateInfo.get("yr").toString());
			Iterator<MonthExpenseCummaryCollection> iterator = monthSummary.iterator();
			MonthExpenseCummaryCollection summary;
			while (iterator.hasNext()) {
				summary = iterator.next();
				obj = new BasicDBObject();
				obj.append("un", summary.getUn());
				obj.append("ab_p", summary.getAb_paid());
				obj.append("ab_np", summary.getAb_ntb_paid());
				obj.append("e_p", summary.getE_paid());
				obj.append("e_np", summary.getE_ntb_paid());
				obj.append("t_p", summary.getAb_paid() + summary.getE_paid());
				obj.append("t_np", summary.getAb_ntb_paid() + summary.getE_ntb_paid());

				objList.add(obj);
				te = te + summary.getE_paid();
				userscount++;

			}

			response_obj.append("rmessage", "SUCCESS");
			response_obj.append("mnth", dateInfo.get("mnth").toString());
			response_obj.append("yr", dateInfo.get("yr").toString());
			response_obj.append("dat", objList);

			Context context = new Context();
			context.setVariable("date", new Date());
			context.setVariable("ae", te / userscount);
			context.setVariable("te", te);
			context.setVariable("data", response_obj);
			context.setVariable("title", "Previous month Summary");

			mailSender.sendExpenseSummaryMessage(context, dateInfo, credentialRepo.getEmailList());

		} catch (Exception e) {
			log.error("EXCEPTION: " + e.toString());
			e.printStackTrace();
		}
	}

}
