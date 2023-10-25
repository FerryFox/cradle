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
import {useNavigate} from "react-router-dom";
import {FormEvent, useState} from 'react';
import axios from "axios";
import ArrowBackIosIcon from '@mui/icons-material/ArrowBackIos';
import {IconButton} from "@mui/material";
import LockOpenIcon from '@mui/icons-material/LockOpen';
import Copyright from "./Copyright";


type FromErrors = {
    email?: string;
}

export default function SignIn()
{
    const [formErrors, setFormErrors] = useState<FromErrors>({});

    const [remember, setRemember] = useState(() => {
        let storedValue = localStorage.getItem('remember');
        return storedValue === 'true';
    });

    const [email, setEmail] = useState(() => {
        let storedValue = localStorage.getItem('email');
        return storedValue ?? '';
    });

    const navigate = useNavigate();
    const [isShaking, setIsShaking] = useState<boolean>(false);

    const handleShake = () => {
        setIsShaking(true);
        setTimeout(() => {
            setIsShaking(false);
        }, 820);  // match the duration of the shake animation
    };

    const validateFormData = ()  => {
        let errors: FromErrors = {};

        const emailPattern : RegExp = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i;
        if (!email || !emailPattern.test(email)) {
            errors.email = 'Email is not valid';
        }

        setFormErrors(errors);
        return errors;
    }

    const handleSubmit = async (event : FormEvent<HTMLFormElement>) : Promise<void>  =>
    {
        event.preventDefault();

        // Validate form data
        const errors = validateFormData();
        setFormErrors(errors);

        if (Object.keys(errors).length > 0)
        {
            handleShake();
            return;
        }

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
                if (remember) localStorage.setItem('email', data.get('email') as string);
                localStorage.setItem('authToken', token);
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
        }
    };

return (
<Container className={isShaking ? 'shake' : ''}
           component="main"
           maxWidth="xs">

    <Box sx={{ marginTop: 8,
               display: 'flex',
               flexDirection: 'column',
               alignItems: 'center',
               width: '100%' }}>

        <Grid container spacing={1}>
            <Grid item xs={1}>
                <IconButton color="primary" onClick={() => navigate("/")}>
                    <ArrowBackIosIcon/>
                </IconButton>
            </Grid>

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

            <TextField margin="normal"
                       required
                       fullWidth
                       id="email"
                       label="Email Address"
                       name="email"
                       autoComplete="email"
                       autoFocus
                       value={email}
                       onChange={e => setEmail(e.target.value)}
                       error={!!formErrors.email}
                       helperText={formErrors.email}/>

            <TextField margin="normal"
                       required
                       fullWidth
                       name="password"
                       label="Password"
                       type="password"
                       id="password"
                       autoComplete="current-password"/>

            <FormControlLabel label="Remember me" control={
                <Checkbox  value="remember"
                           checked={remember}
                           color="primary"
                            onChange={e => {
                                const isChecked = e.target.checked;
                                setRemember(isChecked);
                                localStorage.setItem('remember', String(isChecked));
                                if (!isChecked)  localStorage.removeItem('email');
                            }}/>
            }/>

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
    <Copyright />
</Container>
);
}