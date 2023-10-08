import React, {useEffect, useState} from 'react';
import {Container, Stack} from "@mui/material";
import {NavigateFunction, useNavigate} from "react-router-dom";

import Title from "./Title";
import ButtonHub from "./ButtonHub";
import NewsBox from "./NewsBox";
import {News} from "./model/models";
import {loadNews} from "./service/newsService";
import About from "./About";

export default function HomePage()
{
    const navigateTo : NavigateFunction = useNavigate();

    const [news, setNews] = useState<News[]>([]);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        loadNews(setNews, setError);
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
        <Stack spacing={2}>
            <Title/>

            <ButtonHub
                isAuthenticated={isAuthenticated}
                deleteToken={deleteToken}
                navigate={navigateTo}/>

            <NewsBox
                news={news}/>
        </Stack>
    </Container>

    <About/>
</div>
    );
}