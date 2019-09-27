package com.example.emngr.mongorepo;


import java.util.HashMap;
import java.util.List;

import com.example.emngr.mongocoll.ExpenseCollection;


public interface ExpenseRepo {
	public void addExpense(HashMap<String, String> request, List<String> userCount) throws Exception;
	public List<ExpenseCollection> getMonthExpense(String yr,String mnth) throws Exception;
	
	public List<ExpenseCollection> getMyExpenseList (HashMap<String, String> request) throws Exception;
	
	public ExpenseCollection removeExpenseByObjectId(String objectId) throws Exception;
	
	
	public void clearRepo();
}


