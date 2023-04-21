import RestaurantInfo from "./component/RestaurantInfo";
import RestaurantMain from "./component/RestaurantMain";
import { Route,Router,Routes } from "react-router-dom";

function App() {
  return (
    <>
      <Router>
	      <Routes>
		        <Route path="/" element ={<RestaurantMain/>}/>
	      </Routes>
      </Router>
    </>

  );
}

export default App;
