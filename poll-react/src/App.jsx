import { Route, Routes, Navigate } from "react-router-dom";
import Header from "./pages/header/Header";
import Signup from "./pages/auth/signup/Signup";
import Login from "./pages/auth/login/Login";
import Dashboard from "./pages/user/dashboard/Dashboard";
import CreatePoll from "./pages/user/create-poll/CreatePoll";
import ViewMyPolls from "./pages/user/view-my-polls/ViewMyPolls";
import ViewPollDetails from "./pages/user/view-poll-details/ViewPollDetails";










function App() {
  return (
    <>
      <Header />

      <Routes>
        <Route path="/" element={<Navigate to="/login" />} />
        <Route path="/register" element={<Signup />} />
        <Route path="/login" element={<Login />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/poll/create" element={<CreatePoll />} />
        <Route path="/my-polls" element={<ViewMyPolls />} />
        <Route path="/poll/:id/:view" element={<ViewPollDetails />} />
        

        
        

      </Routes>
    </>
  );
}

export default App;
