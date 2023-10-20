import {Button, ButtonGroup, Paper} from "@mui/material";
import DashboardIcon from "@mui/icons-material/Dashboard";
import LoginIcon from "@mui/icons-material/Login";
import AssignmentIcon from "@mui/icons-material/Assignment";
import React from "react";
import {NavigateFunction} from "react-router-dom";
import {DEFAULT_ELEVATION} from "../../globalConfig";

type ButtonHubProps = {
    isAuthenticated: boolean;
    deleteToken: () => void;
    navigate: NavigateFunction;
};

export default function ButtonHub({isAuthenticated, deleteToken, navigate}: ButtonHubProps)
{
    return (
        <Paper elevation={DEFAULT_ELEVATION} sx={{ borderRadius: 1 }}>
                <ButtonGroup
                variant="contained"

                 sx={{
                        display: 'flex', // Set ButtonGroup as flex container
                        width: '100%',   // Make sure it takes the full width of its parent
                    }}>

                    {isAuthenticated ? (
                        <>
                            <Button
                                fullWidth
                                size={"large"}
                                color="primary"
                                onClick={() => navigate("/dashboard")}
                                sx={{ flex: 1, textTransform: 'none', fontSize: '16px' }} // Override text transformation and font size
                            >
                                <DashboardIcon sx={{ marginRight: '8px' }} /> {/* Adding some spacing between icon and text */}
                                Dashboard
                            </Button>
                            <Button
                                fullWidth
                                size={"large"}
                                color="primary"
                                onClick={() => deleteToken()}
                                sx={{ flex: 1, textTransform: 'none', fontSize: '16px' }}
                            >
                                <LoginIcon sx={{ marginRight: '8px' }} />
                                Logout
                            </Button>
                        </>
                    ) : (
                        <>
                            <Button
                                fullWidth
                                size={"large"}
                                color="primary"
                                onClick={() => navigate("/signin")}
                                sx={{flex: 1, textTransform: 'none', fontSize: '16px' }}
                            >
                                <LoginIcon sx={{ marginRight: '8px' }} />
                                Sign In
                            </Button>
                            <Button
                                fullWidth
                                size={"large"}
                                color="primary"
                                onClick={() => navigate("/signup")}
                                sx={{flex: 1, textTransform: 'none', fontSize: '16px' }}
                            >
                                <AssignmentIcon sx={{ marginRight: '8px' }} />
                                Register
                            </Button>
                        </>
                    )}
                </ButtonGroup>
        </Paper>
    )
}