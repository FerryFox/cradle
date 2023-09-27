import React, {useEffect, useState} from 'react';
import Box from '@mui/material/Box';
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import {Button, ButtonGroup, Container,  Typography} from "@mui/material";
import LoginIcon from '@mui/icons-material/Login';
import AssignmentIcon from "@mui/icons-material/Assignment";
import {useNavigate} from "react-router-dom";
import DashboardIcon from '@mui/icons-material/Dashboard';
import IntroductionCard from "../news/IntroductionCard";
import axios from "axios";

function HomePage()
{
    const navigate = useNavigate();
    const [features, setFeatures] = useState([]);

    useEffect(() =>
    {
        axios.get('/api/news/all')
            .then((response) => {
                setFeatures(response.data);
            }).catch((error) => {
                console.log(error);
            });
    }, []);


    const [isAuthenticated, setIsAuthenticated] = React.useState(false);
    useEffect(() => {
        const token = localStorage.getItem('authToken');
        if (token) {
            setIsAuthenticated(true);
        }
    });

    function deleteToken() {
        localStorage.removeItem('authToken');
        setIsAuthenticated(false);
    }

return (
<div className="home-container">
    <Container>

            <Card elevation={3} sx={{ mt : 1}}>
                <Box
                    sx={{
                        width: '100%',
                        height: '40vh',
                        backgroundImage: `url('https://images.nightcafe.studio/jobs/GvuoWquMBm3xh7q84M3f/GvuoWquMBm3xh7q84M3f--4--k2jld.jpg?tr=w-1600,c-at_max')`, // replace with your image path
                        backgroundSize: 'cover',
                        backgroundPosition: 'center',
                    }}>
                </Box>
                <CardContent sx={{ backgroundColor: 'transparent' }}>
                    <Typography color={"cadetblue"} variant={"h2"} sx={{ fontSize: ['2em', '3em', '4em'] }}>
                        Welcome to StamPete
                    </Typography>
                </CardContent>
            </Card>

        <Box sx={{
            width: '100%',
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            height: '25vh',
        }}>
            <ButtonGroup
                variant="contained"
                sx={{
                    borderRadius: '1px', // Rounded borders for the ButtonGroup
                }}
            >
                {isAuthenticated ? (
                    <>
                        <Button
                            size={"large"}
                            color="primary"
                            onClick={() => navigate("/dashboard")}
                            sx={{ textTransform: 'none', fontSize: '16px' }} // Override text transformation and font size
                        >
                            <DashboardIcon sx={{ marginRight: '8px' }} /> {/* Adding some spacing between icon and text */}
                            Dashboard
                        </Button>
                        <Button
                            size={"large"}
                            color="primary"
                            onClick={() => deleteToken()}
                            sx={{ textTransform: 'none', fontSize: '16px' }}
                        >
                            <LoginIcon sx={{ marginRight: '8px' }} />
                            Logout
                        </Button>
                    </>
                ) : (
                    <>
                        <Button
                            size={"large"}
                            color="primary"
                            onClick={() => navigate("/signin")}
                            sx={{ textTransform: 'none', fontSize: '16px' }}
                        >
                            <LoginIcon sx={{ marginRight: '8px' }} />
                            Sign In
                        </Button>
                        <Button
                            size={"large"}
                            color="primary"
                            onClick={() => navigate("/signup")}
                            sx={{ textTransform: 'none', fontSize: '16px' }}
                        >
                            <AssignmentIcon sx={{ marginRight: '8px' }} />
                            Register
                        </Button>
                    </>
                )}
            </ButtonGroup>
        </Box>

        <Box>
            {features.map((feature, index) => (
                <IntroductionCard key={feature.id} order={index + 1} feature={feature}></IntroductionCard>
            ))}
        </Box>


    </Container>
</div>
    );
}

export default HomePage;