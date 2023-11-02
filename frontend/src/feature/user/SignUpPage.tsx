import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import {IconButton} from "@mui/material";
import ArrowBackIosIcon from "@mui/icons-material/ArrowBackIos";
import LockOpenIcon from '@mui/icons-material/LockOpen';
import Copyright from "../core/Copyright";
import {FormEvent, useState} from "react";

type UserDTO = {
    firstname: string,
    email: string,
    password: string,
    receiveNews: boolean
}

type FormErrors = {
    firstname?: string;
    email?: string;
    password?: string;
}

type AuthResponse = {
    access_token: string;
    // ... any other expected properties ...
};

const saveUser = async (userDTO : UserDTO) :Promise<AuthResponse | null> =>
{
    try
    {
        const response = await axios.post<AuthResponse>('/api/auth/register', userDTO);
        if(response.status === 200 || response.status === 201)
        {
            return response.data;
        }
    }catch (error)
    {
        console.error('There was an error saving the user:', error);
    }
    return null;
};

export default function SignUp()
{
    const navigate = useNavigate();

    const [formData, setFormData] = useState<UserDTO>({
        firstname: '',
        email: '',
        password: '',
        receiveNews: false,
    });

    const [isShaking, setIsShaking] = useState<boolean>(false);

    const [formErrors, setFormErrors] = useState<FormErrors>({});

    const handleShake = () => {
        setIsShaking(true);
        setTimeout(() => {
            setIsShaking(false);
        }, 820);  // match the duration of the shake animation
    };

    const validateFormData = () => {
        let errors :FormErrors = {};

        if (!formData.firstname || formData.firstname.length > 31) {
            errors.firstname = 'Name is required and should not be longer then 30 characters';
        }

        const emailPattern : RegExp = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i;
        if (!formData.email || !emailPattern.test(formData.email)) {
            errors.email = 'Valid email is required';
        }

        if (!formData.password || formData.password.length < 6) {
            errors.password = 'Password must be at least 6 characters';
        }


        setFormErrors(errors);  // Update the state once here

        return errors;
    };

    const handleSubmit = async (event : FormEvent<HTMLFormElement>) : Promise<void> =>
    {
        event.preventDefault();

        // Validate form data
        const errors = validateFormData();
        setFormErrors(errors);

        if (Object.keys(errors).length > 0) {
            handleShake();
            return;
        }

        try
        {
            const response   = await saveUser(formData);
            if (response){
                const token = response.access_token;
                if (token) {
                    // Store the token in LocalStorage
                    localStorage.setItem('authToken', token);
                    // Set default axios authorization header
                    axios.defaults.headers.common['Authorization'] = 'Bearer ' + token;
                    navigate('/dashboard');
                }
                else { console.error('No token received'); }
            }
        }
        catch (error)
        {
            console.error("Error during registration:", error);
        }
    };

return (
<Container className={isShaking ? 'shake' : ''}
           component="main"
           maxWidth="xs">

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
                    <TextField required
                               fullWidth
                               id="userName"
                               label="Choose your Name"
                               name="userName"
                               autoComplete="family-name"
                               value={formData.firstname}
                               onChange={e =>
                                   setFormData(prev => ({ ...prev, firstname: e.target.value }))}
                               error={!!formErrors.firstname}
                               helperText={formErrors.firstname}/>
                </Grid>

                <Grid item xs={12}>
                    <TextField required
                               fullWidth
                               id="email"
                               label="Email Address"
                               name="email"
                               autoComplete="email"
                               value={formData.email}
                               onChange={e =>
                                   setFormData(prev => ({ ...prev, email: e.target.value }))}
                               error={!!formErrors.email}
                               helperText={formErrors.email}/>
                </Grid>

                <Grid item xs={12}>
                    <TextField required
                               fullWidth
                               name="password"
                               label="Password"
                               type="password"
                               id="password"
                               autoComplete="new-password"
                               value={formData.password}
                               onChange={e =>
                                   setFormData(prev => ({ ...prev, password: e.target.value }))}
                               error={!!formErrors.password}
                               helperText={formErrors.password}/>
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
    <Copyright/>
</Container>
);
}