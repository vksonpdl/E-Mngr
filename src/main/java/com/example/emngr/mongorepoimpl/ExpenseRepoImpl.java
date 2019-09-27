package com.example.emngr.mongorepoimpl;

import java.util.Date;
import java.util.HashMap;
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

import com.example.emngr.bot.BotHeart;
import com.example.emngr.mongocoll.ExpenseCollection;
import com.example.emngr.mongocoll.MonthExpenseCummaryCollection;
import com.example.emngr.mongorepo.ExpenseRepo;
import com.example.emngr.utl.UtilImpl;

@Component
public class ExpenseRepoImpl implements ExpenseRepo {

	private static Logger log = LoggerFactory.getLogger(ExpenseRepoImpl.class);
	
	@Autowired
	MongoTemplate mongo;
	
	@Autowired
	BotHeart botHeart;
	UtilImpl util;
	ExpenseCollection expense;
	private Query query;
	private MonthExpenseCummaryCollection expenseSummary;
	private Long time;

	@Override
	public void addExpense(HashMap<String, String> request, List<String> userslist) throws Exception {

		time = new Date().getTime();
		
		int usercount = userslist.size();
		float amount_paid = Float.parseFloat(request.get("exp"));
		expense = new ExpenseCollection();
		expense.setCat(request.get("cat"));
		expense.setUn(request.get("un"));
		expense.setDy(request.get("dy"));
		expense.setExp(amount_paid);
		expense.setMnth(request.get("mnth"));
		expense.setTmstmp(request.get("tmstmp"));
		expense.setYr(request.get("yr"));
		mongo.insert(expense);

		query = new Query();
		query.addCriteria(Criteria.where("un").is(request.get("un")));
		query.addCriteria(Criteria.where("mnth").is(request.get("mnth")));
		query.addCriteria(Criteria.where("yr").is(request.get("yr")));

		expenseSummary = mongo.findOne(query, MonthExpenseCummaryCollection.class);
		if (null != expenseSummary) {

			Update up = new Update();
			up.set("e_paid", expenseSummary.getE_paid() + amount_paid);
			up.set("e_ntb_paid", expenseSummary.getE_ntb_paid() + ((amount_paid / usercount) - amount_paid));
			mongo.updateFirst(query, up, MonthExpenseCummaryCollection.class);

		} else {
			expenseSummary = new MonthExpenseCummaryCollection();
			expenseSummary.setYr(request.get("yr").toString());
			expenseSummary.setMnth(request.get("mnth").toString());
			expenseSummary.setE_paid(amount_paid);
			expenseSummary.setE_ntb_paid((amount_paid / usercount) - amount_paid); // change
			expenseSummary.setUn(request.get("un"));
			expenseSummary.setAb_paid(0);
			expenseSummary.setAb_ntb_paid(0);

			mongo.insert(expenseSummary);
		}

		for (int i = 0; i < usercount; i++) {
			String user = userslist.get(i);
			if (!(user.equals(request.get("un")))) {

				query = new Query();
				query.addCriteria(Criteria.where("mnth").is(request.get("mnth")));
				query.addCriteria(Criteria.where("yr").is(request.get("yr")));
				query.addCriteria(Criteria.where("un").is(user));

				expenseSummary = mongo.findOne(query, MonthExpenseCummaryCollection.class);
				if (null != expenseSummary) {

					Update up = new Update();
					up.set("e_ntb_paid", expenseSummary.getE_ntb_paid() + (amount_paid / usercount));
					mongo.updateFirst(query, up, MonthExpenseCummaryCollection.class);

				} else {
					expenseSummary = new MonthExpenseCummaryCollection();
					expenseSummary.setYr(request.get("yr").toString());
					expenseSummary.setMnth(request.get("mnth").toString());
					expenseSummary.setE_paid(0);
					expenseSummary.setE_ntb_paid(amount_paid / usercount);
					expenseSummary.setUn(user);
					expenseSummary.setAb_paid(0);
					expenseSummary.setAb_ntb_paid(0);

					mongo.insert(expenseSummary);
				}
			}
		}
		
		botHeart.sendExpenseAddedMessage(request.get("cat"), amount_paid, request.get("un"));

		log.info("EXCMPLTD : addExpense : "+(new Date().getTime()-time)+" ms");
	}

	@Override
	public List<ExpenseCollection> getMonthExpense(String yr, String mnth) throws Exception {

		time = new Date().getTime();
		
		Query query = new Query();
		query.addCriteria(Criteria.where("mnth").is(mnth));
		query.addCriteria(Criteria.where("yr").is(yr));
		List<ExpenseCollection> expenses = mongo.find(query, ExpenseCollection.class);
		
		log.info("EXCMPLTD : getMonthExpense : "+(new Date().getTime()-time)+" ms");
		
		return expenses;
	}

	@Override
	public List<ExpenseCollection> getMyExpenseList(HashMap<String, String> request) throws Exception {
		
		time = new Date().getTime();
		
		query = new Query();
		query.addCriteria(Criteria.where("mnth").is(request.get("mnth")));
		query.addCriteria(Criteria.where("yr").is(request.get("yr")));
		query.addCriteria(Criteria.where("un").is(request.get("un")));
		List<ExpenseCollection> expenses = mongo.find(query, ExpenseCollection.class);

		log.info("EXCMPLTD : getMyExpenseList : "+(new Date().getTime()-time)+" ms");
		
		return expenses;
	}

	@Override
	public ExpenseCollection removeExpenseByObjectId(String objectId) throws Exception {
		
		time = new Date().getTime();

		query = new Query();
		query.addCriteria(Criteria.where("_id").is(new ObjectId(objectId)));

		ExpenseCollection expense = mongo.findOne(query, ExpenseCollection.class);

		mongo.remove(query, ExpenseCollection.class);

		log.info("EXCMPLTD : removeExpenseByObjectId : "+(new Date().getTime()-time)+" ms");
		
		return expense;
	}

	@Override
	public void clearRepo() {
		
		time = new Date().getTime();
		
		mongo.remove(ExpenseCollection.class);
		
		log.info("EXCMPLTD : clearRepo : "+(new Date().getTime()-time)+" ms");
		
	}
}
