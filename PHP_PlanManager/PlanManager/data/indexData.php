
<?php
    //连接对象
    static $conn;
    //连接数据库
    function connDatabase_data(){
        global $conn;
        

        $servername = "kqktrsb2.2364.dnstoo.com";
        $username = "ym970u_f";
        $password = "a203358";
        $databaseName = 'ym970u';

/*        $servername = "localhost";
        $username = "s1506106";
        $password = "jxMQ65lpBB";
        $databaseName = 's1506106';*/


        $conn = new mysqli($servername,$username,$password,$databaseName);
        if( $conn -> connect_errno ){
            echo "<h1>connection error!</h1>";
            echo $conn;
            return;
        }
    }
    
    
    
?>