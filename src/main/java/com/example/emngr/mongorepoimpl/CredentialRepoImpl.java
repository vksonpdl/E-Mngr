package com.example.emngr.mongorepoimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;

import com.example.emngr.bean.TelegramBean;
import com.example.emngr.mongocoll.UserCollection;
import com.example.emngr.mongorepo.CredentialRepo;
import com.mongodb.bulk.UpdateRequest;
import com.mongodb.client.result.UpdateResult;

@Component
public class CredentialRepoImpl implements CredentialRepo {

	private static Logger log = LoggerFactory.getLogger(CredentialRepoImpl.class);

	@Autowired
	MongoTemplate mongoTemplate;
	UserCollection userColl;
	private Query query;
	private Update update;

	boolean returnBoolean;
	String returnString;
	private Long time;

	@Override
	public boolean createCredential(HashMap<String, String> credential) throws Exception {

		time = new Date().getTime();

		userColl = new UserCollection();
		userColl.setName(credential.get("name"));
		userColl.setPwd(credential.get("pwd"));
		userColl.setUn(credential.get("un"));
		userColl.setRl(credential.get("rl"));
		userColl.setEmail(credential.get("email"));

		mongoTemplate.insert(userColl);

		log.info("EXCMPLTD : createCredential : " + (new Date().getTime() - time) + " ms");

		return true;
	}

	@Override
	public UserCollection validateCredential(HashMap<String, String> credential) throws Exception {

		time = new Date().getTime();

		Query query = new Query();
		query.addCriteria(Criteria.where("un").is(credential.get("un")));
		query.addCriteria(Criteria.where("pwd").is(credential.get("pwd")));
		UserCollection coll = mongoTemplate.findOne(query, UserCollection.class);
		if (coll != null) {
			coll.setPwd("NA");
		}

		log.info("EXCMPLTD : validateCredential : " + (new Date().getTime() - time) + " ms");
		return coll;

	}

	@Override
	public List<String> getUserList() throws Exception {

		time = new Date().getTime();

		List<String> userList = new ArrayList<String>();
		List<UserCollection> users = mongoTemplate.findAll(UserCollection.class);
		Iterator<UserCollection> useriterator = users.iterator();
		while (useriterator.hasNext()) {
			userList.add(useriterator.next().getUn());
		}

		log.info("EXCMPLTD : getUserList : " + (new Date().getTime() - time) + " ms");
		return userList;
	}

	@Override
	public boolean updatePassword(String un, String pwd, Boolean isDefPwd) throws Exception {

		time = new Date().getTime();

		returnBoolean = false;
		query = new Query();
		query.addCriteria(Criteria.where("un").is(un));
		
		update = new Update();
		update.set("pwd", pwd);
		update.set("pwddef", isDefPwd);
		UpdateResult result = mongoTemplate.updateFirst(query, update, UserCollection.class);

		if (result.getModifiedCount() > 0) {
			returnBoolean = true;
		}

		log.info("EXCMPLTD : updatePassword : " + (new Date().getTime() - time) + " ms");
		return returnBoolean;
	}

	@Override
	public List<String> getEmailList() throws Exception {

		time = new Date().getTime();

		List<String> emailList = new ArrayList<String>();
		Iterator<UserCollection> users = mongoTemplate.findAll(UserCollection.class).iterator();
		while (users.hasNext()) {
			emailList.add(users.next().getEmail());
		}

		log.info("EXCMPLTD : getEmailList : " + (new Date().getTime() - time) + " ms");

		return emailList;
	}

	@Override
	public boolean isUserExistbyBotUn(String bootun, String chatId) {

		time = new Date().getTime();

		returnBoolean = false;
		query = new Query();
		query.addCriteria(Criteria.where("tel.un").is(bootun));

		update = new Update();
		update.set("tel.chatId", chatId);

		userColl = mongoTemplate.findOne(query, UserCollection.class);

		if (null != userColl) {
			mongoTemplate.updateFirst(query, update, UserCollection.class);
			returnBoolean = true;
		}

		log.info("EXCMPLTD : isUserExistbyBotUn : " + (new Date().getTime() - time) + " ms");
		return returnBoolean;
	}

	@Override
	public String getUnbyBotUn(String bootun) {

		time = new Date().getTime();

		returnString = null;
		query = new Query();
		query.addCriteria(Criteria.where("tel.un").is(bootun));

		userColl = mongoTemplate.findOne(query, UserCollection.class);

		if (null != userColl) {
			returnString = userColl.getUn();
		}

		log.info("EXCMPLTD : getUnbyBotUn : " + (new Date().getTime() - time) + " ms");
		return returnString;
	}

	@Override
	public List<TelegramBean> getTelegramObjects() throws Exception {

		time = new Date().getTime();

		TelegramBean tel;
		List<TelegramBean> telList = new ArrayList<TelegramBean>();
		List<UserCollection> users = mongoTemplate.findAll(UserCollection.class);
		for (UserCollection user : users) {
			tel = user.getTel();
			if (null != tel) {
				telList.add(tel);
			}

		}

		log.info("EXCMPLTD : getTelegramObjects : " + (new Date().getTime() - time) + " ms");
		return telList;
	}

	@Override
	public String getEmailbyUn(String un) throws Exception {
		
		time = new Date().getTime();
		query = new Query();
		query.addCriteria(Criteria.where("un").is(un));
		userColl = mongoTemplate.findOne(query, UserCollection.class);
		log.info("EXCMPLTD : getEmailbyUn : " + (new Date().getTime() - time) + " ms");
		
		return userColl.getEmail();
		
		
	}

	@Override
	public List<UserCollection> getUsersCollection() throws Exception {
		
		return mongoTemplate.findAll(UserCollection.class);
	}

	@Override
	public String getBotUnbyUn(String un) {
		time = new Date().getTime();
		
		query = new Query();
		query.addCriteria(Criteria.where("un").is(un));
		userColl = mongoTemplate.findOne(query, UserCollection.class);
		
		
		log.info("EXCMPLTD : getBotUnbyUn : " + (new Date().getTime() - time) + " ms");
		return userColl.getTel().getUn();
	}

}
