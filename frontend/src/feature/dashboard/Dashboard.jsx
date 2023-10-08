import * as React from 'react';
import {
    Box, Toolbar, Container,
    Grid, Paper,
} from '@mui/material';
import {useNavigate} from "react-router-dom";
import AppBarComponent from "../core/AppBarComponent";
import DrawerComponent from "../core/DrawerComponent";
import BottomController from "../core/BottomController";

export default function Dashboard()
{
    const navigate = useNavigate();
    const [open, setOpen] = React.useState(false);
    const toggleDrawer = () => {setOpen(!open);
};

return (

<Box sx={{ display: 'flex' }}>
    <BottomController/>
    <AppBarComponent toggleDrawer={toggleDrawer} />
    <DrawerComponent open={open} toggleDrawer={toggleDrawer} navigate={navigate} />

    <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>

    <Toolbar />
        <Grid container spacing={3} >
            <Grid item xs={12} >
                <Paper sx={{ p: 2 }} onClick={() => navigate("/stampcards")}>
                   See your cards
                </Paper>
            </Grid>

            <Grid item xs={12} >
                <Paper sx={{ p: 2 }} onClick={() => navigate("/templates")}>
                   Find the newest cards
                </Paper>
            </Grid>

            <Grid item xs={12}>
                <Paper sx={{ p: 2 }} onClick={() => navigate("/template/form")}>
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
</Box>

);}
