import React, {useEffect, useState} from 'react';
import Box from '@mui/material/Box';
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import {Button, ButtonGroup, Container,  Typography} from "@mui/material";
import LoginIcon from '@mui/icons-material/Login';
import AssignmentIcon from "@mui/icons-material/Assignment";
import LogoutIcon from '@mui/icons-material/Logout';
import {useNavigate} from "react-router-dom";
import DashboardIcon from '@mui/icons-material/Dashboard';


function HomePage()
{
    const navigate = useNavigate();

    const [isAuthenticated, setIsAuthenticated] = React.useState(false);
    useEffect(() => {
        const token = localStorage.getItem('authToken');
        if (token) {
            setIsAuthenticated(true);
        }
    });

    function delteToken() {
        localStorage.removeItem('authToken');
        setIsAuthenticated(false);
    }

    return (
<div className="home-container">
    <Container>
        <Box sx={{
            width: '100%',
            bgcolor: 'background.paper',
            display: 'flex',
            justifyContent: 'flex-end',
            alignItems: 'center',
            mt: 2,
        }}>
            <ButtonGroup variant="contained"
                         sx={{
                                 border: '1px solid',
                                 borderColor: 'divider'
                            }}>
            {isAuthenticated &&
            <div>
                <Button color="primary" onClick={() => navigate("/dashboard")}>
                    <DashboardIcon/>
                    <Typography >
                        Dashboard
                    </Typography>
                </Button>

                <Button color="primary" onClick={() => delteToken()}>
                    <LoginIcon/>
                    <Typography >
                        Logout
                    </Typography>
                </Button>
            </div>
            }

            {!isAuthenticated &&
            <div>
                <Button color="primary" onClick={() => navigate("/signin")}>
                    <LoginIcon/>
                    <Typography >
                    Sign In
                    </Typography>
                </Button>

                <Button color="primary" onClick={() => navigate("/signup")}>
                    <AssignmentIcon />
                    <Typography >
                        Register
                    </Typography>
                </Button>
            </div>
            }
            </ButtonGroup>
        </Box>

        <Box sx={{ width: '100%', bgcolor: 'background.paper' }}>

            <Card elevation={3} sx={{ mt: 2 }}>
                <Box
                    sx={{
                        height: 470,
                        backgroundImage: `url('https://images.nightcafe.studio/jobs/GvuoWquMBm3xh7q84M3f/GvuoWquMBm3xh7q84M3f--4--k2jld.jpg?tr=w-1600,c-at_max')`, // replace with your image path
                        backgroundSize: 'cover',
                        backgroundPosition: 'center',
                    }}>
                </Box>
                <CardContent sx={{ backgroundColor: 'transparent' }}>
                    <Typography variant={"h2"}>
                        Welcome to StamPete
                    </Typography>
                </CardContent>
            </Card>

            <Card elevation={3} sx={{ mt: 2 }}>
                <Box
                    sx={{
                        height: 200,
                        backgroundImage: `url('')`, // replace with your image path
                        backgroundSize: 'cover',
                        backgroundPosition: 'center',
                    }}>
                </Box>
            </Card>
        </Box>
    </Container>



</div>
    );
}

export default HomePage;