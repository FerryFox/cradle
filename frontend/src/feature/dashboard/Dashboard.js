import * as React from 'react';
import Drawer from '@mui/material/Drawer';
import {
    Box, Toolbar, Typography, Divider, IconButton, Badge, Container,
    Grid, Paper, Link, List, ListItem, ListItemIcon, ListItemText, styled, ListItemButton
} from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import NotificationsIcon from '@mui/icons-material/Notifications';
import InboxIcon from '@mui/icons-material/Inbox';
import MailIcon from '@mui/icons-material/Mail';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import DashboardCustomizeIcon from '@mui/icons-material/DashboardCustomize';
import {useNavigate} from "react-router-dom";
import AppBarComponent from "../core/AppBarComponent";
import DrawerComponent from "../core/DrawerComponent";


export default function Dashboard()
{
    const navigate = useNavigate();
    const [open, setOpen] = React.useState(true);
    const toggleDrawer = () => {setOpen(!open);
};

return (
<Box sx={{ display: 'flex' }}>

<AppBarComponent toggleDrawer={toggleDrawer} />
<DrawerComponent open={open} toggleDrawer={toggleDrawer} navigate={navigate} />

    <Box component="main" sx={{backgroundColor: (theme) =>
        theme.palette.mode === 'light'
            ? theme.palette.grey[100]
            : theme.palette.grey[900],
            flexGrow: 1,
            height: '100vh',
            overflow: 'auto',}}>

        <Toolbar />

        <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
        <Grid container spacing={3}>
            <Grid item xs={12} md={8} lg={9}>
                <Paper sx={{ p: 2 }}>
                    Chart Placeholder
                </Paper>
            </Grid>

            <Grid item xs={12} md={4} lg={3}>
                <Paper sx={{ p: 2 }}>
                    Deposits Placeholder
                </Paper>
            </Grid>

            <Grid item xs={12}>
                <Paper sx={{ p: 2 }}>
                    Orders Placeholder
                </Paper>
            </Grid>
        </Grid>
    </Container>
</Box>
</Box>
);}
