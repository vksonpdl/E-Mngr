package com.example.emngr.mongorepo;


import java.util.HashMap;
import java.util.List;

import com.example.emngr.bean.TelegramBean;
import com.example.emngr.mongocoll.UserCollection;

public interface CredentialRepo {
	public boolean createCredential(HashMap<String, String> credential)throws Exception;
	public UserCollection validateCredential(HashMap<String, String> credential)throws Exception;
	public List<UserCollection> getUsersCollection()throws Exception;
	public List<String> getUserList()throws Exception;
	public boolean updatePassword(String un, String pwd,Boolean isDefPwd) throws Exception;
	public List<String> getEmailList()throws Exception;
	public String getEmailbyUn(String un)throws Exception;
	
	
	public boolean isUserExistbyBotUn(String botUn, String chatId);
	public String getUnbyBotUn(String botUn);
	public String getBotUnbyUn(String un);
	
	public List<TelegramBean> getTelegramObjects () throws Exception;

}
