package com.example.emngr.bean;

public class ExpenseStatementBean {
	private String un;
	private float ep;
	private float etbp;
	
	public ExpenseStatementBean() {
		this.etbp=0;
		this.ep=0;
	}
	public String getUn() {
		return un;
	}
	public void setUn(String un) {
		this.un = un;
	}
	public float getEp() {
		return ep;
	}
	public void setEp(float ep) {
		this.ep = ep;
	}
	public float getEtbp() {
		return etbp;
	}
	public void setEtbp(float etbp) {
		this.etbp = etbp;
	}
	
	

}
