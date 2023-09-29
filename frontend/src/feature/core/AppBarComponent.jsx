import AppBar from "@mui/material/AppBar";
import {Badge, IconButton, Toolbar, Typography} from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";
import NotificationsIcon from "@mui/icons-material/Notifications";
import * as React from "react";
import {useNavigate} from "react-router-dom";
import ArrowBackIcon from '@mui/icons-material/ArrowBack';

function AppBarComponent({ toggleDrawer , showMenuButtonElseBack = true}) {
    const navigate = useNavigate();

    return (
        <AppBar position="absolute">
            <Toolbar sx={{ justifyContent: 'space-between' }}>
                {showMenuButtonElseBack ?
                    <IconButton edge="start" color="inherit" aria-label="open drawer" onClick={toggleDrawer}>
                        <MenuIcon />
                    </IconButton>
                :
                    <IconButton edge="start" color="inherit" aria-label="open drawer" onClick={() => navigate(-1)}>
                        <ArrowBackIcon />
                    </IconButton>
                }

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
    );
}
export default AppBarComponent;