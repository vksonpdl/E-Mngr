package com.example.emngr.bot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;

@Component
public class BotBrainHelper {

	@Autowired
	BotBrain botBrain;

	// TODO: Change to Private
	protected static final Map<Long, BasicDBObject> CONVERSATION_OBJECT = new HashMap<Long, BasicDBObject>();
	protected static List<String> LIST_EXPENSE_REASON = new ArrayList<String>();
	protected static List<String> LIST_USERS_EXCEPT_ME = new ArrayList<String>();
	protected static List<String> LIST_EXPENSE_ID = new ArrayList<String>();
	protected static List<String> LIST_AMOUNT_ID = new ArrayList<String>();

	protected static BasicDBObject LIST_OBJECT = new BasicDBObject();

	protected static final String AMOUNT_PAID_TO = "amnt_pd_to";
	protected static final String AMOUNT_PAID_REASON = "amnt_rsn";
	protected static final String AMOUNT_PAID_AMOUNT = "amnt_pd";
	protected static final String AMOUNT_REMOVE_ID = "amnt_rmv_id";

	protected static void addToList(String data, String STRING) {
		switch (STRING) {
		case BotConstants.STRING_EXPENSE_REASON:
			LIST_EXPENSE_REASON.add(data);
			break;
		case BotConstants.STRING_EXPENSE_ID:
			LIST_EXPENSE_ID.add(data);
			break;
		case BotConstants.STRING_USERS_EXCEPT_ME:
			LIST_USERS_EXCEPT_ME.add(data);
			break;
		case BotConstants.STRING_AMOUNT_ID:
			LIST_AMOUNT_ID.add(data);
			break;
		default:
			System.err.println("Hey Something Went Wrong From addToList(String data, String STRING), Class: "
					+ BotBrainHelper.class.getName());
			break;
		}

	}

	public static boolean isListContains(String data, String STRING) {

		boolean returnBoolean = false;

		switch (STRING) {
		case BotConstants.STRING_EXPENSE_REASON:
			if (LIST_EXPENSE_REASON.contains(data)) {
				returnBoolean = true;
			}
			break;
		case BotConstants.STRING_EXPENSE_ID:
			if (LIST_EXPENSE_ID.contains(data)) {
				returnBoolean = true;
			}

			break;
		case BotConstants.STRING_USERS_EXCEPT_ME:
			// TODO CHECH WHy ?
			break;
		case BotConstants.STRING_AMOUNT_ID:
			if (LIST_AMOUNT_ID.contains(data)) {
				returnBoolean = true;
			}
			break;
		default:
			System.err.println("Hey Something Went Wrong From isListContains(String data, String STRING), Class: "
					+ BotBrainHelper.class.getName());
			break;
		}

		return returnBoolean;
	}

	public static void clearAllList() {
		LIST_EXPENSE_REASON.clear();
		LIST_EXPENSE_REASON.clear();
		LIST_EXPENSE_ID.clear();
		LIST_AMOUNT_ID.clear();
	}

	protected static Float getFloatFromMessage(String messageText) {
		float returnFloat = 0;
		try {
			returnFloat = Float.parseFloat(messageText);
		} catch (Exception e) {

		}

		return returnFloat;
	}

	protected static BasicDBObject getChatObjectbyChatId(Long chatId) {

		return CONVERSATION_OBJECT.get(chatId);

	}

	protected static List<String> getList(String STRING) {

		// TODO: EXPENSE_ID and AMOUNT_ID Cases are not being used, check why

		List<String> returnList;
		switch (STRING) {
		case BotConstants.STRING_EXPENSE_REASON:
			returnList = LIST_EXPENSE_REASON;
			break;
		case BotConstants.STRING_EXPENSE_ID:
			returnList = LIST_EXPENSE_ID;
			break;
		case BotConstants.STRING_USERS_EXCEPT_ME:
			returnList = LIST_USERS_EXCEPT_ME;
			break;
		case BotConstants.STRING_AMOUNT_ID:
			returnList = LIST_AMOUNT_ID;
			break;
		default:
			returnList = null;
			System.err.println(
					"Hey Something Went Wrong From getList(String STRING), Class: " + BotBrainHelper.class.getName());
			break;
		}
		return returnList;
	}

	public static void destroyConversationObject(Long chatId) {
		Set<Long> chatIdSet = CONVERSATION_OBJECT.keySet();

		if (null != chatIdSet && chatIdSet.contains(chatId)) {
			CONVERSATION_OBJECT.remove(chatId);

		}
	}

	public static boolean isChatIdExist(Long chatId) throws Exception {

		boolean returnBoolean = false;

		Set<Long> chatIdSet = CONVERSATION_OBJECT.keySet();
		if (null != chatIdSet && chatIdSet.contains(chatId)) {

			returnBoolean = true;
		}

		return returnBoolean;
	}

	public static void addChatObject(String chatOprn, Long chatId, Long msgTime) throws Exception {
		BasicDBObject obj = new BasicDBObject();
		obj.put(BotConstants.OPERATION, chatOprn);
		obj.put(BotConstants.OPERATION_TIME, msgTime);
		CONVERSATION_OBJECT.put(chatId, obj);
	}

	public static boolean isChatSesssionExpired(Long chatId, Integer seconds) {

		boolean returnBoolean = false;
		BasicDBObject chatObject = CONVERSATION_OBJECT.get(chatId);

		Long objectTime = chatObject.getLong(BotConstants.OPERATION_TIME);
		Long currentTime = new Date().getTime();

		if ((currentTime - objectTime) > (seconds * 1000)) {

			returnBoolean = true;
		}

		return returnBoolean;

	}

	public static boolean shouldIRespond(Long msgTime, Integer seconds) {

		boolean returnBoolean = false;
		Long currentTime = new Date().getTime();

		if ((currentTime - msgTime) > (seconds * 1000)) {

			returnBoolean = true;
		}

		return returnBoolean;

	}

	public static boolean updateChatObjectwithExpenseReason(Long chatId, String expenseReason) throws Exception {

		boolean returnBoolean = false;
		BasicDBObject chatObject = CONVERSATION_OBJECT.get(chatId);

		chatObject.put(BotConstants.EXPENSE_REASON, expenseReason);
		CONVERSATION_OBJECT.remove(chatId);
		CONVERSATION_OBJECT.put(chatId, chatObject);
		returnBoolean = true;

		return returnBoolean;

	}

	public static boolean updateChatObjectwithAmountPaidTo(Long chatId, String amountPaidTo) throws Exception {

		boolean returnBoolean = false;
		BasicDBObject chatObject = CONVERSATION_OBJECT.get(chatId);
		if (null != chatObject && null != chatObject.get(BotConstants.OPERATION)
				&& chatObject.getString(BotConstants.OPERATION).length() > 0) {
			chatObject.put(AMOUNT_PAID_TO, amountPaidTo);
			CONVERSATION_OBJECT.remove(chatId);
			CONVERSATION_OBJECT.put(chatId, chatObject);
			returnBoolean = true;
		}

		return returnBoolean;

	}

	public static boolean updateChatObjectwithExpenseAmount(Long chatId, Float expenseAmount) throws Exception {

		boolean returnBoolean = false;
		BasicDBObject chatObject = CONVERSATION_OBJECT.get(chatId);

		chatObject.put(BotConstants.EXPENSE_AMOUNT, expenseAmount);
		CONVERSATION_OBJECT.remove(chatId);
		CONVERSATION_OBJECT.put(chatId, chatObject);
		returnBoolean = true;

		return returnBoolean;

	}

	public static boolean updateChatObjectwithAmount(Long chatId, Float expenseAmount) throws Exception {

		boolean returnBoolean = false;
		BasicDBObject chatObject = CONVERSATION_OBJECT.get(chatId);

		chatObject.put(AMOUNT_PAID_AMOUNT, expenseAmount);
		CONVERSATION_OBJECT.remove(chatId);
		CONVERSATION_OBJECT.put(chatId, chatObject);
		returnBoolean = true;

		return returnBoolean;

	}

	public static boolean updateChatObjectwithAmountPaidFor(Long chatId, String PaidFor) throws Exception {

		boolean returnBoolean = false;
		BasicDBObject chatObject = CONVERSATION_OBJECT.get(chatId);

		chatObject.put(AMOUNT_PAID_REASON, PaidFor);
		CONVERSATION_OBJECT.remove(chatId);
		CONVERSATION_OBJECT.put(chatId, chatObject);
		returnBoolean = true;

		return returnBoolean;

	}

	public static String getChatOperationFromChatObject(Long chatId) {

		String returnSTring = "";
		BasicDBObject chatObject = CONVERSATION_OBJECT.get(chatId);

		if (null != chatObject && null != chatObject.get(BotConstants.OPERATION)
				&& chatObject.getString(BotConstants.OPERATION).length() > 0) {
			returnSTring = chatObject.getString(BotConstants.OPERATION);
		}

		return returnSTring;
	}

	public static boolean isExpenseReasonAdded(Long chatId, String chatOprn) {
		boolean returnBoolean = false;
		BasicDBObject chatObject = CONVERSATION_OBJECT.get(chatId);
		if (null != chatObject.get(BotConstants.OPERATION) && chatObject.get(BotConstants.OPERATION).equals(chatOprn)
				&& null != chatObject.get(BotConstants.EXPENSE_REASON)) {
			returnBoolean = true;
		}
		return returnBoolean;
	}

	public static boolean addExpenseAmount(Long chatId, Float expense, String chatOprn) {
		boolean returnBoolean = false;
		BasicDBObject chatObject = CONVERSATION_OBJECT.get(chatId);
		if (null != chatObject.get(BotConstants.OPERATION) && chatObject.get(BotConstants.OPERATION).equals(chatOprn)
				&& null != chatObject.get(BotConstants.EXPENSE_REASON)) {
			chatObject.append(BotConstants.EXPENSE_AMOUNT, expense);
			CONVERSATION_OBJECT.remove(chatId);
			CONVERSATION_OBJECT.put(chatId, chatObject);
			returnBoolean = true;
		}
		return returnBoolean;

	}

	public static boolean isExpenseReasonExist(Long chatId) {

		boolean returnBoolean = false;
		BasicDBObject chatObject = CONVERSATION_OBJECT.get(chatId);
		if (null != chatObject.getString(BotConstants.EXPENSE_REASON)
				&& chatObject.getString(BotConstants.EXPENSE_REASON).length() > 0) {

			returnBoolean = true;
		}

		return returnBoolean;
	}

	public static String getExpenseReason(Long chatId) {
		return CONVERSATION_OBJECT.get(chatId).getString(BotConstants.EXPENSE_REASON);
	}

	public static String getAmountPaidTo(Long chatId) {
		return CONVERSATION_OBJECT.get(chatId).getString(AMOUNT_PAID_TO);
	}

	public static Float getExpenseAmount(Long chatId) {
		return Float.parseFloat(CONVERSATION_OBJECT.get(chatId).get(BotConstants.EXPENSE_AMOUNT).toString());
	}

	public static int getExpenseChatObjectStatus(Long chatId) {
		int retturnNumber = 9;
		BasicDBObject chatObject = CONVERSATION_OBJECT.get(chatId);
		if (null != chatObject.getString(BotConstants.EXPENSE_REASON)) {
			if (null != chatObject.getString(BotConstants.EXPENSE_AMOUNT)) {
				// You are Good To Go :-)
				retturnNumber = 0;
			} else {
				// No Expense Amount Added but Reason Present!
				retturnNumber = 2;
			}

		} else {
			// No Expense reason Added !
			retturnNumber = 1;
		}

		return retturnNumber;
	}

	public static int getAmountBalancerObjectStatus(Long chatId) {
		int returnNumber = 9;
		BasicDBObject chatObject = CONVERSATION_OBJECT.get(chatId);

		if (null != chatObject.getString(AMOUNT_PAID_TO)) {

			if (null != chatObject.get(AMOUNT_PAID_REASON)) {

				if (null != chatObject.get(AMOUNT_PAID_AMOUNT)) {

					// You are Good To Go :-)
					returnNumber = 0;
				} else {
					returnNumber = 2;
				}
			} else {
				// No Amount ReasonAdded but PaidTo Present!
				returnNumber = 3;

			}

		} else {
			// Amount Paidto Not Found
			returnNumber = 1;
		}

		return returnNumber;

	}

	public static int getExpenseRemoveObjectStatus(Long chatId) {
		int returnNumber = 9;
		BasicDBObject chatObject = CONVERSATION_OBJECT.get(chatId);

		if (null != chatObject.getString(BotConstants.EXPENSE_REMOVE_ID)) {
			returnNumber = 0;

		} else {
			// RemoveId Not Present
			returnNumber = 1;
		}

		return returnNumber;

	}

	public static int getAmountRemoveObjectStatus(Long chatId) {
		int returnNumber = 9;
		BasicDBObject chatObject = CONVERSATION_OBJECT.get(chatId);

		if (null != chatObject.getString(AMOUNT_REMOVE_ID)) {
			returnNumber = 0;

		} else {
			// RemoveId Not Present
			returnNumber = 1;
		}

		return returnNumber;

	}

	public static boolean updateChatObjectwithExpenseIdAdd(Long chatId, String expenseId) throws Exception {

		boolean returnBoolean = false;
		BasicDBObject chatObject = CONVERSATION_OBJECT.get(chatId);

		chatObject.put(BotConstants.EXPENSE_REMOVE_ID, expenseId);
		CONVERSATION_OBJECT.remove(chatId);
		CONVERSATION_OBJECT.put(chatId, chatObject);
		returnBoolean = true;

		return returnBoolean;

	}

	public static void updateChatObjectwithAmountIdAdd(Long chatId, String amountId) throws Exception {

		BasicDBObject chatObject = CONVERSATION_OBJECT.get(chatId);

		chatObject.put(AMOUNT_REMOVE_ID, amountId);
		CONVERSATION_OBJECT.remove(chatId);
		CONVERSATION_OBJECT.put(chatId, chatObject);

	}

	protected static String amountPaidtoWhome(Long chatId) {
		return CONVERSATION_OBJECT.get(chatId).getString(AMOUNT_PAID_TO);
	}

}
