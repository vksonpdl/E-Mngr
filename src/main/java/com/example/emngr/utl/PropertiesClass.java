package com.example.emngr.utl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class PropertiesClass {
	
	@Autowired
	private Environment env;
	
	public String getMailId() {
		return env.getProperty("emngr.mail.id");
	}

}
