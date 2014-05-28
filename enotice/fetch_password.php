<?php
 
/*
 * Following code will get password from server based on email address.
 * 
 */
 
    // array for JSON response
    $response = array();

    require_once __DIR__ . '/db_connect.php';

    $db = new DB_CONNECT();

    // get password from gcmuser table
    $phone=$_POST['phone'];
    $q="SELECT pass from gcmuser WHERE mobile='9888903138'";//".$phone."'";
    $query = mysql_query($q);
    $count = mysql_num_rows($query);
 
    if (!empty($query)) {
	if($count==1){
	        while($result=mysql_fetch_array($query)) {
			$response['success']=1;
			$response['password']=$result['pass'];
			
		}

	}
	else if($count==0){
		$response['success']=0;
		$response['message']="This mobile number is not registered";
	}
	else{
		$response['success']=0;
		$response['message']="Not able to connect to server";
	}
    }

    echo json_encode($response);
 	
?>
