package com.contactura.contactura;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {

	public static void main(String[] args) {
		
		 BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	     String encodedPassword = passwordEncoder.encode("root");
	         
	        System.out.println(encodedPassword);
	}

}
