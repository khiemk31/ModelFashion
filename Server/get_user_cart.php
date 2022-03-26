<?php
    require "dbconnect.php";
    $user_id = $_GET['user_id'];
    $get_cart = "SELECT * FROM Cart_FashionShop WHERE user_id = '$user_id'";
    $cart = mysqli_query($connect,$get_cart);
    $arr_cart_product = array();
    class CartProduct{
        function CartProduct($cart_id, $user_id,$product_name, $size_id, $quantity){
            $this->cart_id = $cart_id;
            $this->user_id = $user_id;
            $this->product_name = $product_name;
            $this->size_id = $size_id;
            $this->quantity = $quantity;
        }
    }
    while($row = mysqli_fetch_assoc($cart)){
        array_push($arr_cart_product, new CartProduct($row['cart_id'], $row['user_id'], $row['product_name'], $row['size_id'], $row['quantity']));
    }
    echo json_encode($arr_cart_product);
?>