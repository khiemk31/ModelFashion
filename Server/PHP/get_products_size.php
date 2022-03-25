<?php
    require "dbconnect.php";
    $product_name = $_POST['product_name'];
    $get_products_size = "SELECT * FROM Product_Size_FashionShop WHERE product_name = '$product_name'";
    $data = mysqli_query($connect,$get_products_size);
    $arr_size = array();
    class ProductSize{
        function ProductSize($id,$product_name,$size,$quantity){
            $this->id = $id;
            $this->product_name = $product_name;
            $this->size = $size;
            $this->quantity = $quantity;
        }
    }
    while($row = mysqli_fetch_assoc($data)){
        array_push($arr_size, new ProductSize($row['product_size_id'],$row['product_name'],$row['size'],$row['quantity']));
    }
    echo json_encode($arr_size);
?>