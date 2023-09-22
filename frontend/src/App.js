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
import Templates from "./feature/template/Templates";
import TemplatesOwned from "./feature/template/TemplatesOwned";
import TemplateForm from "./feature/template/TemplateForm";
import TemplateDetails from "./feature/template/TemplateDetails";
import TemplateEdit from "./feature/template/TemplateEdit";

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
                    <Route path="/templates" element={<Templates/>} />
                    <Route path="/templates/owned" element={<TemplatesOwned/>} />
                    <Route path="/template/form" element={<TemplateForm/>} />
                    <Route path="/template/details" element={<TemplateDetails/>} />
                    <Route path={"/template/edit"} element={<TemplateEdit/>}></Route>
                </Routes>
            </Router>
    </ThemeProvider>
</div>
    );
}

export default App;
