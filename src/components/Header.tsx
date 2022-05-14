import { FC } from "react";
import { Link } from 'react-router-dom';

type HeaderProps = {};
const Header: FC<HeaderProps> = () => {
  return (
    <div className="bg-[#111827] drop-shadow-lg flex justify-between items-center sticky top-0 z-20 px-2 py-6">
      <Link to="/home" className="text-white text-xl">Model Fashion</Link>
      <button className="bg-[#6366F1] text-white rounded-[4px] drop-shadow-md p-2">
        Sign out
      </button>
    </div>
  );
};

export default Header;
