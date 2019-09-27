package com.example.emngr.mongorepoimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.example.emngr.mongocoll.AmountBalancerCollection;
import com.example.emngr.mongocoll.ExpenseCollection;
import com.example.emngr.mongocoll.MonthExpenseCummaryCollection;
import com.example.emngr.mongorepo.CompleteSummaryRepo;

@Component
public class CompleteSummaryRepoImpl implements CompleteSummaryRepo {
	
	private static Logger log = LoggerFactory.getLogger(CompleteSummaryRepoImpl.class);

	@Autowired
	MongoTemplate mongo;
	private Query query;
	private Query query_temp;
	private Update update;
	private MonthExpenseCummaryCollection monthSummary;
	private Long time;

	@Override
	public List<MonthExpenseCummaryCollection> getCurrentMonthSummary(String mnth, String yr) throws Exception {
		time = new Date().getTime();
		query = new Query();
		query.addCriteria(Criteria.where("mnth").is(mnth));
		query.addCriteria(Criteria.where("yr").is(yr));
		query.with(new Sort(Sort.Direction.ASC,"un"));
		log.info("EXCMPLTD : getCurrentMonthSummary : "+(new Date().getTime()-time)+" ms");
		
		return mongo.find(query, MonthExpenseCummaryCollection.class);
		
	}

	@Override
	public void removeExpense(ExpenseCollection expense, int userCount, HashMap<String, String> dateObj)
			throws Exception {
		time = new Date().getTime();
		query = new Query();
		query.addCriteria(Criteria.where("yr").is(dateObj.get("yr")));
		query.addCriteria(Criteria.where("mnth").is(dateObj.get("mnth")));

		List<MonthExpenseCummaryCollection> monthExpenseList = mongo.find(query, MonthExpenseCummaryCollection.class);

		for (MonthExpenseCummaryCollection monthExpense : monthExpenseList) {

			if (monthExpense.getUn().equals(expense.getUn())) {

				query = new Query();
				query.addCriteria(Criteria.where("yr").is(dateObj.get("yr")));
				query.addCriteria(Criteria.where("mnth").is(dateObj.get("mnth")));
				query.addCriteria(Criteria.where("un").is(monthExpense.getUn()));
				update = new Update();
				update.set("e_paid", monthExpense.getE_paid() - expense.getExp());
				update.set("e_ntb_paid",
						monthExpense.getE_ntb_paid() + (expense.getExp() - (expense.getExp() / userCount)));
				mongo.updateFirst(query, update, MonthExpenseCummaryCollection.class);

			} else {

				update = new Update();
				update.set("e_ntb_paid", monthExpense.getE_ntb_paid() - (expense.getExp() / userCount));

				query_temp = new Query();
				query_temp.addCriteria(Criteria.where("un").is(monthExpense.getUn()));
				query_temp.addCriteria(Criteria.where("yr").is(dateObj.get("yr")));
				query_temp.addCriteria(Criteria.where("mnth").is(dateObj.get("mnth")));
				mongo.updateFirst(query_temp, update, MonthExpenseCummaryCollection.class);

			}

		}

		log.info("EXCMPLTD : removeExpense : "+(new Date().getTime()-time)+" ms");
	}

	@Override
	public void clearRepo() {
		time = new Date().getTime();
		mongo.remove(MonthExpenseCummaryCollection.class);
		log.info("EXCMPLTD : clearRepo : "+(new Date().getTime()-time)+" ms");
	}

	@Override
	public void removeAmount(AmountBalancerCollection amount, HashMap<String, String> request) throws Exception {

		time = new Date().getTime();
		
		List<String> usList = new ArrayList<>();
		usList.add(amount.getUn_frm());
		usList.add(amount.getUn_to());

		query = new Query();
		query.addCriteria(Criteria.where("yr").is(request.get("yr")));
		query.addCriteria(Criteria.where("mnth").is(request.get("mnth")));
		query.addCriteria(Criteria.where("un").in(usList));
		List<MonthExpenseCummaryCollection> monthSummaryList = mongo.find(query, MonthExpenseCummaryCollection.class);
		for (MonthExpenseCummaryCollection monthSummary : monthSummaryList) {

			query_temp = new Query();
			update = new Update();
			query_temp.addCriteria(Criteria.where("un").is(monthSummary.getUn()));

			if (monthSummary.getUn().equals(amount.getUn_frm())) {
				Float updatedAmnt = monthSummary.getAb_paid() - amount.getAmnt();

				update.set("ab_paid", updatedAmnt);
				mongo.updateFirst(query_temp, update, MonthExpenseCummaryCollection.class);

			} else {
				Float updatedAmnt = monthSummary.getAb_ntb_paid() - amount.getAmnt();
				update.set("ab_ntb_paid", updatedAmnt);
				mongo.updateFirst(query_temp, update, MonthExpenseCummaryCollection.class);
			}

		}
		
		log.info("EXCMPLTD : removeAmount : "+(new Date().getTime()-time)+" ms");
	}

	@Override
	public void refreshCurrentSummaryCollection(List<String> usernames, HashMap<String, String> dateObj)
			throws Exception {

		time = new Date().getTime();
		
		query = new Query();
		List<String> usernamesFromSummmary = new ArrayList<String>();

		query.addCriteria(Criteria.where("yr").is(dateObj.get("yr")));
		query.addCriteria(Criteria.where("mnth").is(dateObj.get("mnth")));

		List<MonthExpenseCummaryCollection> monthSummaryList = mongo.find(query, MonthExpenseCummaryCollection.class);

		for (MonthExpenseCummaryCollection monthSummary : monthSummaryList) {
			usernamesFromSummmary.add(monthSummary.getUn());

		}
		
		for (String user : usernames) {
			if(!usernamesFromSummmary.contains(user)) {
				monthSummary = new MonthExpenseCummaryCollection();
				monthSummary.setUn(user);
				monthSummary.setEmptyData(dateObj);
				mongo.insert(monthSummary);
				
			}
			
		}
		
		log.info("EXCMPLTD : refreshCurrentSummaryCollection : "+(new Date().getTime()-time)+" ms");

	}

}
