package com.example.emngr.bot;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.emngr.mongocoll.AmountBalancerCollection;
import com.example.emngr.mongocoll.CategoryCollection;
import com.example.emngr.mongocoll.ExpenseCollection;
import com.example.emngr.mongocoll.MonthExpenseCummaryCollection;
import com.example.emngr.mongorepo.AmountBalancerRepo;
import com.example.emngr.mongorepo.CompleteSummaryRepo;
import com.example.emngr.mongorepoimpl.CategoryRepoImpl;
import com.example.emngr.mongorepoimpl.CredentialRepoImpl;
import com.example.emngr.mongorepoimpl.ExpenseRepoImpl;
import com.example.emngr.utl.Util;
import com.example.emngr.utl.UtilImpl;
import com.mongodb.BasicDBObject;

@Component
public class BotBrain {

	@Autowired
	private CompleteSummaryRepo completeSummarRepo;
	@Autowired
	ExpenseRepoImpl expenseRepo;
	@Autowired
	AmountBalancerRepo amountBalancerRepo;
	@Autowired
	CredentialRepoImpl credentialRepo;
	@Autowired
	private CategoryRepoImpl categoryRepo;

	private Util util;;

	public static final String MSG_START = "/Start";
	public static final String MSG_CURRENT_MONTH_SUMMARY_GET = "/CurrentMonthSummary";
	public static final String MSG_PREV_MONTH_SUMMARY_GET = "/PreviousMonthSummary";
	public static final String MSG_CURRENT_MONTH_EXPENSE_GET = "/CurrentMonthExpense";
	public static final String MSG_PREV_MONTH_EXPENSE_GET = "/PreviousMonthExpense";
	
	public static final String MSG_CURRENT_MONTH_AB_GET = "/CurrentMonth_AB";
	public static final String MSG_PREV_MONTH_AB_GET = "/PreviousMonth_AB";

	public static final String MSG_EXPENSE_ADD = "/AddExpense";
	public static final String MSG_AMOUNT_BALANCER_ADD = "/AddAmountBalancer";
	public static final String MSG_AMOUNT_BALANCER_REMOVE = "/RemoveFromAmountBalancer";
	public static final String MSG_EXPENSE_REMOVE = "/RemoveExpense";
	public static final String MSG_CONFIRM = "/Confirm";
	public static final String MSG_CANCEL = "/Cancel";

	private static final String CHAT_OPRN_EXPENSE_ADD = "expense_add";
	private static final String CHAT_OPRN_AMOUNT_ADD = "amnt_add";

	private static final String CHAT_OPRN_EXPENSE_REMOVE = "expense_rmv";
	private static final String CHAT_OPRN_AMOUNT_REMOVE = "amnt_rmv";

	private String responseText;

	private List<MonthExpenseCummaryCollection> monthSummary;

	public String getStartMessage(String botUn, Long chatId) {

		BotBrainHelper.destroyConversationObject(chatId);
		BotBrainHelper.clearAllList();

		responseText = BotMessageGenerator.getStartMessage(botUn);
		return responseText;

	}

	public String getCancelMessage(Long chatId) {
		BotBrainHelper.destroyConversationObject(chatId);
		BotBrainHelper.clearAllList();

		responseText = "";

		responseText = responseText + "\nNo worries, Let's Start from the Scrach again !";
		responseText = responseText + "\nPlease do a Fresh " + MSG_START;
		return responseText;
	}

	public String getConfirmMessage(Long chatId, String bootUn) throws Exception {

		responseText = "";

		// Checking if ChatObject exist and get the Chat Operation

		if (BotBrainHelper.isChatIdExist(chatId)
				&& BotBrainHelper.getChatOperationFromChatObject(chatId).length() > 0) {

			// Looks like user have some operation in chat Object, Lets see what operation
			// it is and act accordingly
			String chatOperation = BotBrainHelper.getChatOperationFromChatObject(chatId);

			switch (chatOperation) {
			case CHAT_OPRN_EXPENSE_ADD:

				// EXPENSE ADD SWITCH START
				// It's Expense Add Operation, Check if we are good to Go

				if (BotBrainHelper.getExpenseChatObjectStatus(chatId) == 0) {
					// Good TO Go, Call Repo to Add Expense
					BasicDBObject chatObject = BotBrainHelper.getChatObjectbyChatId(chatId);
					HashMap<String, String> repoRequest = BotBrainRepoRequestGenerator.getAddExpenseRequest(chatObject,
							credentialRepo.getUnbyBotUn(bootUn));

					expenseRepo.addExpense(repoRequest, credentialRepo.getUserList());

					responseText = BotMessageGenerator.getAMountAddedMessage();

				} else {
					responseText = BotMessageGenerator.getOopsMessage();

				}
				// EXPENSE ADD SWITCH END

				break;
			case CHAT_OPRN_AMOUNT_ADD:

				if (BotBrainHelper.getAmountBalancerObjectStatus(chatId) == 0) {
					BasicDBObject chatObject = BotBrainHelper.getChatObjectbyChatId(chatId);
					amountBalancerRepo.addAmount(BotBrainRepoRequestGenerator.getAddAmountRequest(chatObject,
							credentialRepo.getUnbyBotUn(bootUn)));

					responseText = BotMessageGenerator.getAMountAddedMessage();
				} else {
					responseText = BotMessageGenerator.invalidConfirmMessage();
				}

				break;

			case CHAT_OPRN_EXPENSE_REMOVE:
				if (BotBrainHelper.getExpenseRemoveObjectStatus(chatId) == 0) {

					String objectId = BotBrainHelper.getChatObjectbyChatId(chatId)
							.getString(BotConstants.EXPENSE_REMOVE_ID).replace("/", "");
					ExpenseCollection expense = expenseRepo.removeExpenseByObjectId(objectId);
					int usersCount = credentialRepo.getUserList().size();
					completeSummarRepo.removeExpense(expense, usersCount,
							BotBrainRepoRequestGenerator.getCurrentDateMap());

					responseText = BotMessageGenerator.getAmountRemovedMessage();
				} else {
					responseText = BotMessageGenerator.invalidConfirmMessage();
				}
				break;

			case CHAT_OPRN_AMOUNT_REMOVE:

				if (BotBrainHelper.getAmountRemoveObjectStatus(chatId) == 0) {
					String objectId = BotBrainHelper.getChatObjectbyChatId(chatId)
							.getString(BotBrainHelper.AMOUNT_REMOVE_ID).replace("/", "");

					AmountBalancerCollection amount = amountBalancerRepo.removeAmountByObjectId(objectId);

					String un = credentialRepo.getUnbyBotUn(bootUn);
					completeSummarRepo.removeAmount(amount,
							BotBrainRepoRequestGenerator.getAmountBalancerListRequest(un));

					// TODO: Waw-Update Please
					responseText = BotMessageGenerator.getAmountRemovedMessage();
				} else {
					responseText = BotMessageGenerator.invalidConfirmMessage();
				}

				break;

			default:

				responseText = BotMessageGenerator.getOopsMessage();

				break;
			}

		}

		else {
			// Either the ChatObject doesn't exist or ChatOperation not added
			responseText = BotMessageGenerator.invalidConfirmMessage();

		}

		BotBrainHelper.destroyConversationObject(chatId);
		BotBrainHelper.clearAllList();

		return responseText;
	}

	public String getExpenseSummaryGetMessage(String botUn,boolean prevmonth) throws Exception {
		responseText = "";

		
		util = new UtilImpl();
		HashMap<String, String> dateInfo;
		if(prevmonth) {
			responseText = responseText+" Here is the Summary of Previous Month ! \n \n";
			dateInfo = util.getPreviuosMonth();
		}
		else {
			responseText = responseText+" Here is the Summary of the Current Month! \n \n";
			dateInfo = util.getThisMonth();
		}

		monthSummary = completeSummarRepo.getCurrentMonthSummary(dateInfo.get("mnth").toString(),
				dateInfo.get("yr").toString());

		Iterator<MonthExpenseCummaryCollection> iterator = monthSummary.iterator();

		boolean expenseExist = false;

		if (iterator.hasNext()) {
			expenseExist = true;
		}

		MonthExpenseCummaryCollection summary;
		while (iterator.hasNext()) {
			summary = iterator.next();

			responseText = responseText + "<b>" + summary.getUn() + "</b>\n";
			responseText = responseText + "Amount Paid :" + (summary.getE_paid() + summary.getAb_paid()) + " \n";

			responseText = responseText + "Amount Need To Pay :"
					+ ((summary.getAb_ntb_paid() + summary.getE_ntb_paid())) + " \n";
			responseText = responseText + "------------------------- \n\n";
		}
		if (!expenseExist) {
			responseText = "No Expeanse Details found for The Month ! \n";
		}

		responseText = "\n\n" + responseText + MSG_START + " Again..";

		return responseText;
	}

	public String getExpenseSummary(boolean prevmnth) throws Exception {

		util = new UtilImpl();
		HashMap<String, String> dateInfo;

		if (prevmnth) {
			dateInfo = util.getPreviuosMonth();
		} else {
			dateInfo = util.getThisMonth();
		}
	

		List<ExpenseCollection> expenseList = expenseRepo.getMonthExpense(dateInfo.get("yr"), dateInfo.get("mnth"));
		if (expenseList.size() > 0) {

			int counter = 0;
			responseText = "<b>Here is the Expense Details !</b> \n \n";
			for (ExpenseCollection expense : expenseList) {
				counter++;
				responseText = responseText + "#" + counter + ") \n";
				responseText = responseText + "Paid by : " + expense.getUn() + "\n";
				responseText = responseText + "Category : " + expense.getCat() + "\n";
				responseText = responseText + "Amount : " + expense.getExp() + "\n";
				responseText = responseText + "Date : " + new Date(Long.parseLong(expense.getTmstmp())) + "\n";
				responseText = responseText + "------------------------------ \n";

			}
			responseText = responseText + BotMessageGenerator.getStartNewTransactionMessage();

		} else {
			responseText = BotMessageGenerator.getNoDataFound();
		}

		return responseText;
	}
	
	
	
	
	public String getABSummary(boolean prevmnth) throws Exception {

		util = new UtilImpl();
		HashMap<String, String> dateInfo;

		if (prevmnth) {
			dateInfo = util.getPreviuosMonth();
		} else {
			dateInfo = util.getThisMonth();
		}
		
		List<AmountBalancerCollection> amountList = amountBalancerRepo.getAmountBalancerSummary(dateInfo);
		
		if (amountList.size() > 0) {

			int counter = 0;
			responseText = "<b>Here is the Amount Balancer Details !</b> \n \n";
			for (AmountBalancerCollection amount : amountList) {
				counter++;
				responseText = responseText + "#" + counter + ") \n";
				responseText = responseText + "Paid by : " + amount.getUn_frm() + "\n";
				responseText = responseText + "Paid To : " + amount.getUn_to() + "\n";
				responseText = responseText + "Amount : " + amount.getAmnt() + "\n";
				responseText = responseText + "Reason : " + amount.getRsn() + "\n";
				responseText = responseText + "Date : " + new Date(Long.parseLong(amount.getTmstmp())) + "\n";
				responseText = responseText + "------------------------------ \n";

			}
			responseText = responseText + BotMessageGenerator.getStartNewTransactionMessage();

		} else {
			responseText = BotMessageGenerator.getNoDataFound();
		}

		return responseText;
	}

	

	public String getExpenseAddMessage(String botUn, Long chatId, Long msgTime) throws Exception {

		responseText = "";
		responseText = responseText + "Hi " + botUn + ",\n";
		responseText = responseText + "Please Select the Correct Category from below.\n\n";

		BotBrainHelper.destroyConversationObject(chatId);
		BotBrainHelper.clearAllList();

		BotBrainHelper.addChatObject(CHAT_OPRN_EXPENSE_ADD, chatId, msgTime);

		List<CategoryCollection> categoryCollection = categoryRepo.getCategories();

		for (CategoryCollection category : categoryCollection) {

			BotBrainHelper.addToList("/" + category.getCat(), BotConstants.STRING_EXPENSE_REASON);

			responseText = responseText + "/" + category.getCat() + "  \n";
		}

		responseText = responseText + "\n";

		return responseText;
	}

	public String getAmountBalancerAddMessage(Long chatId, String botUn, Long msgTime) throws Exception {
		responseText = "";

		BotBrainHelper.destroyConversationObject(chatId);
		BotBrainHelper.clearAllList();

		BotBrainHelper.addChatObject(CHAT_OPRN_AMOUNT_ADD, chatId, msgTime);

		responseText = "Plese select the user from the below list, for whome you have paid \n\n";

		List<String> usersList = credentialRepo.getUserList();
		String un_i = credentialRepo.getUnbyBotUn(botUn);

		for (String un : usersList) {
			if (!un.equals(un_i)) {

				responseText = responseText + " /" + un + " \n";
				BotBrainHelper.addToList("/" + un, BotConstants.STRING_USERS_EXCEPT_ME);
			}
		}

		return responseText;
	}

	public String getExpenseRemove(Long chatId, String botUn, Long msgTime) throws Exception {
		int counter = 1;
		responseText = "";

		BotBrainHelper.destroyConversationObject(chatId);
		BotBrainHelper.clearAllList();

		BotBrainHelper.addChatObject(CHAT_OPRN_EXPENSE_REMOVE, chatId, msgTime);

		BasicDBObject chatObject = BotBrainHelper.getChatObjectbyChatId(chatId);

		List<ExpenseCollection> expenseList = expenseRepo.getMyExpenseList(
				BotBrainRepoRequestGenerator.getMyExpenseObjectIds(chatObject, credentialRepo.getUnbyBotUn(botUn)));

		if (null != expenseList && expenseList.size() > 0) {

			responseText = "Please Select the Currosponding Id to remove the Expense ";

			for (ExpenseCollection expense : expenseList) {

				BotBrainHelper.addToList("/" + expense.getId().toString(), BotConstants.STRING_EXPENSE_ID);

				responseText = responseText + "\n #)" + counter + " \n";

				responseText = responseText + "Category : " + expense.getCat() + " \n";
				responseText = responseText + "Expense : " + expense.getExp() + " \n";
				responseText = responseText + "Added Date : " + new Date(Long.parseLong(expense.getTmstmp())) + " \n";
				responseText = responseText + "Id : /" + expense.getId().toString() + " \n";
				responseText = responseText + "------------------------------------\n";

				counter++;
			}

		} else {
			responseText = "Sorry You do not have any expense this Month !";
			responseText = responseText + " Please " + BotBrain.MSG_START + " New Transaction.... ";
		}

		return responseText;
	}

	public String getAmountBalancerRemove(Long chatId, String botUn, Long msgTime) throws Exception {
		int counter = 1;
		responseText = "";

		BotBrainHelper.destroyConversationObject(chatId);
		BotBrainHelper.clearAllList();

		BotBrainHelper.addChatObject(CHAT_OPRN_AMOUNT_REMOVE, chatId, msgTime);

		String un = credentialRepo.getUnbyBotUn(botUn);
		List<AmountBalancerCollection> amountList = amountBalancerRepo
				.getMyAmountBalancerList(BotBrainRepoRequestGenerator.getAmountBalancerListRequest(un));

		if (null != amountList && amountList.size() > 0) {

			responseText = "Please Select the Currosponding Id to remove ";

			for (AmountBalancerCollection amount : amountList) {

				BotBrainHelper.addToList("/" + amount.getId().toString(), BotConstants.STRING_AMOUNT_ID);

				responseText = responseText + "\n #)" + counter + " \n";

				responseText = responseText + "Paid To : " + amount.getUn_to() + " \n";
				responseText = responseText + "Amount : " + amount.getAmnt() + " \n";
				responseText = responseText + "Reason : " + amount.getRsn() + " \n";
				responseText = responseText + "Added Date : " + new Date(Long.parseLong(amount.getTmstmp())) + " \n";
				responseText = responseText + "Id : /" + amount.getId().toString() + " \n";
				responseText = responseText + "------------------------------------\n";

				counter++;
			}

		} else {
			responseText = "Sorry You do not have any AMount Balancer this Month !";
			responseText = responseText + " Please " + BotBrain.MSG_START + " New Transaction.... ";
		}

		return responseText;
	}

	public String getUnAuthrorizedMessage(String botUn) {
		responseText = "";

		responseText = "Hi " + botUn + ", \nSeems you have not registered with E-Mngr."
				+ "\n Please reach out the E-Mngr Admin to get Registered.";
		return responseText;

	}

	public String getDefaultMessage(String botUn, String messageText, Long chatId) throws Exception {
		if (BotBrainHelper.isChatIdExist(chatId) && BotBrainHelper.isChatSesssionExpired(chatId, 15)) {

			BotBrainHelper.destroyConversationObject(chatId);
			BotBrainHelper.clearAllList();
			responseText = BotMessageGenerator.getchatSessionExpiredMessage();
		}

		else if (BotBrainHelper.isChatIdExist(chatId)) {
			// User Started an Operation
			String chatOperation = BotBrainHelper.getChatOperationFromChatObject(chatId);

			switch (chatOperation) {
			case CHAT_OPRN_EXPENSE_ADD:

				switch (BotBrainHelper.getExpenseChatObjectStatus(chatId)) {

				case 0:
					responseText = BotMessageGenerator.getConfirmOrCancelMessage();
					break;
				case 1:

					if (BotBrainHelper.getList(BotConstants.STRING_EXPENSE_REASON).contains(messageText)) {

						BotBrainHelper.updateChatObjectwithExpenseReason(chatId, messageText.substring(1));

						responseText = BotMessageGenerator.getAmountMessage();
					} else {
						// invalid Expense Reason, Give Error
						responseText = BotMessageGenerator.getInvalidOptionSelectedMessafeforExpenseAdd();
						List<String> expenseList = BotBrainHelper.getList(BotConstants.STRING_EXPENSE_REASON);
						for (String reason : expenseList) {
							responseText = responseText + reason + "\n";
						}
						responseText = responseText + BotMessageGenerator.getCancelConfirmationMessage();
					}

					break;
				case 2:
					float expenseAmount = BotBrainHelper.getFloatFromMessage(messageText);
					if (expenseAmount > 0) {
						BotBrainHelper.updateChatObjectwithExpenseAmount(chatId, expenseAmount);
						responseText = BotMessageGenerator.getExpenseAddConfirmationMessage(expenseAmount, chatId);
					}

					else {

						responseText = BotMessageGenerator.getInvalidAmountMessage();

					}
					break;

				default:
					responseText = BotMessageGenerator.getOopsMessage();

					break;
				}

				////////////////////////////////////

				break;

			case CHAT_OPRN_AMOUNT_ADD:

				switch (BotBrainHelper.getAmountBalancerObjectStatus(chatId)) {
				case 0:
					responseText = BotMessageGenerator.getConfirmOrCancelMessage();
					break;

				case 1:

					// Should be PaidTo not present

					if (BotBrainHelper.getList(BotConstants.STRING_USERS_EXCEPT_ME).contains(messageText)) {

						BotBrainHelper.updateChatObjectwithAmountPaidTo(chatId, messageText.substring(1));
						responseText = BotMessageGenerator
								.getAmountPaidReasonMessage(BotBrainHelper.amountPaidtoWhome(chatId));
					} else {

						responseText = BotMessageGenerator.getInvalidOptionSelectedMessafeforAmountAdd();
						List<String> amountPaidToList = BotBrainHelper.getList(BotConstants.STRING_USERS_EXCEPT_ME);
						for (String amountPaidTo : amountPaidToList) {
							responseText = responseText + amountPaidTo + "\n";
						}
						responseText = responseText + BotMessageGenerator.getCancelConfirmationMessage();

					}

					break;

				case 2:

					// Should be Amount not present
					float expenseAmount = BotBrainHelper.getFloatFromMessage(messageText);
					if (expenseAmount > 0) {
						BotBrainHelper.updateChatObjectwithAmount(chatId, expenseAmount);
						responseText = BotMessageGenerator.getAmountAddConfirmationMessage(expenseAmount, chatId);

					} else {
						responseText = BotMessageGenerator.getInvalidAmountMessage();
					}

					break;

				case 3:

					responseText = BotMessageGenerator.getAmountMessage();
					BotBrainHelper.updateChatObjectwithAmountPaidFor(chatId, messageText);
					break;
				default:

					responseText = BotMessageGenerator.getOopsMessage();
					break;
				}

				break;
			case CHAT_OPRN_EXPENSE_REMOVE:
				switch (BotBrainHelper.getExpenseRemoveObjectStatus(chatId)) {
				case 0:
					responseText = BotMessageGenerator.getConfirmOrCancelMessage();
					break;
				case 1:
					if (BotBrainHelper.isListContains(messageText, BotConstants.STRING_EXPENSE_ID)) {
						BotBrainHelper.updateChatObjectwithExpenseIdAdd(chatId, messageText);
						responseText = BotMessageGenerator.getExpenseRemoveCOnfirmMessage();
					} else {
						responseText = BotMessageGenerator.getInvalidIdMessage();
					}

					break;

				default:
					responseText = BotMessageGenerator.getOopsMessage();
					break;
				}

				break;

			case CHAT_OPRN_AMOUNT_REMOVE:
				switch (BotBrainHelper.getAmountRemoveObjectStatus(chatId)) {
				case 0:

					responseText = BotMessageGenerator.getConfirmOrCancelMessage();
					break;
				case 1:
					if (BotBrainHelper.isListContains(messageText, BotConstants.STRING_AMOUNT_ID)) {

						BotBrainHelper.updateChatObjectwithAmountIdAdd(chatId, messageText);
						responseText = BotMessageGenerator.getExpenseRemoveCOnfirmMessage();
					} else {
						responseText = BotMessageGenerator.getInvalidIdMessage();
					}

					break;

				default:
					break;
				}

				break;
			default:

				responseText = BotMessageGenerator.getOopsMessage();
				break;
			}

		} else {

			responseText = BotMessageGenerator.getInvalidMessage();
		}

		/////////////////////////////

		return responseText;
	}

}
