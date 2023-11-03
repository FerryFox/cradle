import AppBar from "@mui/material/AppBar";
import {Badge, IconButton, InputAdornment, Toolbar, Typography} from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";
import NotificationsIcon from "@mui/icons-material/Notifications";
import * as React from "react";
import {useNavigate} from "react-router-dom";
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import FilterAltIcon from '@mui/icons-material/FilterAlt';
import TextField from "@mui/material/TextField";
import QuestionMarkIcon from '@mui/icons-material/QuestionMark';
import {number} from "prop-types";
import {useEffect, useState} from "react";
import axios from "axios";

type AppBarComponentProps = {
    toggleDrawer?: () => void,
    title?: string
    showBackButton: boolean
    showSecondLine: boolean
};

export default function AppBarComponent({  toggleDrawer,
                            title,
                            showBackButton,
                            showSecondLine} : AppBarComponentProps,)
{
    const navigate = useNavigate();
    const [mailCount, setMailCount] = useState<number>(0);
    const token = localStorage.getItem("authToken");

    useEffect(() => {
        if (!token) {
            navigate("/signin");
        }
        axios.get<number>("/api/mails/count").then((response) => {
            setMailCount(response.data)
        });
    }, []);

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

                <IconButton color="inherit"  onClick={() => navigate("/mail")}>
                    <Badge badgeContent={mailCount} color="secondary">
                            <NotificationsIcon />
                    </Badge>
                </IconButton>
            </div>

        {showSecondLine && (
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', width: '100%', height: '50%' }}>

                <IconButton edge="start" color="inherit" aria-label="open drawer" onClick={() => navigate(-1)}>
                    <ArrowBackIcon />
                </IconButton>

                <TextField id="search-string"
                           label="Search"
                           variant="filled"
                           InputProps={{
                               startAdornment: (
                                   <InputAdornment position="start">
                                       <QuestionMarkIcon />
                                   </InputAdornment>
                               ),
                           }}
                           style={{ backgroundColor: 'white',
                                    borderRadius: '5px',}}
                           />

                <IconButton>
                    <FilterAltIcon/>
                </IconButton>
            </div>)}

            </Toolbar>
        </AppBar>
    );
}
