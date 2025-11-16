import { Route, Routes } from "react-router-dom"
import Header from "./pages/header/Header"
import Signup from "./pages/auth/signup/Signup"
import Login from "./pages/auth/login/Login"


function App() {

  return (
    <>
      <Header/>
      <Routes>
        <Route path="/register" element={<Signup/>}/>
        <Route path="/login" element={<Login/>}/>

      </Routes>
    </>
  )
}

export default App
