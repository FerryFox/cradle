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

export default function SignIn()
{
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
    <Box sx={{marginTop: 8, display: 'flex', flexDirection: 'column', alignItems: 'center',}}>
        <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
            <LockOutlinedIcon />
        </Avatar>

        <Typography component="h1" variant="h5">
            Sign in
        </Typography>

        <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
            <TextField margin="normal" required fullWidth id="email" label="Email Address" name="email"
                       autoComplete="email" autoFocus/>

            <TextField margin="normal" required fullWidth name="password" label="Password" type="password"
                       id="password" autoComplete="current-password"/>

            <FormControlLabel control={<Checkbox value="remember" color="primary" />} label="Remember me"/>

            <Button type="submit" fullWidth variant="contained" sx={{ mt: 3, mb: 2 }}>
                Sign In
            </Button>

            <Grid container>
                <Grid item xs>
                    <Link href="#" variant="body2">
                        Forgot password?
                    </Link>
                </Grid>
                <Grid item>
                    <Link href="/signup" variant="body2">
                        {"Don't have an account? Sign Up"}
                    </Link>
                </Grid>
            </Grid>
        </Box>
    </Box>
    <Copyright sx={{ mt: 8, mb: 4 }} />
</Container>
);
}