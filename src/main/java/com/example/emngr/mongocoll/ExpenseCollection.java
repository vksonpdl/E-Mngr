package com.example.emngr.mongocoll;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("em-expense")
public class ExpenseCollection {
	@Id
	private ObjectId id;
	private String cat;
	private float exp;
	private String un;
	private String mnth;
	private String dy;
	private String yr;
	private String tmstmp;
	
	
	
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getCat() {
		return cat;
	}
	public void setCat(String cat) {
		this.cat = cat;
	}
	public float getExp() {
		return exp;
	}
	public void setExp(float exp) {
		this.exp = exp;
	}
	public String getUn() {
		return un;
	}
	public void setUn(String un) {
		this.un = un;
	}
	public String getMnth() {
		return mnth;
	}
	public void setMnth(String mnth) {
		this.mnth = mnth;
	}
	public String getDy() {
		return dy;
	}
	public void setDy(String dy) {
		this.dy = dy;
	}
	public String getYr() {
		return yr;
	}
	public void setYr(String yr) {
		this.yr = yr;
	}
	public String getTmstmp() {
		return tmstmp;
	}
	public void setTmstmp(String tmstmp) {
		this.tmstmp = tmstmp;
	}
	
	

}
