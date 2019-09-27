package com.example.emngr.utl;

import java.util.HashMap;

public interface Util {
	
	public HashMap<String, String> getThisMonth ();
	public HashMap<String, String> getPreviuosMonth ();
	public String encryptPassword(String pwd,String un) throws Exception; 

}
