package com.example.emngr.utl;

import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class UtilImpl implements Util {
	
	private static final String PWD_DATA="@#$&qwertyuiopasdfghjklzxcvbnm@#$&0123456789@#$&MNBVCXZLKJHGFDSAPOIUYTREWQ@#$&";

	@SuppressWarnings("deprecation")
	@Override
	public HashMap<String, String> getThisMonth() {
		Date date = new Date();

		HashMap<String, String> dateObj = new HashMap<String, String>();
		dateObj.put("dy", date.getDate() + "");
		dateObj.put("mnth", (date.getMonth() + 1) + "");
		dateObj.put("yr", (date.getYear() + 1900) + "");
		dateObj.put("tmstmp", (date.getTime()+""));

		return dateObj;
	}
	
	
	@SuppressWarnings("deprecation")
	@Override
	public HashMap<String, String> getPreviuosMonth() {
		Date date = new Date();

		HashMap<String, String> dateObj = new HashMap<String, String>();
		
		int date_int = date.getDate();
		int mnth_int= date.getMonth() + 1;
		int yr_int = date.getYear() + 1900;
		
		if(mnth_int==1) {
			mnth_int =12;
			yr_int--;
		}
		else {
			mnth_int--;
		}
		
		dateObj.put("dy", date_int + "");
		dateObj.put("mnth", mnth_int + "");
		dateObj.put("yr", yr_int+ "");
		dateObj.put("tmstmp", (date.getTime()+""));

		return dateObj;
	}

	@Override
	public String encryptPassword(String pwd,String un) throws Exception {
		byte[] salt = un.getBytes();
		String securePassword = nu_getSecurePassword(pwd, salt);
		return securePassword;
	}

	private static String nu_getSecurePassword(String passwordToHash, byte[] salt) {
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(salt);
			byte[] bytes = md.digest(passwordToHash.getBytes());
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}
	
	public String getRandomPassword(int pwdLength) {
		
		Random random = new Random();
		StringBuilder builder = new StringBuilder();
		for(int i=0;i<pwdLength;i++) {
			
			builder.append(PWD_DATA.charAt(random.nextInt(PWD_DATA.length())));
			
			
		}
		
		return builder.toString();
	}
}
