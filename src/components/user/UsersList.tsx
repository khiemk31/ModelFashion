import { FC } from "react";
import { UserData } from "../../UserData";

type UsersListProps = {
  users: UserData[];
};

const UsersList: FC<UsersListProps> = ({ users }) => {
  return (
    <>
      <div className="px-6">
        <h1 className="text-slate-600 text-[30px] leading-[40px] font-semibold mt-10 mb-10">
          Danh sách người dùng
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
                  <div className="flex items-center justify-center">Avatar</div>
                </th>
                <th className="border-r p-2 text-base font-semibold   text-gray-500">
                  <div className="flex items-center justify-center">
                    Họ và tên
                  </div>
                </th>
                <th className="border-r p-2 text-base font-semibold text-gray-500">
                  <div className="flex items-center justify-center">
                    Password
                  </div>
                </th>
                <th className="border-r p-2 text-base font-semibold text-gray-500">
                  <div className="flex items-center justify-center">
                    Giới tính
                  </div>
                </th>
                <th className="border-r p-2 text-base font-semibold text-gray-500">
                  <div className="flex items-center justify-center">
                    Ngày sinh
                  </div>
                </th>
                <th className="border-r p-2 text-base font-semibold text-gray-500">
                  <div className="flex items-center justify-center">
                    Địa chỉ
                  </div>
                </th>
                <th className="border-r p-2 text-base font-semibold text-gray-500">
                  <div className="flex items-center justify-center">
                    Điện thoại
                  </div>
                </th>
                <th className="border-r p-2 text-base font-semibold text-gray-500">
                  <div className="flex items-center justify-center">
                    Loại tài khoản
                  </div>
                </th>
                <th className="border-r p-2 text-base font-semibold text-gray-500">
                  <div className="flex items-center justify-center">
                    Trạng thái
                  </div>
                </th>
                <th className="border-r p-2 text-base font-semibold text-gray-500">
                  <div className="flex items-center justify-center">
                    Hành động
                  </div>
                </th>
              </tr>
            </thead>
            {/*data*/}
            <tbody>
              {users.map(
                ({
                  user_id,
                  avatar,
                  user_name,
                  password,
                  gender,
                  date_of_birth,
                  address,
                  phone,
                  role,
                  active,
                }) => (
                  <tr className="border-b bg-gray-100 text-center text-sm text-gray-600">
                    <td className="border-r p-2">{user_id}</td>
                    <td className="border-r p-2">
                      <img src={avatar} alt="" />
                    </td>
                    <td className="border-r p-2">{user_name}</td>
                    <td className="border-r p-2">{password}</td>
                    <td className="border-r p-2">{gender ? "nu" : "nam"}</td>
                    <td className="border-r p-2">{date_of_birth}</td>
                    <td className="border-r p-2">{address}</td>
                    <td className="border-r p-2">{phone}</td>
                    <td className="border-r p-2">{role}</td>
                    <td className="border-r p-2">
                      {active ? "block" : "active"}
                    </td>
                    <td>
                      <div className="flex">
                        <a
                          href=""
                          className="bg-purple-500 p-2 text-xs font-thin text-white hover:shadow-lg"
                        >
                          View
                        </a>
                        <button className="bg-blue-500 p-2 text-xs font-thin text-white hover:shadow-lg">
                          Edit
                        </button>
                        <button className="bg-red-500 p-2 text-xs font-thin text-white hover:shadow-lg">
                          {active ? "unblock" : "block"}
                        </button>
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

export default UsersList;
