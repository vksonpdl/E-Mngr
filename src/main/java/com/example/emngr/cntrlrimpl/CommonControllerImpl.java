package com.example.emngr.cntrlrimpl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.emngr.mongorepo.MongoTestRepo;

@RestController
public class CommonControllerImpl {
	
	@Autowired
	public MongoTestRepo mongoRepo;

	private HashMap<String, String> responseMap;

	@RequestMapping(value = "/healthcheck", method = RequestMethod.GET)
	public HashMap<String, String> healthcheck() {

		responseMap = new HashMap<String, String>();
		responseMap.put("health", "Ok");
		
		try {
			
			String count = mongoRepo.getDoc();
			responseMap.put("Mongo", "Success");
			responseMap.put("MongoCollectionCount", count);
			
		}
		catch (Exception e) {
			responseMap.put("Mongo", "Exception");
			responseMap.put("MongoException", e.toString());
			e.printStackTrace();
		}
		return responseMap;
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	
	public HashMap<String, String> test(@RequestParam String name) {

		responseMap = new HashMap<String, String>();
		responseMap.put("health", "Ok");
		if(null!=name && name.length()>1) {
			responseMap.put(name, name);
			
		}
		
		return responseMap;
	}

}
