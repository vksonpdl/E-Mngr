package com.example.emngr.mongocoll;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.emngr.bean.TelegramBean;



@Document("em-credentials")
public class UserCollection {

	@Indexed(unique = true)
	private String un;
	private String pwd;
	private Boolean pwddef;
	private String rl;
	private String name;
	private String email;
	
	private TelegramBean tel;

	
	
	public Boolean getPwddef() {
		return pwddef;
	}

	public void setPwddef(Boolean pwddef) {
		this.pwddef = pwddef;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUn() {
		return un;
	}

	public void setUn(String un) {
		this.un = un;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getRl() {
		return rl;
	}

	public void setRl(String rl) {
		this.rl = rl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TelegramBean getTel() {
		return tel;
	}

	public void setTel(TelegramBean tel) {
		this.tel = tel;
	}

	

}
