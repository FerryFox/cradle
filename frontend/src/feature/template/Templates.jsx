import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Template from "./Template";
import Grid from '@mui/material/Grid';
import Container from '@mui/material/Container';
import BottomController from "../core/BottomController";
import {Toolbar, Typography} from "@mui/material";
import AppBarComponent from "../core/AppBarComponent";

function Templates()
{
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
            <AppBarComponent showMenuButtonElseBack={false}/>
            <Toolbar></Toolbar>
            <BottomController/>

            <Typography variant="h6">
                Choose a Stamp Card
            </Typography>
            <Typography variant={"body2"}>
                from a list of {templates.length} public templates to get started
            </Typography>

            <Grid container spacing={4} justifyContent="center">
                {templates.map(t => (
                    <Grid item xs={6} sm={6} md={4} key={t.id} >
                        <Template TemplateModel={t} />
                    </Grid>
                ))}
            </Grid>

        </Container>
    );
}

export default Templates;