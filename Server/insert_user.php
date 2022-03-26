<?php
    require "dbconnect.php";
    $taikhoan = $_POST['taikhoan'];
    $matkhau = $_POST['matkhau'];
    $check_user_name = 0;
    $str_get_user_name = "SELECT user_name FROM User_FashionShop";
    $data = mysqli_query($connect,$str_get_user_name);
    $data_user_name = array();
    while($row = mysqli_fetch_assoc($data)){
        array_push($data_user_name,$row['user_name']);
    }
    foreach($data_user_name as $user_name){
        if($user_name == $taikhoan){
            $check_user_name++;
        }
    }
    if(strlen($taikhoan)>0 && strlen($matkhau)>0 && $check_user_name == 0){
        $query = "INSERT INTO User_FashionShop VALUES (null,'$taikhoan','$matkhau','empty',
        'empty','empty','empty','1900-01-01','empty',0,'empty')";
        $data = mysqli_query($connect,$query);
        if($data){
            echo "Success";
            }else{
            echo "Failed".mysql_error($connect);
            }
        }else{
            if($check_user_name >0){
                echo "Duplicated username";
            }else{
               echo "Null"; 
            }
        }
?>