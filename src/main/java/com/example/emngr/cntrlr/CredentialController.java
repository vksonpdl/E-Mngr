package com.example.emngr.cntrlr;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

public interface CredentialController {
	
	public HashMap<String, String> registerUser(HashMap<String, String> value);
	public HashMap<String, String> login (HashMap<String, String> value, HttpSession session);
	public HashMap<String, String> changePassword (HashMap<String, String> request, HttpSession session);
	

}
