<?php
    require "dbconnect.php";
    $user_id = $_POST['user_id'];
    $size_id = $_POST['size_id'];
    $product_name = $_POST['product_name'];
    $quantity = $_POST['quantity'];
    $get_cart = "SELECT size_id FROM Cart_FashionShop WHERE user_id = '$user_id'";
    $insert_cart = "INSERT INTO Cart_FashionShop VALUES (null,'$user_id','$product_name','$size_id','$quantity')";
    $data2 = mysqli_query($connect,$get_cart);
    $check = 0;
    while($row = mysqli_fetch_assoc($data2)){
        if($size_id == $row['size_id']){
            $check++;
        }
    }
    if($check>0){
        echo "duplicated";
    }else{
        $data = mysqli_query($connect,$insert_cart);
        if($data){
            echo "ok";
        }else{
            echo "fail".mysqli_error($connect);
        }
    }
?>