import React, {useEffect, useState} from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import './App.css';
import {createTheme, CssBaseline, ThemeProvider} from "@mui/material";
import HomePage from './HomePage';
import Dashboard from './feature/dashboard/Dashboard';
import SignInPage from './feature/user/SignInPage';
import SignUpPage from './feature/user/SignUpPage';
import NotFoundPage from './NotFoundPage';
import axios from "axios";
import './assets/animation/css/Shake.css';
import ResetPassword from "./feature/user/ResetPassword";

function App() {
// Retrieve token
    const token = localStorage.getItem('authToken');
    if (token) {
        axios.defaults.headers.common['Authorization'] = 'Bearer ' + token;
    }

    const darkTheme = createTheme({
        palette: {
            mode: 'dark',
            // ...other palette configurations if needed
        },
    });

    //view
    //<PrivateRoute path={"/dashboard"} isLoggedIn={true} element={<Dashboard />}></PrivateRoute>
return (
<div className="App">
    <ThemeProvider theme={darkTheme}>
    <CssBaseline enableColorScheme/>
            <Router>
                <Routes>
                    <Route path="/" element={<HomePage />} />
                    <Route path="/signin" element={<SignInPage />} />
                    <Route path="/signup" element={<SignUpPage />} />
                    <Route path="/dashboard" element={<Dashboard/>} />
                    <Route path="*" element={<NotFoundPage />} />
                    <Route path="/reset-password" element={<ResetPassword />} />
                </Routes>
            </Router>
    </ThemeProvider>
</div>
    );
}

export default App;
