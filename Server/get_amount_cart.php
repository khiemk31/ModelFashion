<?php
    require "dbconnect.php";
    $product_name = json_decode($_GET['product_name']);
    $condition = '"'.implode('","',$product_name).'"';
    $get_amount = "SELECT SUM(Product_FashionShop.price*Cart_FashionShop.quantity) AS amount FROM Product_FashionShop INNER JOIN Cart_FashionShop ON Product_FashionShop.product_name = Cart_FashionShop.product_name WHERE Cart_FashionShop.product_name IN ($condition)";
    $data = mysqli_query($connect,$get_amount);
    $amount = 0;
    while($row = mysqli_fetch_assoc($data)){
        $amount = $row['amount'];
    }
    echo $amount;
?>
