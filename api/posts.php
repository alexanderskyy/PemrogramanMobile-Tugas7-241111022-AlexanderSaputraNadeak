<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json");
$url = "https://jsonplaceholder.typicode.com/posts";
$response = file_get_contents($url);
echo $response;