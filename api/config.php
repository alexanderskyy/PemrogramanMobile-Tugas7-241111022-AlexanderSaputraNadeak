<?php
$host = "localhost";
$user = "root";
$pass = "";
$db   = "satwalaya";

$conn = new mysqli($host, $user, $pass, $db);
if ($conn->connect_error) {
    http_response_code(500);
    die(json_encode(["error" => "Koneksi gagal: " . $conn->connect_error]));
}
$conn->set_charset("utf8");