package com.example.enoticeapp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {


	  private Pattern pattern1, pattern2;
	  private Matcher matcher1, matcher2;

	  private static final String PASSWORD_PATTERN = 
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
	  
	  /* 	^            # Start of the line
  			[a-z0-9_-]	 # Match characters and symbols in the list, a-z, 0-9, underscore, hyphen
            {3,15}       # Length at least 3 characters and maximum length of 15 
			$            # End of the line

		Whole combination is means, 3 to 15 characters with any lower case character,
		digit or special symbol “_-” only. 
		
	   */
	  private static final String USERNAME_PATTERN = "^[a-z0-9_-]{3,15}$";

	  public Validator(){
		  pattern1 = Pattern.compile(PASSWORD_PATTERN);
		  pattern2 = Pattern.compile(USERNAME_PATTERN);
	  }

	  /**
	   * Validate password with regular expression
	   * @param password password for validation
	   * @return true valid password, false invalid password
	   */
	  public boolean validatePass(final String password){

		  matcher1 = pattern1.matcher(password);
		  if(matcher1.matches())
			  return true;
		  else
			  return false;

	  }
	  
	  /**
	   * Validate username with regular expression
	   * @param username username for validation
	   * @return true valid username, false invalid username
	   */
	  public boolean validateUser(final String username){

		  matcher2 = pattern2.matcher(username);
		  if(matcher2.matches())
			  return true;
		  else
			  return false;

	  }
}
