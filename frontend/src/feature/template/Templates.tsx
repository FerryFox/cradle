import React, { useState, useEffect } from 'react';
import Template from "./Template";
import Grid from '@mui/material/Grid';
import Container from '@mui/material/Container';
import {Toolbar} from "@mui/material";
import Button from "@mui/material/Button";
import {createStampCardFromTemplateId} from "../../assets/service/stampCardService";
import Controller from "../core/Controller";
import {TemplateModel} from "./model/models";
import {loadTemplateModels} from "./service/templateService";

export default function Templates()
{
    const [templates, setTemplates] = useState<TemplateModel[]>([]);
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(true);

    useEffect(() =>
    {
        const fetchData = async () => {
            await loadTemplateModels(setTemplates, setError, setLoading);
        };

        fetchData();
    }, []);

    if (loading) return (
        <Container>
          <p>loading</p>
        </Container>
    )

    if (error) return (
        <div>Error loading data. Please try again later.</div>
    )

    return (
    <div>
     <Controller title={"Templates"} showSecondLine={true} />
        <Container>
            <Toolbar></Toolbar>
            <Toolbar></Toolbar>

            <Grid container spacing={2} justifyContent="center" sx={{mt : 3}}>
                {templates.map(template => (
                    <Grid item xs={6} sm={6} md={4} key={template.id} >
                        <Template templateModel={template}  />
                        <Button onClick={() => createStampCardFromTemplateId(template.id) }> Get this card </Button>
                    </Grid>
                ))}
            </Grid>
        </Container>
    </div>
    );
}

