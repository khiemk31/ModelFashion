import { FC } from "react";
import { Link } from "react-router-dom";
import { ProductData } from "../../ProductData";

type ProductProps = {
  products: ProductData[];
};

const ProductsList: FC<ProductProps> = ({ products }) => {
  return (
    <>
      <div className="px-6">
        <h1 className="text-slate-600 text-[30px] leading-[40px] font-semibold mt-10 mb-10">
          Danh sách sản phẩm
        </h1>

        <div className="flex justify-end">
          <Link to="/product/add">
            <button className="bg-purple-600 text-white rounded-md drop-shadow-md p-2 mb-6">
              Thêm sản phẩm
            </button>
          </Link>
        </div>

        <div className="table w-full p-2">
          <table className="w-full border">
            {/*title*/}
            <thead>
              <tr className="border-b bg-gray-50">
                <th className="border-r p-2 text-sm font-semibold text-gray-500">
                  <div className="flex items-center justify-center">ID</div>
                </th>
                <th className="border-r p-2 text-sm font-semibold text-gray-500">
                  <div className="flex items-center justify-center">Ảnh</div>
                </th>
                <th className="border-r p-2 text-sm font-semibold text-gray-500">
                  <div className="flex items-center justify-center">
                    Tên sản phẩm
                  </div>
                </th>
                <th className="border-r p-2 text-sm font-semibold text-gray-500">
                  <div className="flex items-center justify-center">
                    Giá bán
                  </div>
                </th>
                <th className="border-r p-2 text-sm font-semibold text-gray-500">
                  <div className="flex items-center justify-center">Loại</div>
                </th>
                <th className="border-r p-2 text-sm font-semibold text-gray-500">
                  <div className="flex items-center justify-center">Size</div>
                </th>
                <th className="border-r p-2 text-sm font-semibold text-gray-500">
                  <div className="flex items-center justify-center">
                    Số lượng
                  </div>
                </th>
                <th className="border-r p-2 text-sm font-semibold text-gray-500">
                  <div className="flex items-center justify-center">
                    Hành động
                  </div>
                </th>
              </tr>
            </thead>
            {/*data*/}
            <tbody>
              {products.map(
                ({
                  product_id,
                  product_image,
                  product_name,
                  price,
                  category_name,
                  size,
                  quantity,
                }) => (
                  <tr className="border-b bg-gray-100 text-center text-sm text-gray-600">
                    <td className="border-r p-2">{product_id}</td>
                    <td className="border-r p-2">
                      <img src={product_image} alt="" />
                    </td>
                    <td className="border-r p-2">{product_name}</td>
                    <td className="border-r p-2">{price}</td>
                    <td className="border-r p-2">{category_name}</td>
                    <td className="border-r p-2">{size}</td>
                    <td className="border-r p-2">{quantity}</td>
                    <td>
                      <div>
                        <a
                          href=""
                          className="bg-purple-500 p-2 text-xs font-thin text-white hover:shadow-lg"
                        >
                          View
                        </a>
                      </div>
                    </td>
                  </tr>
                )
              )}
            </tbody>
          </table>
        </div>
      </div>
    </>
  );
};

export default ProductsList;
