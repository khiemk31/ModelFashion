import { FC, useEffect, useState } from "react";
import Header from "../Header";
import Menu from "../Menu";
import axios from "axios";
import CategoryList from "./CategoryList";

type CategoryProps = {};

const Category: FC<CategoryProps> = () => {
  const [category, setCategory] = useState<[]>([]);

  useEffect(() => {
    axios
      .get(`https://model-fashion.herokuapp.com/category/getAll`)
      .then((res) => {
        setCategory(res.data.data);
      })
      .catch((error) => console.log(error, "error"));
  }, []);

  console.log(category, "category");

  return (
    <div>
      <div>
        <Header />
        <div className="flex">
          <div className="w-[12%]">
            <Menu />
          </div>
          <div className="w-[88%]">
            <CategoryList category={category} />
          </div>
        </div>
      </div>
    </div>
  );
};

export default Category;
