import React, {useEffect, useState} from 'react';
import Box from '@mui/material/Box';
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import {Button, ButtonGroup, Container,  Typography} from "@mui/material";
import LoginIcon from '@mui/icons-material/Login';
import AssignmentIcon from "@mui/icons-material/Assignment";
import {useNavigate} from "react-router-dom";
import DashboardIcon from '@mui/icons-material/Dashboard';
import IntroductionCard from "./feature/news/IntroductionCard";

function HomePage()
{
    const navigate = useNavigate();

    const features = [
        {
            id: 1,
            title: "Collect & Earn",
            description: "Unlock endless possibilities! Collect stamp cards from companies and friends to earn exclusive rewards!",
            category: "User",
            url: "https://images.nightcafe.studio/jobs/fJuhrZ8z80u4L7jFFTQ1/fJuhrZ8z80u4L7jFFTQ1--1--gbizv.jpg?tr=w-1600,c-at_max"
        },
        {
            id: 2,
            title: "Create & Share",
            description: "Spread the joy by creating your unique stamp cards and sharing them with friends. Motivate and be motivated!",
            category: "Rewards",
            url: "https://images.nightcafe.studio/jobs/Nfypghj3m4ie5ZFitkHN/Nfypghj3m4ie5ZFitkHN--1--fxlo3.jpg?tr=w-1600,c-at_max"
        },
        {
            id: 3,
            title: "Reward & Connect",
            description: "Connect with friends, share stamps, and celebrate every reward. Make every interaction rewarding!",
            category: "Social",
            url: "https://images.nightcafe.studio/jobs/tRSREJlm1oX2klXRWfMc/tRSREJlm1oX2klXRWfMc--1--uahmv.jpg?tr=w-1600,c-at_max"
        },
        {
            id: 4,
            title: "Personal Motivator",
            description: "Set your goals and mark your achievements with customizable stamp cards. Make every step count!",
            category: "Personal Development",
            url: "https://images.nightcafe.studio/jobs/yZu3VYKdRYTPcv5ffuh4/yZu3VYKdRYTPcv5ffuh4--1--xb0zp.jpg?tr=w-1600,c-at_max"
        },
        {
            id: 5,
            title: "Joy of Giving",
            description: "Send a token of love! Gift stamp cards to your friends and let them unwrap the joy of earning rewards!",
            category: "Personal Development",
            url: "https://images.nightcafe.studio/jobs/WQ9LJwGtdgmFjNrAIvon/WQ9LJwGtdgmFjNrAIvon--1--wn5vy.jpg?tr=w-1600,c-at_max"
        },
        {
            id: 6,
            title: "Discover & Save",
            description: "Discover new stamp cards daily from your favorite brands and save big on exciting rewards!",
            category: "Personal Development",
            url: "https://images.nightcafe.studio/jobs/wzDBg3M1YEEEn9TFWkeT/wzDBg3M1YEEEn9TFWkeT--1--kzq0q.jpg?tr=w-1600,c-at_max"
        },
        {
            id: 7,
            title: "Interactive Collecting",
            description: "Experience the thrill of collecting! Gather stamps interactively and see what rewards await!",
            category: "Personal Development",
            url: "https://images.nightcafe.studio/jobs/X78UrtOc1DgWX15CDU4S/X78UrtOc1DgWX15CDU4S--1--lcy5i.jpg?tr=w-1600,c-at_maxx"
        },
        {
            id: 8,
            title: "Customized Experience",
            description: "Personalize your journey! Create and collect stamp cards that reflect your taste and preferences!",
            category: "Personal Development",
            url: "https://images.nightcafe.studio/jobs/FK1nqcMODdS2NTv0hmWx/FK1nqcMODdS2NTv0hmWx--1--mnbdg.jpg?tr=w-1600,c-at_max"
        }
    ];

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
            {features.map((feature) => (
                <IntroductionCard feature={feature}></IntroductionCard>
            ))}
        </Box>


    </Container>
</div>
    );
}

export default HomePage;