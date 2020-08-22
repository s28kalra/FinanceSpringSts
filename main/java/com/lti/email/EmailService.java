package com.lti.email;

import org.springframework.stereotype.Component;

@Component
public class EmailService {
	
	public int sendOtpEmail(String number) {
		return 0;
	}
	
	
	public boolean sendRegisterEmail(String customerFirstName, String number) {
		return false;
	}

}
