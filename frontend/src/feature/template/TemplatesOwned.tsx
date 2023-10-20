import React, {useEffect, useState} from "react";
import axios from 'axios';
import {useNavigate} from "react-router-dom";
import Container from "@mui/material/Container";
import Grid from "@mui/material/Grid";
import Template from "./Template";
import NewTemplateButton from "./NewTemplateButton";

import {Toolbar} from "@mui/material";
import Controller from "../core/Controller";
import {TemplateModel} from "./model/models";

function TemplatesOwned()
{
    const navigate = useNavigate();
    const [templates, setTemplates] = useState<TemplateModel[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() =>
    {
        setLoading(true);
        setError(null);

        axios.get('/api/templates/my')
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
            <Controller title={"Your Templates"} showSecondLine={false}/>
        </Container>);

    if (error) return <div>Error loading data. Please try again later.</div>

return (
    <>
    <Controller title={"Your Templates"} showSecondLine={false} />
    <Container>
        <Toolbar></Toolbar>
        <Grid container spacing={4} justifyContent="center" sx={{mt :1}}>
            <Grid item xs={6} sm={6} md={4} onClick={() => navigate('/template/form')} >
                <NewTemplateButton ></NewTemplateButton>
            </Grid>

            {templates.map(template => (
                <Grid item xs={6} key={template.id}>
                        <div onClick={() => navigate("/template/details", { state: { templateModel: template }})}>
                            <Template templateModel={template} />
                        </div>
                </Grid>
            ))}
        </Grid>
    </Container>
    </>
);
}
export default TemplatesOwned;