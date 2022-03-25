<?php
    require "dbconnect.php";
    $sizes_id = json_decode($_GET['sizes_id']);
    $condition = '"'.implode('","',$sizes_id).'"';
    $get_sizes = "SELECT * FROM Product_Size_FashionShop INNER JOIN Cart_FashionShop ON
    Product_Size_FashionShop.product_size_id = Cart_FashionShop.size_id WHERE Cart_FashionShop.size_id IN ($condition)";
    $data = mysqli_query($connect,$get_sizes);
    $result_sizes = array();
    class ProductSize{
        function ProductSize($id,$product_name,$size,$quantity){
            $this->id = $id;
            $this->product_name = $product_name;
            $this->size = $size;
            $this->quantity = $quantity;
        }
    }
    while($row = mysqli_fetch_assoc($data)){
        array_push($result_sizes, new ProductSize($row['product_size_id'],$row['product_name'],$row['size'],$row['quantity']));
    }
    echo json_encode($result_sizes);
?>