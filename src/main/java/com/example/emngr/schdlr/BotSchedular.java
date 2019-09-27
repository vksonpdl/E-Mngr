package com.example.emngr.schdlr;

import java.io.File;
import java.io.InputStream;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.emngr.bot.BotBrainHelper;
import com.example.emngr.mongorepoimpl.CredentialRepoImpl;

@Component
public class BotSchedular {
	private static Logger log = LoggerFactory.getLogger(BotSchedular.class);

	@Autowired
	CredentialRepoImpl credentialRepo;

	// @Scheduled(cron = "0/5 * * * * *")
	public void tellAJoke() {
		try {
			Resource resource = new ClassPathResource("fun.prop");

			InputStream input = resource.getInputStream();

			File file = resource.getFile();

			if (file.exists()) {
				System.out.println(file.getAbsolutePath() + " : Is exist");
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		/*
		 * System.out.println("Root FIle : " + new File("").getAbsolutePath());
		 * System.out.println("Dot File FIle : " + new File(".").getAbsolutePath());
		 */
	}

	@Scheduled(cron = "0 0 3 * * *")
	public void clearBotCache() {

		log.info("Schedular clearBotCache() started  @ "+new Date());

		BotBrainHelper.clearAllList();

	}

}
