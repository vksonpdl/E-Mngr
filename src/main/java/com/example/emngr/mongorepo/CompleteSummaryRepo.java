package com.example.emngr.mongorepo;

import java.util.HashMap;
import java.util.List;

import com.example.emngr.mongocoll.AmountBalancerCollection;
import com.example.emngr.mongocoll.ExpenseCollection;
import com.example.emngr.mongocoll.MonthExpenseCummaryCollection;

public interface CompleteSummaryRepo {
	public List<MonthExpenseCummaryCollection> getCurrentMonthSummary(String mnth, String yr) throws Exception;
	
	public void removeExpense(ExpenseCollection expense, int userCount, HashMap<String, String> dateObj) throws Exception;
	public void removeAmount(AmountBalancerCollection amount, HashMap<String, String> dateObj) throws Exception;
	
	public void refreshCurrentSummaryCollection(List<String> usernames, HashMap<String, String> dateObj) throws Exception;
	
	public void clearRepo();

}
