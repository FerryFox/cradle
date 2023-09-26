import React, {useEffect, useState} from "react";
import axios from 'axios';
import TemplateForm from "./TemplateForm";
import {useNavigate} from "react-router-dom";
import Container from "@mui/material/Container";
import Grid from "@mui/material/Grid";
import Paper from "@mui/material/Paper";
import Template from "./Template";
import Button from "@mui/material/Button";
import Box from "@mui/material/Box";

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

    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error loading data. Please try again later.</div>

    return (
        <Container>
            <h1>Your Templates</h1>
            <p>Total templates: {templates.length}</p>

            <Grid container spacing={4} justifyContent="center">
                <Grid item xs={12} sm={6} md={4}>
                    <Box
                        component="div"
                        elevation={3}
                        style={{ height: '300px', backgroundColor: 'lightgray', cursor: 'pointer' }}
                        onClick={() => navigate('/template/form')}>
                        Click to create new template
                    </Box>
                </Grid>

                {templates.map(t => (
                <Grid item xs={12} sm={6} md={4} key={t.name}>
                        <div onClick={() => navigate("/template/details", { state: { templateModel: t }})}>
                            <Template TemplateModel={t} />
                        </div>
                </Grid>
                ))}
            </Grid>
        </Container>
    );
}
export default TemplatesOwned;