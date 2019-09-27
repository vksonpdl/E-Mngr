package com.example.emngr.mongorepo;


import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class MongoTestRepo {

	@Autowired
	private MongoTemplate templete;
	
	public String getDoc() throws Exception{
		String ret;
		
		Set<String> names= templete.getCollectionNames();
		int num = names.size();
		ret = num+"";
		
		return ret;
	}
}
