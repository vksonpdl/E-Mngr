package com.example.emngr.mongocoll;



import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("em-amntblncr")
public class AmountBalancerCollection {

	private String un_frm;
	private String un_to;
	private float amnt;
	private String rsn;
	private String mnth;
	private String dy;
	private String yr;
	private String tmstmp;

	@Id
	private ObjectId id;
	
	


	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getUn_frm() {
		return un_frm;
	}

	public void setUn_frm(String un_frm) {
		this.un_frm = un_frm;
	}

	public String getUn_to() {
		return un_to;
	}

	public void setUn_to(String un_to) {
		this.un_to = un_to;
	}

	public float getAmnt() {
		return amnt;
	}

	public void setAmnt(float amnt) {
		this.amnt = amnt;
	}

	public String getRsn() {
		return rsn;
	}

	public void setRsn(String rsn) {
		this.rsn = rsn;
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
