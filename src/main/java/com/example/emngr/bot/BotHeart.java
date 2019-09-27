package com.example.emngr.bot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.BotSession;

import com.example.emngr.bean.TelegramBean;
import com.example.emngr.mongorepoimpl.CredentialRepoImpl;

@Component
public class BotHeart extends TelegramLongPollingBot {

	@Value("${bot.token}")
	private String token;

	@Value("${bot.username}")
	private String username;

	@Autowired
	TelegramBotsApi telegramBotsApi;
	@Autowired
	CredentialRepoImpl credentialRepo;

	@Autowired
	BotBrain botBrain;
	private BotSession botSession;

	private List<BotSession> sessions = new ArrayList<>();

	@Override
	public void onUpdateReceived(Update update) {

		try {
			if (update.hasMessage()) {

				Message message = update.getMessage();
				String messageText = message.getText().trim().replace("<", "").replace(">", "");
				String botUn = message.getFrom().getUserName();
				Long chatId = message.getChatId();
				String responseText = "";

				int maxMessageLength = 30;
				int minMessageLength = 1;
				long msgTime = Long.parseLong(message.getDate() + "") * 1000;

				if (!BotBrainHelper.shouldIRespond(Long.parseLong(update.getMessage().getDate() + "") * 1000, 60)) {

					if (messageText.length() > maxMessageLength || messageText.length() < minMessageLength) {

						responseText = BotMessageGenerator.getLongMessage(maxMessageLength, minMessageLength);
					}

					else if (credentialRepo.isUserExistbyBotUn(botUn, chatId.toString())) {

						switch (messageText) {

						case BotBrain.MSG_START:
							responseText = botBrain.getStartMessage(botUn, chatId);
							break;

						case "getobj":
							if (BotBrainHelper.isChatIdExist(chatId)) {
								responseText = BotBrainHelper.CONVERSATION_OBJECT.get(chatId).toString();
							} else {
								responseText = "Something Wrong, ChatObject Destroyed !";
							}

							break;

						case BotBrain.MSG_CURRENT_MONTH_SUMMARY_GET:
							responseText = botBrain.getExpenseSummaryGetMessage(botUn, false);
							break;
						case BotBrain.MSG_PREV_MONTH_SUMMARY_GET:
							responseText = botBrain.getExpenseSummaryGetMessage(botUn, true);
							break;

						case BotBrain.MSG_CURRENT_MONTH_EXPENSE_GET:
							responseText = botBrain.getExpenseSummary(false);
							break;
						case BotBrain.MSG_PREV_MONTH_EXPENSE_GET:
							responseText = botBrain.getExpenseSummary(true);
							break;
						case BotBrain.MSG_CURRENT_MONTH_AB_GET:
							responseText = botBrain.getABSummary(false);
							break;
						case BotBrain.MSG_PREV_MONTH_AB_GET:
							responseText = botBrain.getABSummary(true);
							break;
						case BotBrain.MSG_EXPENSE_ADD:
							responseText = botBrain.getExpenseAddMessage(botUn, chatId, msgTime);
							break;

						case BotBrain.MSG_CANCEL:
							responseText = botBrain.getCancelMessage(chatId);
							break;

						case BotBrain.MSG_CONFIRM:
							responseText = botBrain.getConfirmMessage(chatId, botUn);
							break;

						case BotBrain.MSG_AMOUNT_BALANCER_ADD:
							responseText = botBrain.getAmountBalancerAddMessage(chatId, botUn, msgTime);
							break;
						case BotBrain.MSG_EXPENSE_REMOVE:
							responseText = botBrain.getExpenseRemove(chatId, botUn, msgTime);
							break;

						case BotBrain.MSG_AMOUNT_BALANCER_REMOVE:
							responseText = botBrain.getAmountBalancerRemove(chatId, botUn, msgTime);
							break;
						default:
							responseText = botBrain.getDefaultMessage(botUn, messageText, chatId);
							break;
						}
					}

					else {
						responseText = botBrain.getUnAuthrorizedMessage(botUn);
					}

					SendMessage replay = new SendMessage();
					replay.setParseMode(ParseMode.HTML);
					replay.setText(responseText);
					replay.setChatId(update.getMessage().getChatId());

					execute(replay);
				}
			}

		} catch (TelegramApiRequestException e) {

			e.getApiResponse();
			e.getCause();
			e.toString();
			e.getErrorCode();
			e.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/////////////////////////////////

	@Override
	public String getBotUsername() {

		return username;
	}

	@Override
	public String getBotToken() {
		// TODO CHeck if we can return Empty String
		return token;
	}

	@PreDestroy
	public void destroyDetails() {

		botSession.stop();

		sessions.forEach(sessions -> {
			if (sessions != null) {
				sessions.stop();
			}
		});
	}

	public void sendExpenseAddedMessage(String cat, Float amount, String un) {

		try {
			String botun = credentialRepo.getBotUnbyUn(un);
			List<TelegramBean> telegramObjs = credentialRepo.getTelegramObjects();

			for (TelegramBean telegramBean : telegramObjs) {

				if (!telegramBean.getUn().equals(botun)) {//botun
					
					SendMessage replay = new SendMessage();
					replay.setParseMode(ParseMode.HTML);
					
					replay.setText(BotMessageGenerator.getExpenseAddedNotification(telegramBean.getUn(),un,cat,amount));
					replay.setChatId(telegramBean.getChatId());

					execute(replay);
					
					
					System.out.println(telegramBean.getChatId());
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
