import { FC } from "react";
import { CategoryData } from "../../CategoryData";

type CategoryListProps = {
  category: CategoryData[];
};

const UsersList: FC<CategoryListProps> = ({ category }) => {
  return (
    <>
      <div className="px-6">
        <h1 className="text-slate-600 text-[30px] leading-[40px] font-semibold mt-10 mb-10">
          Danh sách thể loại
        </h1>

        <div className="table w-full">
          <table className="w-full border">
            {/*title*/}
            <thead>
              <tr className="border-b bg-gray-50">
                <th className="border-r p-2 text-base font-semibold text-gray-500">
                  <div className="flex items-center justify-center">ID</div>
                </th>
                <th className="border-r p-2 text-base font-semibold text-gray-500">
                  <div className="flex items-center justify-center">Tên</div>
                </th>
                <th className="border-r p-2 text-base font-semibold text-gray-500">
                  <div className="flex items-center justify-center">Hành động</div>
                </th>
              </tr>
            </thead>
            {/*data*/}
            <tbody>
              {category.map(({ category_id, category_name }) => (
                <tr className="border-b bg-gray-100 text-center text-sm text-gray-600">
                  <td className="border-r p-2">{category_id}</td>
                  <td className="border-r p-2">{category_name}</td>
                  <td>
                    <div className="flex justify-center">
                      <a
                        href=""
                        className="bg-purple-500 p-2 text-xs font-thin text-white hover:shadow-lg"
                      >
                        View
                      </a>
                      <button className="bg-blue-500 p-2 text-xs font-thin text-white hover:shadow-lg">
                        Edit
                      </button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </>
  );
};

export default UsersList;
