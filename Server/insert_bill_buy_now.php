<?php
    require "dbconnect.php";
    $user_id = $_POST['user_id'];
    $date = $_POST['date'];
    $price = $_POST['price'];
    $size_id = $_POST['size_id'];
    $insert_bill = "INSERT INTO Bill_FashionShop VALUES (null,'$user_id','$date','Đang chờ','$price')";
    $get_bill_id = "SELECT MAX(bill_id) AS bill_id FROM Bill_FashionShop";
    $data = mysqli_query($connect,$insert_bill);
    $data2 = mysqli_query($connect,$get_bill_id);
    if($data){
        $bill_id='';
        while($row = mysqli_fetch_assoc($data2)){
            $bill_id = $row['bill_id'];
        }
        $insert_detail_bill = "INSERT INTO Detail_Bill_FashionShop VALUES(null,'$size_id','$bill_id',1)";
        $data3 = mysqli_query($connect,$insert_detail_bill);
        if($data3){
            echo "Đặt hàng thành công";
        }else{
            $delete_bill = "DELETE FROM Bill_FashionShop WHERE bill_id = '$bill_id'";
            mysqli_query($connect,$delete_bill);
            echo "Fail".mysql_error($connect);
        }
    }else{
        echo "Fail".mysql_error($connect);
    }
?>