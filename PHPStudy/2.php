<?php
    //phpinfo();
    //echo $_SERVER["HTTP_USER_AGENT"]
    class Car{
        var $color;
        function __construct()
        {
            echo "初始化类...";
            $color = "red";
        }
        function getColor(){
            return $this->color;
        }
    }
    function getHell(){
        echo "HelloWrold";
    }


?>


