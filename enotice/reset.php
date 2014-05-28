<?php
$response = array();

$user="priyanka";
//$_POST['username'];
$pass="pass";//$POST['password'];

// include db connect class
require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();

// updating password
$q="UPDATE gcmuser SET pass='".$pass."' WHERE user='".$user."'";
$query= mysql_query($q);

if($query==1){
$response['success']=1;
$reponse['message']="Password Updated Successfully";
}
else
$response['success']=0;
$reponse['message']="Something Wrong Happens";

// echoing JSON response
echo json_encode($response);
?>
