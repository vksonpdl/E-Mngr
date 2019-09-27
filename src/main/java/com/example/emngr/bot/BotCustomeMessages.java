package com.example.emngr.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.example.emngr.mongorepoimpl.CredentialRepoImpl;

@Component
public class BotCustomeMessages {

	@Autowired
	TelegramBotsApi telegramBotsApi;
	@Autowired
	CredentialRepoImpl credentialRepo;
	
	public void sendExpenseAddedMessage(String cat, Float amnount) {
		
		SendMessage replay = new SendMessage();
		replay.setParseMode(ParseMode.HTML);
		replay.setText("");
		replay.setChatId("987656579846");
		
		
		
	}
	
}
