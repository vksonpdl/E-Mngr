package com.example.emngr.cntrlrimpl;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.emngr.cntrlr.HomeController;

@Controller

public class HomeControllerImpl implements HomeController {
	
	
	@RequestMapping("/")
	@Override
	public String getmetoHome() {
		//return "../index";
		return "forward:/index.html";
	}
	
	 @RequestMapping(value = "/{[path:[^\\.]*}")
	    public String redirect() {
	        return "forward:/index.html";
	    } 

	
}
