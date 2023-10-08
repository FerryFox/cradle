import React, {useEffect, useState} from "react";
import axios from 'axios';
import {useNavigate} from "react-router-dom";
import Container from "@mui/material/Container";
import Grid from "@mui/material/Grid";
import Template from "./Template";
import BottomController from "../core/BottomController";
import NewTemplateButton from "./NewTemplateButton";
import AppBarComponent from "../core/AppBarComponent";

import {Toolbar} from "@mui/material";

function TemplatesOwned()
{
    const navigate = useNavigate();
    const [templates, setTemplates] = useState([]);
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
            <AppBarComponent showMenuButtonElseBack={false} title="Your Templates"/>
            <Toolbar></Toolbar>
        </Container>);

    if (error) return <div>Error loading data. Please try again later.</div>

return (
    <Container>
        <AppBarComponent showMenuButtonElseBack={false} title="Your Templates"/>
        <Toolbar></Toolbar>
        <BottomController/>


        <Grid container spacing={4} justifyContent="center" sx={{mt :1}}>
            <Grid item xs={6} sm={6} md={4} onClick={() => navigate('/template/form')} >
                <NewTemplateButton ></NewTemplateButton>
            </Grid>

            {templates.map(t => (
                <Grid item xs={6} key={t.name}>
                        <div onClick={() => navigate("/template/details", { state: { templateModel: t }})}>
                            <Template templateModel={t} />
                        </div>
                </Grid>
            ))}
        </Grid>
    </Container>
);
}
export default TemplatesOwned;