import React, {useEffect, useState} from 'react';
import {Container, Stack} from "@mui/material";
import {NavigateFunction, useNavigate} from "react-router-dom";

import Title from "./Title";
import ButtonHub from "./ButtonHub";
import NewsBox from "./NewsBox";
import {News} from "./model/models";
import {loadNews} from "./service/newsService";
import About from "./About";
import axios from "axios";
import {Skeleton} from "@mui/lab";
import IntroductionCard from "./IntroductionCard";

export default function HomePage()
{
    const navigateTo : NavigateFunction = useNavigate();

    const [news, setNews] = useState<News[]>([]);
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        loadNews(setNews, setError, setLoading);
    }, []);

    const [isAuthenticated, setIsAuthenticated] = React.useState(false);

    useEffect(() => {
        const checkTokenValidity = async () => {
            const token = localStorage.getItem('authToken');
            if (token) {
                try {
                    await axios.get('/api/auth/check-token', {
                        headers: {
                            'Authorization': `Bearer ${token}`
                        }
                    });

                axios.defaults.headers.common['Authorization'] = 'Bearer ' + token;
                setIsAuthenticated(true)
                }
                catch {
                    setIsAuthenticated(false)
                    localStorage.removeItem('authToken');
                }
            }
        }
        checkTokenValidity().then();
    }, []);

    function deleteToken() {
        localStorage.removeItem('authToken');
        setIsAuthenticated(false);
    }

    if(error) {
        return <div>Failed to load news: {error}</div>;
    }

return (
<div className="home-container">
    <Container>
        <Stack spacing={2}>
           <Title/>

            <ButtonHub
                isAuthenticated={isAuthenticated}
                deleteToken={deleteToken}
                navigate={navigateTo}/>

            {loading ?
                (
                    Array(8).fill(null).map((_, index) => (
                        <Skeleton
                            key={index}
                            animation="wave"
                            variant="rectangular"
                            width="100%"
                            height="22vh"
                        />
                    ))
                )
                    :
                (<NewsBox news={news}/>)
            }

        </Stack>
    </Container>

    <About/>
</div>
    );
}