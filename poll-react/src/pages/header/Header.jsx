import { AppBar, Box, Button, IconButton, Toolbar, Typography } from "@mui/material";
import MenuIcon from '@mui/icons-material/Menu';
import { Link } from "react-router-dom";

const Header = () => {
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

                    <Button component={Link} to="/login" color="inherit">Login</Button>
                    <Button component={Link} to="/register" color="inherit">Register</Button>
                </Toolbar>
            </AppBar>

            {/* THIS fixes your layout */}
            <Box sx={{ height: 64 }} />
        </>
    );
};

export default Header;
