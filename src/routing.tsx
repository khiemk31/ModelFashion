import { FC } from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Login from "./components/login/Login";
import Home from "./components/home/Home";
import User from "./components/user/User";
import Product from "./components/product/Product";
import AddProduct from "./components/product/AddProduct";
import Category from './components/category/Category';

type Props = {};
const routing: FC<Props> = () => {
  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/home" element={<Home />} />
          <Route path="/user" element={<User />} />
          <Route path="/category" element={<Category />} />
          <Route path="/product" element={<Product />} />
          <Route path="/product/add" element={<AddProduct />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
};

export default routing;
