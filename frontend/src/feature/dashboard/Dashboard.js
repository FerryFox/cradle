import * as React from 'react';
import Drawer from '@mui/material/Drawer';
import AppBar from '@mui/material/AppBar';
import {CssBaseline,Box, Toolbar, Typography, Divider, IconButton, Badge, Container,
    Grid, Paper, Link, List} from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import NotificationsIcon from '@mui/icons-material/Notifications';

export default function Dashboard()
{
    const [open, setOpen] = React.useState(true);
    const toggleDrawer = () => {setOpen(!open);
};

return (
<Box sx={{ display: 'flex' }}>

<AppBar position="absolute">
    <Toolbar sx={{ justifyContent: 'space-between' }}>
        <IconButton edge="start" color="inherit" aria-label="open drawer" onClick={toggleDrawer}>
            <MenuIcon />
        </IconButton>

        <Typography component="h1" variant="h6" color="inherit" noWrap>
            Dashboard
        </Typography>

        <IconButton color="inherit">
            <Badge badgeContent={4} color="secondary">
                <NotificationsIcon />
            </Badge>
        </IconButton>

    </Toolbar>
</AppBar>

<Drawer variant="persistent" open={open}>
    <Toolbar>
        <IconButton onClick={toggleDrawer}>
            <ChevronLeftIcon />
        </IconButton>
    </Toolbar>
    <Divider />
    <List component="nav">
        <div>Main List Items Placeholder</div>
        <Divider />
        <div>Secondary List Items Placeholder</div>
    </List>
</Drawer>

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
        <Typography variant="body2" color="text.secondary" align="center" sx={{ pt: 4 }}>
            {'Copyright © '}
            <Link color="inherit" href="https://mui.com/">
                Your Website
            </Link>{' '}
            {new Date().getFullYear()}
            {'.'}
        </Typography>

    </Container>
    </Box>

</Box>
);}
