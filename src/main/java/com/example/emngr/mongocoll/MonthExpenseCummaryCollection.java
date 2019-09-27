package com.example.emngr.mongocoll;

import java.util.HashMap;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("em-expense-summary")
public class MonthExpenseCummaryCollection {
	private String un;
	private String mnth;
	private String yr;
	private float e_paid;
	private float ab_paid;
	private float e_ntb_paid;
	private float ab_ntb_paid;
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
	public String getYr() {
		return yr;
	}
	public void setYr(String yr) {
		this.yr = yr;
	}
	public float getE_paid() {
		return e_paid;
	}
	public void setE_paid(float e_paid) {
		this.e_paid = e_paid;
	}
	public float getAb_paid() {
		return ab_paid;
	}
	public void setAb_paid(float ab_paid) {
		this.ab_paid = ab_paid;
	}
	public float getE_ntb_paid() {
		return e_ntb_paid;
	}
	public void setE_ntb_paid(float e_ntb_paid) {
		this.e_ntb_paid = e_ntb_paid;
	}
	public float getAb_ntb_paid() {
		return ab_ntb_paid;
	}
	public void setAb_ntb_paid(float ab_ntb_paid) {
		this.ab_ntb_paid = ab_ntb_paid;
	}
	
	public void setEmptyData(HashMap<String, String> dateObj)
	{
		this.ab_ntb_paid=0;
		this.ab_paid=0;
		this.e_ntb_paid=0;
		this.e_paid=0;
		this.mnth=dateObj.get("mnth");
		this.yr=dateObj.get("yr");
	}
	
	
}

