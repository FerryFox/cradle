import AppBar from "@mui/material/AppBar";
import {Badge, IconButton, Input, Stack, Toolbar, Typography} from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";
import NotificationsIcon from "@mui/icons-material/Notifications";
import * as React from "react";
import {useNavigate} from "react-router-dom";
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import FilterAltIcon from '@mui/icons-material/FilterAlt';

type AppBarComponentProps = {
    toggleDrawer?: () => void,
    title?: string
    showBackButton: boolean
    showSecondLine: boolean
};

function AppBarComponent({  toggleDrawer,
                            title,
                            showBackButton,
                            showSecondLine} : AppBarComponentProps,)
{
    const navigate = useNavigate();

return (
    <AppBar position="absolute">
        <Toolbar sx={{ flexDirection: 'column',
                       height: showSecondLine ? '128px' : '64px'}}>

            <div style={{ display: 'flex',
                          justifyContent: 'space-between',
                          alignItems: 'center',
                          width: '100%',
                          height: showSecondLine ? '50%' : '100%'}}>

                {showBackButton ?
                    (<IconButton edge="start" color="inherit" aria-label="open drawer" onClick={() => navigate(-1)}>
                        <ArrowBackIcon />
                    </IconButton>)
                    :
                    (<IconButton edge="start" color="inherit" aria-label="open drawer" onClick={toggleDrawer}>
                        <MenuIcon />
                    </IconButton>)}

                <Typography component="h1" variant="h5" color="inherit" noWrap>
                    {title}
                </Typography>

                <IconButton color="inherit">
                    <Badge badgeContent={4} color="secondary">
                        <NotificationsIcon />
                    </Badge>
                </IconButton>
            </div>

        {showSecondLine && (
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', width: '100%', height: '50%' }}>

                <IconButton edge="start" color="inherit" aria-label="open drawer" onClick={() => navigate(-1)}>
                    <ArrowBackIcon />
                </IconButton>

                <Typography>
                    Searchbar
                </Typography>

                <IconButton>
                    <FilterAltIcon/>
                </IconButton>
            </div>)}

            </Toolbar>
        </AppBar>
    );
}
export default AppBarComponent;