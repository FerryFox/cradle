import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Template from "./Template";
import Grid from '@mui/material/Grid';
import Container from '@mui/material/Container';
import BottomController from "../core/BottomController";
import {Toolbar} from "@mui/material";
import AppBarComponent from "../core/AppBarComponent";
import Button from "@mui/material/Button";
import {createStampCardFromTemplateId} from "../../assets/service/stampCardService";
import AppController from "../core/Controller";
import Controller from "../core/Controller";

function Templates()
{
    const [templates, setTemplates] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() =>
    {
        setLoading(true);
        setError(null);

        axios.get('/api/templates/all/public')
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

    if (loading) return(
        <Container>
          <p>loading</p>
        </Container>);

    if (error) return <div>Error loading data. Please try again later.</div>

    return (
    <div>
     <Controller title={"Templates"} />
        <Container>
            <Toolbar></Toolbar>

            <Grid container spacing={4} justifyContent="center" sx={{mt : 1}}>
                {templates.map(t => (
                    <Grid item xs={6} sm={6} md={4} key={t.id} >
                        <Template templateModel={t}  />
                        <Button onClick={() => createStampCardFromTemplateId(t.id) }> Get </Button>
                    </Grid>
                ))}
            </Grid>
        </Container>
    </div>
    );
}

export default Templates;