package com.example.emngr.cntrlr;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import com.mongodb.BasicDBObject;

public interface AmountBalancerController {
	public HashMap<String, String> addAmount(HashMap<String, String> request, HttpSession session);
	public BasicDBObject getThisMonthAmountBalancerData(boolean prevmnth);
	public BasicDBObject getUsers(HttpSession session);

}
