import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import {useNavigate} from "react-router-dom";
import { useState } from 'react';
import axios from "axios";
import ArrowBackIosIcon from '@mui/icons-material/ArrowBackIos';
import {IconButton} from "@mui/material";
import LockOpenIcon from '@mui/icons-material/LockOpen';

function Copyright(props) {
    return (
        <Typography variant="body2" color="text.secondary" align="center" {...props}>
            {'Copyright © '}
            <Link color="inherit" href="https://mui.com/">
                StamPete
            </Link>{' '}
            {new Date().getFullYear()}
            {'.'}
        </Typography>
    );
}

function saveUsername(username) {
    localStorage.setItem('email', username);
}

function getUsername() {
    return localStorage.getItem('email');
}

export default function SignIn()
{
    const [email, setEmail] = useState(getUsername() || '');
    const navigate = useNavigate();
    const [isShaking, setIsShaking] = useState(false);

    const handleShake = () => {
        setIsShaking(true);
        setTimeout(() => {
            setIsShaking(false);
        }, 820);  // match the duration of the shake animation
    };

    const handleSubmit = async (event) =>
    {
        event.preventDefault();
        try
        {
            const data = new FormData(event.currentTarget);

            const response = await axios.post('/api/auth/authenticate', {
                email: data.get('email'),
                password: data.get('password'),
            });

            const token = response.data.access_token;
            if (token)
            {
                // Store the token in LocalStorage
                localStorage.setItem('authToken', token);
                saveUsername(data.get('email'));
                // Set default axios authorization header
                axios.defaults.headers.common['Authorization'] = 'Bearer ' + token;
                navigate('/dashboard');
            }
            else
            {
                    handleShake();
                    console.error('No token received');
            }
        }
        catch (error)
        {
            handleShake();
            console.error('Error logging in:', error.response ? error.response.data : error.message);
        }
    };

return (
<Container className={isShaking ? 'shake' : ''} component="main" maxWidth="xs">
    <Box sx={{ marginTop: 8, display: 'flex', flexDirection: 'column', alignItems: 'center', width: '100%' }}>
        {/* Use Grid for layout */}
        <Grid container spacing={1}>
            {/* Column for IconButton */}
            <Grid item xs={1}>
                <IconButton color="primary" onClick={() => navigate("/")}>
                    <ArrowBackIosIcon/>
                </IconButton>
            </Grid>

            {/* Center column for Avatar */}
            <Grid item xs={10} display="flex" justifyContent="center">
                <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
                    <LockOpenIcon />
                </Avatar>
            </Grid>
        </Grid>

        <Typography component="h1" variant="h5">
            Sign in
        </Typography>

        <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
            <TextField margin="normal" required fullWidth id="email" label="Email Address" name="email"
                       autoComplete="email" value={email} onChange={e => setEmail(e.target.value)} autoFocus/>

            <TextField margin="normal" required fullWidth name="password" label="Password" type="password"
                       id="password" autoComplete="current-password"/>

            <FormControlLabel control={<Checkbox value="remember" color="primary" />} label="Remember me"/>

            <Button type="submit" fullWidth variant="contained" sx={{ mt: 3, mb: 2 }}>
                Sign In
            </Button>

            <Grid container>
                <Grid item xs>
                    <Button variant="text" onClick={() => navigate('/reset-password')} size="small"
                            sx={{
                                textDecoration: 'underline',
                                fontSize: '0.5rem',
                                textTransform: 'none',
                            }}>
                        <Typography variant="body2" color="primary">
                            Forgot password?
                        </Typography>
                    </Button>
                </Grid>
                <Grid item>
                    <Button variant="text" onClick={() => navigate('/signup')} size="small"
                    sx={{
                        textDecoration: 'underline',
                        fontSize: '0.5rem',
                        textTransform: 'none',
                    }}>
                        <Typography variant="body2" color="primary">
                            Don't have an account?
                        </Typography>
                    </Button>
                </Grid>
            </Grid>
        </Box>
    </Box>
    <Copyright sx={{ mt: 8, mb: 4 }} />
</Container>
);
}