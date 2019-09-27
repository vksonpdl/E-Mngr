package com.example.emngr.mongorepo;

import java.util.HashMap;
import java.util.List;



import com.example.emngr.mongocoll.AmountBalancerCollection;

public interface AmountBalancerRepo {
	public boolean addAmount(HashMap<String, String> request) throws Exception;
	public List<AmountBalancerCollection> getAmountBalancerSummary(HashMap<String, String> request) throws Exception;
	public List<String> getUsers(String un) throws Exception;
	
	public List<AmountBalancerCollection> getMyAmountBalancerList (HashMap<String, String> request) throws Exception;
	public AmountBalancerCollection removeAmountByObjectId(String objectId) throws Exception;
	
	public void clearRepo();

}
