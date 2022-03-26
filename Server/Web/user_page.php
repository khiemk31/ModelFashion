<?php
    require "../dbconnect.php";
    $get_all_user = "SELECT * FROM User_FashionShop";
    $data = mysqli_query($connect, $get_all_user);
    $arr_user = array();
    $table_user = '';
    class User{
        function User($id, $taikhoan, $email, $hoten, $dienthoai, $gioitinh){
            $this->id = $id;
            $this->taikhoan = $taikhoan;
            $this->email = $email;
            $this->hoten = $hoten;
            $this->dienthoai = $dienthoai;
            $this->gioitinh = $gioitinh;
        }
    }
    while($row = mysqli_fetch_assoc($data)){
        array_push($arr_user,new User($row['user_id'],$row['user_name'],$row['email'],$row['full_name'],
        $row['phone'],$row['sex']));
    }
    for($i = 0; $i < count($arr_user); $i++){
        $table_user .= '<form action = ""><tr>
            <td>'.$arr_user[$i]->id.'</td>
            <td>'.$arr_user[$i]->taikhoan.'</td>
            <td>'.$arr_user[$i]->email.'</td>
            <td>'.$arr_user[$i]->hoten.'</td>
            <td>'.$arr_user[$i]->dienthoai.'</td>
            <td>'.$arr_user[$i]->gioitinh.'</td>
            <td>
                <button>Xem</button>
            </td>
        </tr></form>';
    }
    $html ='<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Người dùng</title>
    <link rel="stylesheet" href="main_web.css">
    <style>
        table {
            font-family: arial, sans-serif;
            border-collapse: collapse;
            width: 100%;
        }

        td, th {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }

        tr:nth-child(even) {
            background-color: #dddddd;
        }
    </style>
</head>
<body>
    <div class="topnav">
        <a href="main_page.html">Trang chủ</a>
        <a class="active" href="user_page.php">Người dùng</a>
        <a href="product_page.html">Sản phẩm</a>
        <a href="login_admin.html" id="logout">Đăng xuất</a>
    </div>
    <div>
        <table>
        <tr>
            <th>ID</th>
            <th>Tài khoản</th>
            <th>Email</th>
            <th>Họ tên</th>
            <th>Điện thoại</th>
            <th>Giới tính</th>
            <th>
                <button>Xem</button>
            </th>
        </tr>'.
        $table_user.'
    </table>
    </div>
</body>
</html>';
    echo $html;
?>