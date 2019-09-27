package com.example.emngr.schdlr;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.emngr.mongorepoimpl.CompleteSummaryRepoImpl;
import com.example.emngr.mongorepoimpl.CredentialRepoImpl;
import com.example.emngr.utl.UtilImpl;

@Component
public class CoreSchedular {

	private static Logger log = LoggerFactory.getLogger(CoreSchedular.class);

	@Autowired
	private CompleteSummaryRepoImpl completeSummaryRepo;
	@Autowired
	private CredentialRepoImpl credentialRepo;
	private UtilImpl util;

	@Scheduled(cron = "0 1 0 * * *")
	public void refreshMonthSummaryCollection() {
		log.info("Schedular refreshMonthSummaryCollection() started  @ "+new Date());
		try {
			util = new UtilImpl();

			completeSummaryRepo.refreshCurrentSummaryCollection(credentialRepo.getUserList(), util.getThisMonth());

		} catch (Exception e) {
			log.error("EXCEPTION: " + e.toString());
			e.printStackTrace();
		}

	}
}
