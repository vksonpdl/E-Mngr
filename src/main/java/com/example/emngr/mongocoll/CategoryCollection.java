package com.example.emngr.mongocoll;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("em-category")
public class CategoryCollection {

	private String cat;

	public String getCat() {
		return cat;
	}

	public void setCat(String cat) {
		this.cat = cat;
	}
	
	
}
