<?php
$title=$_POST['title'];
$description=$_POST['description'];
$filepath=$_POST['filepath'];
require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();
echo "connection done";
// get notices from notices table
if($filepath==null)
$q="insert into notices(title,description) values('".$title."', '".$description."')";
else
$q="insert into notices(title,description,filepath) values('".$title."', '".$description."', '".$filepath."')";
echo "query".$q;

//$q="insert into notices(title,description) values('hello22','ki hal chal')";
$query = mysql_query($q);

$lastid=mysql_insert_id();
echo "Last id;".$lastid;
$q="SELECT * FROM gcmuser";
    $query = mysql_query($q);

    if (!empty($query)) {
        
        while($result=mysql_fetch_array($query)) {
		$registrationIDs[]=$result['regid'];
		}
	}
	foreach($registrationIDs as $id){
		echo "hello";
		echo $id;
	}

//$query
//$id=mysql_insert_id();
$api_key = "AIzaSyBhc4yJrO6m2ZYJftt8Z0RmVWgBECm5Gn8";
$message = array("title" => $title, "description" => $description);
	$url = 'https://android.googleapis.com/gcm/send';
	$fields = array(
                'registration_ids'  => $registrationIDs,
                'data'              => array( "message" => $message ),
                );
	//echo "JSON field: ".json_encode($fields);
	$headers = array(
					'Authorization: key=' . $api_key,
					'Content-Type: application/json');
					
					
					
	$ch = curl_init();
	curl_setopt($ch, CURLOPT_URL, $url);
	curl_setopt( $ch, CURLOPT_POST, true );
	curl_setopt( $ch, CURLOPT_HTTPHEADER, $headers);
	curl_setopt( $ch, CURLOPT_RETURNTRANSFER, true );
	curl_setopt( $ch, CURLOPT_POSTFIELDS, json_encode( $fields ) );
	$result = curl_exec($ch);
	curl_close($ch);

echo "Result is HERE:".$result;


echo "done!";
//$count = mysql_num_rows($query);*/
?>
