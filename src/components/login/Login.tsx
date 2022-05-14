import { FC, useState } from "react";
import { Link } from "react-router-dom";

type LoginProps = {};

const Login: FC<LoginProps> = () => {
  const [userName, setUserName] = useState("");
  const [password, setPassword] = useState("");

  if (userName === "admin" && password === "admin") {
    return <Link to="/" />;
  }

  return (
    <>
      <div className="bg-blue-600 drop-shadow-lg flex justify-between items-center px-2 py-6">
        <p className="text-white text-xl">Model Fashion</p>
      </div>
      <div className="flex justify-center mt-20">
        <form className="">
          <p className="text-center text-xl mb-10">Model Fashion</p>
          <div>
            <input
              className="border border-slate-800 rounded-md px-4 py-2 mb-4"
              type="text"
              placeholder="Email"
              onChange={(event) => {
                setUserName(event.target.value);
              }}
            />
          </div>
          <div>
            <input
              className="border border-slate-800 rounded-md px-4 py-2 mb-4"
              type="password"
              placeholder="Password"
              onChange={(event) => {
                setPassword(event.target.value);
              }}
            />
          </div>
          <button className="bg-sky-500 text-white rounded-md drop-shadow-md transition-all duration-500 hover:-translate-y-2 hover:scale-105 w-full p-2">
            Sign in
          </button>
        </form>
      </div>
    </>
  );
};

export default Login;
