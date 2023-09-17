import AppBar from "@mui/material/AppBar";
import {Badge, IconButton, Toolbar, Typography} from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";
import NotificationsIcon from "@mui/icons-material/Notifications";
import * as React from "react";

function AppBarComponent({ toggleDrawer }) {
    return (
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
    );
}
export default AppBarComponent;