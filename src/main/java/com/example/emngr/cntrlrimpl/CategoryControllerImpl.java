package com.example.emngr.cntrlrimpl;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.emngr.cntrlr.CategoryController;
import com.example.emngr.mongocoll.CategoryCollection;
import com.example.emngr.mongorepoimpl.CategoryRepoImpl;


@RestController
public class CategoryControllerImpl implements CategoryController {

	@Autowired
	CategoryRepoImpl categoryRepo;
	
	
	@Override
	@RequestMapping("/getcategories")
	public ArrayList<String> getCategory() {
		ArrayList<String> categoryList = new ArrayList<String>();
		try {
		
			ArrayList<CategoryCollection> categoryCollectionList = categoryRepo.getCategories();
			Iterator<CategoryCollection> iterator = categoryCollectionList.iterator();
			while(iterator.hasNext()) {			
				categoryList.add(iterator.next().getCat());
			}
		}
		catch (Exception e) {
			
		}
		
		

		return categoryList;
	}

}
