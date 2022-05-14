import { FC, useEffect, useState } from 'react';
import Header from "../Header";
import Menu from "../Menu";
import ProductsList from "./ProductsList";
import axios from 'axios';

type ProductsProps = {};

const Product: FC<ProductsProps> = () => {

  const [products, setProducts] = useState<[]>([]);

  useEffect(() => {
    axios
      .get(`https://model-fashion.herokuapp.com/product/getAll`)
      .then((res) => {
        setProducts(res.data.data);
      })
      .catch((error) => console.log(error, "error"));
  }, []);

  console.log(products, "products");

  return (
    <div>
      <Header />
      <div className="flex">
        <div className="w-[12%]">
          <Menu />
        </div>
        <div className="w-[88%]">
          <ProductsList  products={products}/>
        </div>
      </div>
    </div>
  );
};

export default Product;
