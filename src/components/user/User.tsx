import { FC, useEffect, useState } from "react";
import Header from "../Header";
import Menu from "../Menu";
import axios from "axios";
import UsersList from "./UsersList";

type UserProps = {};

const User: FC<UserProps> = () => {
  const [users, setUsers] = useState<[]>([]);

  useEffect(() => {
    axios
      .get(`https://model-fashion.herokuapp.com/user/getAllUser`)
      .then((res) => {
        setUsers(res.data);
      })
      .catch((error) => console.log(error, "error"));
  }, []);

  console.log(users, "users");

  return (
    <div>
      <Header />
      <div className="flex">
        <div className="w-[12%]">
          <Menu />
        </div>
        <div className="w-[88%]">{<UsersList users={users} />}</div>
      </div>
    </div>
  );
};

export default User;
