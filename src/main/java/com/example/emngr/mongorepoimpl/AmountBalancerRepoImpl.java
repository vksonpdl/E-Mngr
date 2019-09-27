package com.example.emngr.mongorepoimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.example.emngr.mongocoll.AmountBalancerCollection;
import com.example.emngr.mongocoll.MonthExpenseCummaryCollection;
import com.example.emngr.mongocoll.UserCollection;
import com.example.emngr.mongorepo.AmountBalancerRepo;

@Component
public class AmountBalancerRepoImpl implements AmountBalancerRepo {

	private static Logger log = LoggerFactory.getLogger(AmountBalancerRepoImpl.class);
	
	@Autowired
	MongoTemplate mongoTemplete;
	AmountBalancerCollection amount;
	MonthExpenseCummaryCollection expenseSummary;
	private Query query;
	private Long time;

	@Override
	public boolean addAmount(HashMap<String, String> request) throws Exception {

		time = new Date().getTime();
		
		// Adding to Aount Balancer Collection
		amount = new AmountBalancerCollection();

		amount.setUn_frm(request.get("un_frm").toString());
		amount.setUn_to(request.get("un_to").toString());
		amount.setAmnt(Float.parseFloat(request.get("amnt")));
		amount.setRsn(request.get("rsn").toString());
		amount.setMnth(request.get("mnth").toString());
		amount.setDy(request.get("dy").toString());
		amount.setYr(request.get("yr").toString());
		amount.setTmstmp(request.get("tmstmp").toString());

		mongoTemplete.insert(amount);

		
		// Adding to Month Summary
		// Adding From Amount

		query = new Query();
		query.addCriteria(Criteria.where("un").is(request.get("un_frm").toString()));
		query.addCriteria(Criteria.where("mnth").is(request.get("mnth").toString()));
		query.addCriteria(Criteria.where("yr").is(request.get("yr").toString()));

		expenseSummary = mongoTemplete.findOne(query, MonthExpenseCummaryCollection.class);
		if (null != expenseSummary) {

			Update up = new Update();
			up.set("ab_paid", expenseSummary.getAb_paid() + Float.parseFloat(request.get("amnt")));
			up.set("ab_ntb_paid", expenseSummary.getAb_ntb_paid() - Float.parseFloat(request.get("amnt")));
			mongoTemplete.updateFirst(query, up, MonthExpenseCummaryCollection.class);
			
			
		} else {
			expenseSummary = new MonthExpenseCummaryCollection();
			expenseSummary.setYr(request.get("yr").toString());
			expenseSummary.setMnth(request.get("mnth").toString());
			expenseSummary.setE_paid(0);
			expenseSummary.setE_ntb_paid(0);

			expenseSummary.setUn(request.get("un_frm").toString());
			expenseSummary.setAb_paid(Float.parseFloat(request.get("amnt")));
			expenseSummary.setAb_ntb_paid(0-(Float.parseFloat(request.get("amnt"))));
			
			mongoTemplete.insert(expenseSummary);
			
			
		}

		// Adding To Amount
		query = new Query();
		query.addCriteria(Criteria.where("un").is(request.get("un_to").toString()));
		query.addCriteria(Criteria.where("mnth").is(request.get("mnth").toString()));
		query.addCriteria(Criteria.where("yr").is(request.get("yr").toString()));

		expenseSummary = mongoTemplete.findOne(query, MonthExpenseCummaryCollection.class);
		if (null != expenseSummary) {

			Update up = new Update();
			up.set("ab_ntb_paid", expenseSummary.getAb_ntb_paid() + Float.parseFloat(request.get("amnt")));
			mongoTemplete.updateFirst(query, up, MonthExpenseCummaryCollection.class);
		} else {
			expenseSummary = new MonthExpenseCummaryCollection();
			expenseSummary.setYr(request.get("yr").toString());
			expenseSummary.setMnth(request.get("mnth").toString());
			expenseSummary.setE_paid(0);
			expenseSummary.setE_ntb_paid(0);

			expenseSummary.setUn(request.get("un_to").toString());
			expenseSummary.setAb_paid(0);
			expenseSummary.setAb_ntb_paid(Float.parseFloat(request.get("amnt")));

			mongoTemplete.insert(expenseSummary);
		}

		log.info("EXCMPLTD : addAmount : "+(new Date().getTime()-time)+" ms");
		
		return true;
	}

	@Override
	public List<AmountBalancerCollection> getAmountBalancerSummary(HashMap<String, String> request) throws Exception {
		
		time = new Date().getTime();

		query = new Query();
		query.addCriteria(Criteria.where("yr").is(request.get("yr").toString()));
		query.addCriteria(Criteria.where("mnth").is(request.get("mnth").toString()));

		log.info("EXCMPLTD : getAmountBalancerSummary : "+(new Date().getTime()-time)+" ms");
		
		return mongoTemplete.find(query, AmountBalancerCollection.class);
	}

	@Override
	public List<String> getUsers(String un) throws Exception {
		
		time = new Date().getTime();
		
		query = new Query();
		List<String> userList = new ArrayList<String>();
		query.addCriteria(Criteria.where("un").ne(un));
		List<UserCollection> users = mongoTemplete.find(query, UserCollection.class);
		Iterator<UserCollection> iterator_user = users.iterator();
		while (iterator_user.hasNext()) {
			userList.add(iterator_user.next().getUn());
		}
		
		log.info("EXCMPLTD : getUsers : "+(new Date().getTime()-time)+" ms");
		
		return userList;
	}

	@Override
	public AmountBalancerCollection removeAmountByObjectId(String objectId) throws Exception {
		
		time = new Date().getTime();
		
		query = new Query().addCriteria(Criteria.where("_id").is(new ObjectId(objectId)));
		AmountBalancerCollection amount = mongoTemplete.findOne(query, AmountBalancerCollection.class);
		mongoTemplete.remove(query, AmountBalancerCollection.class);

		log.info("EXCMPLTD : removeAmountByObjectId : "+(new Date().getTime()-time)+" ms");
		
		return amount;
	}

	@Override
	public List<AmountBalancerCollection> getMyAmountBalancerList(HashMap<String, String> request) throws Exception {
		
		time = new Date().getTime();

		query = new Query();
		query.addCriteria(Criteria.where("un_frm").is(request.get("un").toString()));
		query.addCriteria(Criteria.where("mnth").is(request.get("mnth").toString()));
		query.addCriteria(Criteria.where("yr").is(request.get("yr").toString()));

		log.info("EXCMPLTD : getMyAmountBalancerList : "+(new Date().getTime()-time)+" ms");
		
		return mongoTemplete.find(query, AmountBalancerCollection.class);
	}

	@Override
	public void clearRepo() {
		
		time = new Date().getTime();
		
		mongoTemplete.remove(AmountBalancerCollection.class);
		
		log.info("EXCMPLTD : clearRepo : "+(new Date().getTime()-time)+" ms");
		
		

	}

}
