package com.example.emngr.bot;

public class BotMessageGenerator {

	protected static String getStartMessage(String botUn) {
		String responseText = "Hi " + botUn + ", \nWelcome to <b>E-Mngr Bot.</b>";
		
		responseText = responseText + "\nWe Help you to Manage your Room Expense via Telegram.";
		responseText = responseText + "\n<i>Below Options are available for You Currently.</i>";
		
		responseText = responseText + "\n\n<b>GET Operations </b> \n<i>For getting Transaction Details !</i>";
		responseText = responseText + "\n" + BotBrain.MSG_CURRENT_MONTH_SUMMARY_GET;
		responseText = responseText + "\n" + BotBrain.MSG_PREV_MONTH_SUMMARY_GET;
		responseText = responseText + "\n" + BotBrain.MSG_CURRENT_MONTH_EXPENSE_GET;
		responseText = responseText + "\n" + BotBrain.MSG_PREV_MONTH_EXPENSE_GET;
		
		responseText = responseText + "\n" + BotBrain.MSG_CURRENT_MONTH_AB_GET;
		responseText = responseText + "\n" + BotBrain.MSG_PREV_MONTH_AB_GET;
		
		
		
		responseText = responseText + "\n\n<b>ADD Operations</b> \n<i>For Adding Transaction (Debit) for the Current Month!</i>";;
		responseText = responseText + "\n" + BotBrain.MSG_EXPENSE_ADD;
		responseText = responseText + "\n" + BotBrain.MSG_AMOUNT_BALANCER_ADD;

		responseText = responseText + "\n\n<b>REMOVE Operations</b> \n<i>For Removing your Transaction (Debit) for the Current Month!</i>";;
		responseText = responseText + "\n" + BotBrain.MSG_EXPENSE_REMOVE;
		responseText = responseText + "\n" + BotBrain.MSG_AMOUNT_BALANCER_REMOVE;

		responseText = responseText + "\n\n <i>Your Messages might be Auditted.</i>";

		return responseText;
	}

	protected static String getInvalidAmountMessage() {

		String responseText = "You have Entered an Invalid Expense Amount !\n";
		responseText = responseText + "Please Enter  Amount in number and it should be greater than 0 !\n\n";
		responseText = responseText + "Or Do you want to " + BotBrain.MSG_CANCEL + " the Transaction ?\n";

		return responseText;
	}

	protected static String getInvalidMessage() {

		String responseText = "This Bot is in Development mode.\n";
		responseText = responseText + "Only Few Of the messages are being accepted now\n";
		responseText = responseText + "/Start : Get the Available Options for you";

		return responseText;
	}

	protected static String getInvalidOptionSelectedMessafeforExpenseAdd() {
		String responseText = "You have Entered an Invalid Expense Reason\n";
		responseText = responseText + "Please Select a Valid Expense Reason From the Below List\n";

		return responseText;
	}

	protected static String getInvalidOptionSelectedMessafeforAmountAdd() {
		String responseText = "You have Entered an Invalid Expense Reason\n";
		responseText = responseText + "Please Select a User From the Below List\n";

		return responseText;
	}

	protected static String getCancelConfirmationMessage() {

		String responseText = "Or Do you want to " + BotBrain.MSG_CANCEL + " the Transaction ?\n";
		return responseText;
	}

	protected static String getOopsMessage() {
		String responseText = "Oops Something went Rong !\n";
		responseText = responseText + "Please  " + BotBrain.MSG_START + " Your Conversation Again.";

		return responseText;
	}

	protected static String getExpenseAddConfirmationMessage(Float expense, Long chatId) {

		String responseText = "You are doing fantastic !\n";
		responseText = responseText + "So Hope you want to add " + expense + " ";
		responseText = responseText + "since you have paid the same for ";
		responseText = responseText + BotBrainHelper.getExpenseReason(chatId) + " !\n\n";
		responseText = responseText + "Please " + BotBrain.MSG_CONFIRM + " or  ";
		responseText = responseText + BotBrain.MSG_CANCEL + " the Transaction ?\n";

		return responseText;
	}

	protected static String getConfirmOrCancelMessage() {

		String responseText = "Please " + BotBrain.MSG_CONFIRM + " Or ";
		responseText = responseText + BotBrain.MSG_CANCEL + " The Current Transcation !";

		return responseText;

	}

	protected static String getAmountAddConfirmationMessage(Float expense, Long chatId) {

		String responseText = "You are doing fantastic !\n";
		responseText = responseText + "So Hope you want to add ";
		responseText = responseText + expense + " since you have paid the same to ";
		responseText = responseText + BotBrainHelper.getAmountPaidTo(chatId) + " !\n";
		responseText = responseText + "\nPlease " + BotBrain.MSG_CONFIRM + " or  ";
		responseText = responseText + BotBrain.MSG_CANCEL + " the Transaction ?\n";

		return responseText;
	}

	protected static String getAmountMessage() {

		String responseText = "You are doing fantastic! \n";
		responseText = responseText + "Enter The Amount in Numbers (Decimel values are accepted) !";
		return responseText;

	}

	protected static String getAmountPaidReasonMessage(String amountPaidTo) {

		String responseText = "Please Tell me why did you pay for " + amountPaidTo + " \n";
		return responseText;

	}

	public static String getLongMessage(int max, int min) {

		String responseText = "I can read maximum of " + max + " characters and I Need Minimum " + min
				+ " Characters\n";
		responseText = responseText + BotBrain.MSG_START + " is the proper way to Initiate a Conversation with me !";

		return responseText;
	}

	public static String getAMountAddedMessage() {

		String responseText = "Cool.. You have added the Amount\n";
		responseText = responseText + "Please " + BotBrain.MSG_START + " Again.";

		return responseText;
	}

	public static String invalidConfirmMessage() {

		String responseText = "Hey, Looks like you have not started any Transaction to Confirm!\n";
		responseText = responseText + "Please " + BotBrain.MSG_START + " Something...";

		return responseText;

	}

	public static String getInvalidIdMessage() {
		String responseText = "You have selected Invalid Id \n";
		responseText = responseText + "Please Select a valid Id from the above list \n";
		responseText = responseText + "Or " + BotBrain.MSG_START + " Again...";

		return responseText;
	}

	public static String getExpenseRemoveCOnfirmMessage() {

		String responseText = "Please " + BotBrain.MSG_CONFIRM + " If you want to remove the Expense..\n";
		responseText = responseText + "Or " + BotBrain.MSG_CANCEL;

		return responseText;

	}

	public static String chatObjectIdExpired() {

		String responseText = "Chat Session is Closed \n";
		responseText = responseText + "Please " + BotBrain.MSG_START + " Transaction again";

		return responseText;
	}

	public static String getExpenseRemovedMessage() {
		String responseText = "Expense Removed Successfully! \n";
		responseText = responseText + "Please " + BotBrain.MSG_START + " New Transaction...";

		return responseText;

	}
	
	public static String getAmountRemovedMessage() {
		String responseText = "Amount Removed Successfully! \n";
		responseText = responseText + "Please " + BotBrain.MSG_START + " New Transaction...";

		return responseText;

	}
	
	public static String getchatSessionExpiredMessage() {
		
		String responseText = "Seems the chat session expired! \n";
		responseText = responseText + "Please " + BotBrain.MSG_START + " New Transaction...";

		return responseText;
	}

	public static String getNoDataFound() {
		
		String responseText = "No Details Found ! \n";
		responseText = responseText + "Please " + BotBrain.MSG_START + " New Transaction...";

		return responseText;
	}
	
	public static String getStartNewTransactionMessage() {
		
		return "Please " + BotBrain.MSG_START + " New Transaction...";

		
	}
	
	public static String getExpenseAddedNotification(String botUn,String botUnAdded, String cat, Float amount) {
		String responseText = "Hi "+botUn+" \n";
		responseText = responseText + botUnAdded+" added <b>"+ amount + "</b> to the Expense Manager today for "+cat;

		return responseText;
	}
}
