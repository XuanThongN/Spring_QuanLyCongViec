package com.xuanthongn.spring_quanlycongviec.config;


import org.springframework.security.crypto.password.PasswordEncoder;

public class UserPasswordEncoder  implements PasswordEncoder {
	 @Override
	    public String encode(CharSequence rawPassword) {
	        // Implement your custom encoding logic here
	        // Return the encoded password
	        return rawPassword.toString();
	    }

	    @Override
	    public boolean matches(CharSequence rawPassword, String encodedPassword) {
	        // Implement your custom matching logic here
	        // Compare the rawPassword with the encodedPassword
	        return rawPassword.toString().equals(encodedPassword);
	    }
}
