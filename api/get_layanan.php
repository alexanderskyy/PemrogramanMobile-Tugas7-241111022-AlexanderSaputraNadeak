<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json");

$conn = new mysqli("localhost", "root", "", "satwalaya");
$conn->set_charset("utf8");

$result = [];
$tables = ["penitipan","grooming","konsultasi","vaksinasi"];
$jenis  = ["Penitipan","Grooming","Konsultasi","Vaksinasi"];

for($i = 0; $i < count($tables); $i++){
    $res = $conn->query("SELECT * FROM ".$tables[$i]);
    while($row = $res->fetch_assoc()){
        $row["jenis_layanan"] = $jenis[$i];
        $result[] = $row;
    }
}

echo json_encode($result);
$conn->close();