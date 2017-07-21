<?php
require_once __DIR__ . '/database/db_connect.php';
$db = new DB_CONNECT();
mysql_query("INSERT INTO Improvements(Name,Email,Message) values('" . $_POST["name"] . "','" . $_POST["email"] . "','" . $_POST["message"] . "');") or die(mysql_error());
echo "Thanks for your suggestion!";
?>
