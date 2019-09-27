package com.example.emngr.mongorepoimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.emngr.mongocoll.CategoryCollection;
import com.example.emngr.mongorepo.AmountBalancerRepo;
import com.example.emngr.mongorepo.CompleteSummaryRepo;

@RestController
public class NotUsedRepoImpl {

	@Autowired
	MongoTemplate mongo;
	CategoryCollection cetegory;
	@Autowired ExpenseRepoImpl expenseRepo;
	@Autowired AmountBalancerRepo amountRepo;
	@Autowired CompleteSummaryRepo completeSummary;
	
	public boolean AddCategory(String cat) {
		
		cetegory= new CategoryCollection();
		cetegory.setCat(cat);
		mongo.insert(cetegory);
		
		return true;
	}
	
	@RequestMapping("/clearall")
	public void clearRepos(){
		expenseRepo.clearRepo();
		amountRepo.clearRepo();
		completeSummary.clearRepo();
	}
	
	
}
