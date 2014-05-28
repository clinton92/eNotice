<?php
 
/*
 * Following code will get notices from server.
 * 
 */
 
// array for JSON response
$response = array();

$flag=$_GET['flag'];
// include db connect class
require_once __DIR__ . '/db_connect.php';
 
// connecting to db/home/priyanka/workspace2
$db = new DB_CONNECT();

// get notices from notices table
$q="SELECT * FROM notices WHERE id>".$flag." ORDER BY date DESC";
$query = mysql_query($q);
$count = mysql_num_rows($query);
 
if (!empty($query)) {
    echo "{\"notices\":[";
    while($result=mysql_fetch_array($query)) {
        $notice = array();
        $notice["id"] = $result["id"];
        $notice["title"] = $result["title"];
        // $notice["attachment"] = $result["attachment"];
        $notice["description"] = $result["description"];
        $notice["date"] = $result["date"];
	$notice["filepath"]=$result["filepath"];
        // success
        $response["success"] = 1;
        // user node
        $response["notice"] = array();
        array_push($response["notice"], $notice);
        // echoing JSON response
        echo json_encode($response);
	$count=$count-1;
	if($count!=0)
		echo ",";
			
    } echo "]}";
} else {
  // no notice found
  $response["success"] = 0;
  $response["message"] = "No notice found";
  // echo no users JSON
  echo json_encode($response);
  }

?>
