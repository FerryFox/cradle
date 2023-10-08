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
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import {IconButton} from "@mui/material";
import ArrowBackIosIcon from "@mui/icons-material/ArrowBackIos";
import LockOpenIcon from '@mui/icons-material/LockOpen';

const saveUser = async (userDTO) =>
{
    try
    {
        const response = await axios.post('/api/auth/register', userDTO);
        if(response.status === 200 || response.status === 201)
        {
            return response;
        }
    }catch (error)
    {
        console.error('There was an error saving the user:', error);
    }
    return null;
};

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

export default function SignUp()
{
    const navigate = useNavigate();

    const handleSubmit = async (event) =>
    {
        event.preventDefault();
        const data = new FormData(event.currentTarget);
        const userDTO =
            {
                firstname: data.get('userName'),
                email: data.get('email'),
                password: data.get('password'),
                receiveNews : data.get('allowExtraEmails') === "on"
            };

        try
        {
            const response = await saveUser(userDTO);

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
                // Handle any other validation or issues here
                console.error('No token received');
            }
        }
        catch (error)
        {
            console.error("Error during registration:", error);
        }
    };

return (
<Container component="main" maxWidth="xs">
    <Box sx={{marginTop: 8, display: 'flex', flexDirection: 'column', alignItems: 'center',}}>
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
            Sign up
        </Typography>

        <Box component="form" noValidate onSubmit={handleSubmit} sx={{ mt: 3 }}>
            <Grid container spacing={2}>
                <Grid item xs={12} sm={6}>
                    <TextField required fullWidth id="userName" label="Choose your Name"
                               name="userName" autoComplete="family-name"/>
                </Grid>

                <Grid item xs={12}>
                    <TextField required fullWidth id="email" label="Email Address"
                               name="email" autoComplete="email"/>
                </Grid>

                <Grid item xs={12}>
                    <TextField required fullWidth name="password" label="Password"
                               type="password" id="password" autoComplete="new-password"/>
                </Grid>

                <Grid item xs={12}>
                    <FormControlLabel control={<Checkbox name="allowExtraEmails" color="primary" />}
                        label="I want to receive inspiration, marketing promotions and updates via email."/>
                </Grid>
            </Grid>

            <Button type="submit" fullWidth variant="contained" sx={{ mt: 3, mb: 2 }}>
                Sign Up
            </Button>

            <Grid container justifyContent="flex-end">
                <Grid item>
                    <Button variant="text" onClick={() => navigate('/signin')} size="small"
                            sx={{
                                textDecoration: 'underline',
                                fontSize: '0.5rem',
                                textTransform: 'none',
                            }}>
                        <Typography variant="body2" color="primary">
                            Already have an account?
                        </Typography>
                    </Button>
                </Grid>
            </Grid>
        </Box>
    </Box>
    <Copyright sx={{ mt: 5 }} />
</Container>
);
}