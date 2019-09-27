package com.example.emngr.bot;

import java.util.HashMap;

import com.example.emngr.utl.Util;
import com.example.emngr.utl.UtilImpl;
import com.mongodb.BasicDBObject;

public class BotBrainRepoRequestGenerator {

	private static HashMap<String, String> returnRequest;
	private static Util util;

	protected static HashMap<String, String> getAddExpenseRequest(BasicDBObject object, String un) {

		returnRequest = new HashMap<String, String>();
		util = new UtilImpl();
		returnRequest = util.getThisMonth();
		returnRequest.put("exp", object.get("expns_amnt").toString());
		returnRequest.put("cat", object.get("expns_rsn").toString());
		returnRequest.put("un", un);

		return returnRequest;

	}

	protected static HashMap<String, String> getAddAmountRequest(BasicDBObject object, String un) {
		returnRequest = new HashMap<String, String>();
		util = new UtilImpl();
		returnRequest = util.getThisMonth();

		returnRequest.put("un_frm", un);
		returnRequest.put("amnt", object.get(BotBrainHelper.AMOUNT_PAID_AMOUNT).toString());
		returnRequest.put("rsn", object.get(BotBrainHelper.AMOUNT_PAID_REASON).toString());
		returnRequest.put("un_to", object.get(BotBrainHelper.AMOUNT_PAID_TO).toString());

		return returnRequest;
	}

	protected static HashMap<String, String> getMyExpenseObjectIds(BasicDBObject object, String un) {
		returnRequest = new HashMap<String, String>();
		util = new UtilImpl();
		returnRequest = util.getThisMonth();
		returnRequest.put("un", un);

		return returnRequest;
	}

	protected static HashMap<String, String> getCurrentDateMap() {

		returnRequest = new HashMap<String, String>();
		util = new UtilImpl();
		return util.getThisMonth();
	}

	protected static HashMap<String, String> getAmountBalancerListRequest(String un) {

		util = new UtilImpl();

		returnRequest = util.getThisMonth();
		returnRequest.put("un", un);
		return returnRequest;
	}

}
