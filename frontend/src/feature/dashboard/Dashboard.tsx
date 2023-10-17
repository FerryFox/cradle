import React from "react";
import Controller from "../core/Controller";
import Container from "@mui/material/Container";
import {Paper, Toolbar} from "@mui/material";
import Grid from "@mui/material/Grid";
import {useNavigate} from "react-router-dom";

export default function Dashboard()
{
    const navigateTo = useNavigate();


return (
<>
<Controller title="Dashboard"/>
    <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
        <Toolbar />
        <Grid container spacing={3} >
            <Grid item xs={12} >
                <Paper sx={{ p: 2 }} onClick={() => navigateTo("/stampcards")}>
                    See your cards
                </Paper>
            </Grid>

            <Grid item xs={12} >
                <Paper sx={{ p: 2 }} onClick={() => navigateTo("/templates")}>
                    Find the newest cards
                </Paper>
            </Grid>

            <Grid item xs={12}>
                <Paper sx={{ p: 2 }} onClick={() => navigateTo("/template/form")}>
                    Create a new Stampcard
                </Paper>
            </Grid>

            <Grid item xs={12}>
                <Paper sx={{ p: 2 }}>
                    Read the latest news
                    <p> load news from the server</p>
                </Paper>
            </Grid>
        </Grid>
    </Container>
</>
    );
}
