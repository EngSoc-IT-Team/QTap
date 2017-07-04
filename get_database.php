<?php
//connect to DB
require_once __DIR__ . '/db_connect.php';
$db = new DB_CONNECT();
$response = array();

//eng contacts
$result = mysql_query("SELECT * FROM EngineeringContacts") or die(mysql_error());
// check for empty result
if (mysql_num_rows($result) > 0) {
    $response["EngineeringContacts"] = array();
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $product = array();
        $product["Name"] = $row["Name"];
        $product["Email"] = $row["Email"];
        $product["Position"] = $row["Position"];
        $product["Description"] = $row["Description"];
        array_push($response["EngineeringContacts"], $product);
    }
}

//emerg contacts
$result = mysql_query("SELECT * FROM EmergencyContacts") or die(mysql_error());
// check for empty result
if (mysql_num_rows($result) > 0) {
    $response["EmergencyContacts"] = array();
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $product = array();
        $product["Name"] = $row["Name"];
        $product["PhoneNumber"] = $row["PhoneNumber"];;
        $product["Description"] = $row["Description"];
        array_push($response["EmergencyContacts"], $product);
    }
}

//buildings
$result = mysql_query("SELECT * FROM Buildings ORDER BY Name") or die(mysql_error());
// check for empty result
if (mysql_num_rows($result) > 0) {
    $response["Buildings"] = array();
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $product = array();
        $product["Name"] = $row["Name"];
        $product["Purpose"] = $row["Purpose"];
        $product["BookRooms"] = $row["BookRooms"];
        $product["Food"] = $row["Food"];
        $product["ATM"] = $row["ATM"];
        $product["Lat"] = $row["Lat"];
        $product["Lon"] = $row["Lon"];
        array_push($response["Buildings"], $product);
    }
}

//food
$result = mysql_query("SELECT * FROM Food ORDER BY Name") or die(mysql_error());
// check for empty result
if (mysql_num_rows($result) > 0) {
    $response["Food"] = array();
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $product = array();
        $product["Name"] = $row["Name"];
        $product["MealPlan"] = $row["MealPlan"];
        $product["Card"] = $row["Card"];
        $product["Information"] = $row["Information"];
        $product["BuildingID"] = $row["BuildingID"];
        array_push($response["Food"], $product);
    }
}

if (count($response) > 0) {
    $response["Success"] = 1;
} else {
    $response["Success"] = 0;
}

echo json_encode($response);

?>
