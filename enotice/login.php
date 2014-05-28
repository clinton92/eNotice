<?php
	
/*
 * Following code authenticate the user.
 * 
 */
   
   if(isset($_POST['username']) && isset($_POST['password'])){
	$username = $_POST['username'];
	$password = $_POST['password'];
   
    	require_once __DIR__ . '/db_connect.php';
 
   	 // connecting to database
    	$db = new DB_CONNECT();
		
	//Array for JSON response
	$response = array();
	$response['success']=-1;
	$response['user_data']=array();

	$user_data = array();
	
    	$login = "SELECT * FROM gcmuser WHERE user='$username' AND pass='$password';";
	$result = mysql_query($login);
	
     	// Mysql_num_row is Counting table row
    	$count = mysql_num_rows($result);
        // if it matches it counts to be one 1
    	if($count==1)
    	{
	    session_start();
   	    $session = array();
            //On Success
	    $response['success']=1;
	    $session['valid_user']=$username;
	    if($username=="admin"){
		$user_data['user']=$username;
		$user_data['user_type']=1;
		$session['user_type']=1;
	    }
	    else{
		$user_data['user']=$username;
		$user_data['user_type']=2;
		$session['user_type']=2;
		}
	    array_push($response['user_data'],$user_data);
	    echo json_encode($response);
         
    	}
    	else
    	{
   	    // On Failure
	    $response['success']=0;
	    $response['user_data']=null;
	    echo json_encode($response);
            
        }

    }
?>

