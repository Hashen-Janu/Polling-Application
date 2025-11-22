import { AppBar, Box, Button, IconButton, Toolbar, Typography } from "@mui/material";
import MenuIcon from '@mui/icons-material/Menu';
import { Link, useLocation, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { isTokenValide, removeToken } from "../../utility/common";

const Header = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const [isUserLoggedIn, setIsuserLoggedIn] = useState(false);

    const handleSignOut = () => {
        navigate("/login");
        removeToken();
    };

    useEffect(() => {
        const isLoggedIn = isTokenValide();
        setIsuserLoggedIn(isLoggedIn);
    },[location]);

    useEffect(() => {
        const interval = setInterval(() =>{
            if(!isTokenValide()){
                setIsuserLoggedIn(false);
                handleSignOut();
            } 
        },1800000);

        return()=> clearInterval(interval);
    }, []);


    return (
        <>
            <AppBar position="fixed">
                <Toolbar>
                    <IconButton
                        size="large"
                        edge="start"
                        color="inherit"
                        aria-label="menu"
                        sx={{ mr: 2 }}
                    >
                        <MenuIcon />
                    </IconButton>

                    <Typography variant="h6" sx={{ flexGrow: 1 }}>
                        Polling App
                    </Typography>

                {isUserLoggedIn? (
                    <>
                        <Button component={Link} to="/dashboard" color="inherit">Dashboard</Button>
                        <Button component={Link} to="/poll/create" color="inherit">Post Poll</Button>
                        <Button component={Link} to="/my-polls" color="inherit">My Poll</Button>
                        <Button  color="inherit" onClick={handleSignOut}>Logout</Button>
                    </>

                ) : (
                    <>
                        <Button component={Link} to="/login" color="inherit">Login</Button>
                        <Button component={Link} to="/register" color="inherit">Register</Button>
                    
                    </>
                )}

                </Toolbar>
            </AppBar>

            {/* THIS fixes your layout */}
            <Box sx={{ height: 64 }} />
        </>
    );
};

export default Header;
