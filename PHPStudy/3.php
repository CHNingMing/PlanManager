<?php
onRequest();
function onRequest(){
    // header("Content-Type:text/json; charset=utf-8");
    // header("Access-Control-Allow-Origin: *");
    $resp = array("state"=>"1","msg"=>$_SERVER["REQUEST_URI"]);
    echo json_encode($resp);
}


?>