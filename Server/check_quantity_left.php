<?php
    require "dbconnect.php";
    $size_id = $_POST['size_id'];
    $quantity = $_POST['quantity'];
    $get_quantity = "SELECT quantity FROM Product_Size_FashionShop WHERE product_size_id = '$size_id'";
    $data = mysqli_query($connect,$get_quantity);
    $quantity_left = '';
    if(!$data){
        echo mysql_error($connect);
    }
    while($row = mysqli_fetch_assoc($data)){
        $quantity_left = $row['quantity'];
    }
    if($quantity_left >= $quantity){
        echo "ok";
    }else{
        echo "fail";
    }
?>