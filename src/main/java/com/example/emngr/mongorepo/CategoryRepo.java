package com.example.emngr.mongorepo;

import java.util.ArrayList;

import com.example.emngr.mongocoll.CategoryCollection;

public interface CategoryRepo {
	public ArrayList<CategoryCollection> getCategories () throws Exception;

}
