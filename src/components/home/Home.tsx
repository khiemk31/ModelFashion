import { FC } from "react";
import Header from "../Header";
import Menu from "../Menu";

type Props = {};
const Home: FC<Props> = () => {
  return (
    <div>
      <Header />
      <div className="flex">
        <div className="w-[12%]">
          <Menu />
        </div>
        <div className="w-[88%]">
          <p>Home</p>
        </div>
      </div>
    </div>
  );
};
export default Home;
