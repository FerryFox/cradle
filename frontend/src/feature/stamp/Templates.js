import React, { useState, useEffect } from 'react';
import axios from 'axios';
import {useNavigate} from "react-router-dom";
import Template from "./Template";
import Grid from '@mui/material/Grid';
import Container from '@mui/material/Container';
import Paper from '@mui/material/Paper';


function Templates()
{
    const navigate = useNavigate();
    const [templates, setTemplates] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() =>
    {
        setLoading(true);
        setError(null);

        axios.get('/api/templates/all')
            .then(response =>
            {
                setTemplates(response.data);
                setLoading(false);
            })
            .catch(error =>
            {
                console.error("Error fetching data:", error);
                setError(error);
                setLoading(false);
            });
    }, []);

    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error loading data. Please try again later.</div>

    return (
        <Container>
            <h1>Templates Page</h1>
            <p>Total templates: {templates.length}</p>

            <Grid container spacing={4} justifyContent="center">
                {templates.map(t => (
                    <Grid item xs={12} sm={6} md={4} key={t.name}>
                        <Paper elevation={3} style={{ padding: '16px' }}>
                            <Template template={t} />
                        </Paper>
                    </Grid>
                ))}
            </Grid>
        </Container>
    );
}

export default Templates;