<?php
        require "dbconnect.php";
        $taikhoan = $_POST['taikhoan'];
        $matkhau = $_POST['matkhau'];
        $user = 1;
        class User{
            function User($id,$user,$password,$email,$full_name,$phone,$sex,$birthdate,$address,$fund,$avatar){
                $this->id = $id;
                $this->taiKhoan = $user;
                $this->matKhau = $password;
                $this->email = $email;
                $this->fullName = $full_name;
                $this->phone = $phone;
                $this->sex = $sex;
                $this->birthdate = $birthdate;
                $this->address = $address;
                $this->fund = $fund;
                $this->avatar = $avatar;
            }
        }
        if(strlen($taikhoan)>0 && strlen($matkhau)>0){
            $query = "SELECT * FROM User_FashionShop WHERE user_name = '$taikhoan' && password = '$matkhau'";
            $data = mysqli_query($connect,$query);
            if($data){
                while($row = mysqli_fetch_assoc($data)){
                    $user = new User($row['user_id'], $row['user_name'], $row['password'],
                    $row['email'],$row['full_name'],$row['phone'],$row['sex'],$row['birthdate']
                    ,$row['address'],$row['fund'], $row['avatar']);
                }
                if(count($user)>0){
                    echo json_encode($user);
                }else{
                    echo "Failed";
                }
            }
        }else{
            echo "Null";
        }
    ?>