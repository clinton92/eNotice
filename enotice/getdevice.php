<?php
	$regid = $_POST["regid"];
	$first = $_POST["first"];
	$last = $_POST["last"];
	$user = $_POST["user"];
	$pass = $_POST["pass"];
	$email = $_POST["email"];
	$mobile = $_POST["mobile"];
	$con = mysql_connect("localhost","root","priyanka");
	mysql_select_db("enotice",$con);
// replace sql query according to your mysql table
	mysql_query("INSERT INTO gcmuser(regid,first,last,user,pass,email,mobile) VALUES('".$regid."','".$first."', '".$last."', '".$user."', '".$pass."', '".$email."', '".$mobile."')");
        echo "successs";

	
	
	
?>
