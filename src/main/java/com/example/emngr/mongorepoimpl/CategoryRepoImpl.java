package com.example.emngr.mongorepoimpl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.example.emngr.mongocoll.CategoryCollection;
import com.example.emngr.mongorepo.CategoryRepo;

@Component
public class CategoryRepoImpl implements CategoryRepo{
	@Autowired
	MongoTemplate mongotemp;
	private Query query;

	@Override
	public ArrayList<CategoryCollection> getCategories() throws Exception {
		
		query = new Query();
		query.with(new Sort(Sort.Direction.ASC,"cat"));
		
		return (ArrayList<CategoryCollection>) mongotemp.find(query,CategoryCollection.class);
		
	}

}
