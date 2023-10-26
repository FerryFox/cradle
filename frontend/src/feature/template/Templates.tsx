import React, { useState, useEffect } from 'react';
import Template from "./Template";
import Grid from '@mui/material/Grid';
import Container from '@mui/material/Container';
import {IconButton, Snackbar, Toolbar} from "@mui/material";
import Button from "@mui/material/Button";
import {createStampCardFromTemplateId} from "../../assets/service/stampCardService";
import Controller from "../core/Controller";
import {TemplateModel} from "./model/models";
import {loadTemplateModels} from "./service/templateService";
import HighlightOffIcon from '@mui/icons-material/HighlightOff';



export default function Templates()
{
    const [templates, setTemplates] = useState<TemplateModel[]>([]);
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(true);
    const [openSnackbar, setOpenSnackbar] = useState(false);

    const handleGetCardClick = async (template: TemplateModel) => {
        try {
            await createStampCardFromTemplateId(template.id);
            setOpenSnackbar(true);
        } catch (error) {
            console.error("Error creating the stamp card:", error);
        }
    };

    useEffect(() =>
    {
        loadTemplateModels(setTemplates, setLoading)
            .catch( (error) => setError(error.message));
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
     <Controller title={"Public Stamp Cards"} showSecondLine/>
        <Container>
            <Toolbar></Toolbar>
            <Toolbar></Toolbar>

            <Grid container spacing={2} justifyContent="center" sx={{mt : 3}}>
                {templates.map(template => (
                    <Grid item xs={6} sm={6} md={4} key={template.id} >
                        <Template templateModel={template}  />
                        <Button onClick={() => handleGetCardClick(template)}>
                            Get this card
                        </Button>
                    </Grid>
                ))}
            </Grid>

            <Snackbar
                anchorOrigin={{
                    vertical: 'bottom',
                    horizontal: 'left',
                }}
                open={openSnackbar}
                autoHideDuration={2000}
                onClose={() => setOpenSnackbar(false)}
                message="Stamp card created"
                action={
                    <IconButton color="secondary" size="small" onClick={() => setOpenSnackbar(false)}>
                        <HighlightOffIcon />
                    </IconButton>
                }
            />
        </Container>
    </div>
    );
}

