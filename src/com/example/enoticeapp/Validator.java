package com.example.enoticeapp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {


	  private Pattern pattern;
	  private Matcher matcher;
	  private static final String MOBILE_PATTERN = "\\d{10}";
	  
	  /*
	   ^			   #start of the line
  	[_A-Za-z0-9-\\+]+  # must start with string in the bracket [ ], must contains one or more (+)
  	(			       # start of group #1
    \\.[_A-Za-z0-9-]+  # follow by a dot "." and string in the bracket [ ], must contains one or more (+)
  	)*			       # end of group #1, this group is optional (*)
    @				   # must contains a "@" symbol
     [A-Za-z0-9-]+     	# follow by string in the bracket [ ], must contains one or more (+)
      (				   	# start of group #2 - first level TLD checking
       \\.[A-Za-z0-9]+ 	#  follow by a dot "." and string in the bracket [ ], must contains one or more (+)
      )*				# end of group #2, this group is optional (*)
      (					# start of group #3 - second level TLD checking
       \\.[A-Za-z]{2,}  # follow by a dot "." and string in the bracket [ ], with minimum length of 2
      )					# end of group #3
	 $			        # end of the line
	 
	 The combination means, email address must start with “_A-Za-z0-9-\\+” , optional follow by “.[_A-Za-z0-9-]“, 
	 and end with a “@” symbol. The email’s domain name must start with “A-Za-z0-9-”, follow by first level Tld (.com, .net) “.
	 [A-Za-z0-9]” and optional follow by a second level Tld (.com.au, .com.my) “\\.[A-Za-z]{2,}”, 
	 where second level Tld must start with a dot “.” and length must equal or more than 2 characters.
	   
	   */
	  private static final String EMAIL_PATTERN="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";//"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@”+”[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	  private static final String NAME_PATTERN = "[A-Z][a-zA-Z]*";
	  /*
  		(			# Start of group
  		(?=.*\d)		#   must contains one digit from 0-9
  		(?=.*[a-z])		#   must contains one lowercase characters
  		(?=.*[A-Z])		#   must contains one uppercase characters
  		(?=.*[@#$%])	#   must contains one special symbols in the list "@#$%"
              .		    #   match anything with previous condition checking
                {6,20}	#   length at least 6 characters and maximum of 20	
		)			    #   End of group
		
		?= – means apply the assertion condition, meaningless by itself, always work with other combination

Whole combination is means, 6 to 20 characters string with at least one digit, one upper case letter, one lower 
case letter and one special symbol (“@#$%”).
This regular expression pattern is very useful to implement a strong and complex password.
 */
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

	  /*public Validator(){
		  pattern1 = Pattern.compile(PASSWORD_PATTERN);
		  pattern2 = Pattern.compile(USERNAME_PATTERN);
	  }*/

	  /**
	   * Validate password with regular expression
	   * @param password password for validation
	   * @return true valid password, false invalid password
	   */
	  public boolean validatePass(final String password){
		  pattern = Pattern.compile(PASSWORD_PATTERN);
		  matcher = pattern.matcher(password);
		  if(matcher.matches())
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
		  pattern = Pattern.compile(USERNAME_PATTERN);
		  matcher = pattern.matcher(username);
		  if(matcher.matches())
			  return true;
		  else
			  return false;

	  }
	  
	  /**
	   * Validate email with regular expression
	   * @param email for validation
	   * @return true valid email, false invalid email
	   */
	  public boolean validateEmail(final String email){
		  pattern = Pattern.compile(EMAIL_PATTERN);
		  matcher = pattern.matcher(email);
		  if(matcher.matches())
			  return true;
		  else
			  return false;

	  }
	  
	  /**
	   * Validate mobile with regular expression
	   * @param mobile for validation
	   * @return true valid mobile, false invalid mobile
	   */
	  public boolean validateMobile(final String mobile){
		  pattern = Pattern.compile(MOBILE_PATTERN);
		  matcher = pattern.matcher(mobile);
		  if(matcher.matches())
			  return true;
		  else
			  return false;

	  }
	  /**
	   * Validate name with regular expression
	   * @param name for validation
	   * @return true valid name, false invalid name
	   */
	  public boolean validateName(final String name){
		  pattern = Pattern.compile(NAME_PATTERN);
		  matcher = pattern.matcher(name);
		  if(matcher.matches())
			  return true;
		  else
			  return false;

	  }
	  
}
