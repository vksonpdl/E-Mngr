package com.example.emngr.cntrlr;



import javax.servlet.http.HttpSession;

import com.mongodb.BasicDBObject;

public interface AdminController {
	
	public  BasicDBObject resetAllPasswords(HttpSession session, String un)throws Exception;

}
