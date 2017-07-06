<?php
require_once __DIR__ . '/db_connect.php';
$db = new DB_CONNECT();

if (!empty($_POST["message"])) {
    mysql_query("INSERT INTO Improvements(Name,Email,Message) values('" . $_POST["name"] . "','" . $_POST["email"] . "','" . $_POST["message"] . "');") or die(mysql_error());
    echo "Thank you for your suggestion!";
}
else{
    echo "Please enter a message";
}
?>
