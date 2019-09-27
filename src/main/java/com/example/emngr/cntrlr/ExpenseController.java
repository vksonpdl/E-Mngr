package com.example.emngr.cntrlr;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import com.mongodb.BasicDBObject;

public interface ExpenseController {

	public HashMap<String, String> addExpense(HashMap<String, String> request, HttpSession session);
	public BasicDBObject viewThisMonthExpense(boolean prevmnth);
	
}
