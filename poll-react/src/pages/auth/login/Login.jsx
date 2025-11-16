import { Avatar, Backdrop, Box, Button, CircularProgress, Container, CssBaseline, Link, TextField, Typography, createTheme, ThemeProvider, Grid } from "@mui/material";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { LockOutline, Password, TextFields } from "@mui/icons-material";

const defaultTheme = createTheme();

const Login = () => {
    const [formData, setFormData] = useState({
        email: '',
        Password: ''
    });

    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        console.log(formData);
        setLoading(false);
    };

    return (
        <>
            <ThemeProvider theme={defaultTheme}>
                <Container component="main" maxWidth="xs">
                    <CssBaseline />

                    <Box
                        sx={{
                            marginTop: 8,
                            display: 'flex',
                            flexDirection: 'column',
                            alignItems: 'center',
                        }}
                    >
                        <Avatar sx={{ m: 1, bgcolor: 'primary.main' }}>
                            <LockOutlinedIcon />
                        </Avatar>

                        <Typography component="h1" variant="h5">
                            Sign in
                        </Typography>

                        <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
                            <TextField
                                margin="normal"
                                required
                                fullWidth
                                id="email"
                                label="Email Address"
                                name="email"
                                autoComplete="email"
                                autoFocus
                                value={formData.email}
                                onChange={handleInputChange}
                            />

                            <TextField
                                margin="normal"
                                required
                                fullWidth
                                id="password"
                                label="Password"
                                name="Password"
                                type="password"
                                autoComplete="current-password"
                                value={formData.Password}
                                onChange={handleInputChange}
                            />

                            <Button
                                type="submit"
                                fullWidth
                                variant="contained"
                                sx={{ mt: 3, mb: 2 }}
                                disabled={!formData.email || !formData.Password}
                            >
                                {loading ? <CircularProgress size={24} /> : "Sign In"}
                            </Button>

                            <Grid container>
                                <Grid item>
                                    <Link
                                        variant="body2"
                                        onClick={() => navigate("/register")}
                                        sx={{ cursor: "pointer" }}
                                    >
                                        {"Don't have an account? Sign Up"}
                                    </Link>
                                </Grid>
                            </Grid>

                        </Box>
                    </Box>

                </Container>
            </ThemeProvider>

            <Backdrop
                sx={{ color: "#fff", zIndex: (theme) => theme.zIndex.drawer + 1 }}
                open={loading}
            >
                <CircularProgress />
            </Backdrop>
        </>
    );
};

export default Login;
