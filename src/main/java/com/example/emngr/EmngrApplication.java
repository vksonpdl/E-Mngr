package com.example.emngr;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;

import com.example.emngr.bot.BotHeart;

@SpringBootApplication
@EnableScheduling
@ComponentScan("com.example.emngr")
public class EmngrApplication {

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("IST"));
	}

	public static void main(String[] args) {
		ApiContextInitializer.init();
		TelegramBotsApi botApi = new TelegramBotsApi();
		try {

			botApi.registerBot(new BotHeart());
		} catch (Exception e) {
			e.printStackTrace();
		}

		SpringApplication.run(EmngrApplication.class, args);
	}

}
