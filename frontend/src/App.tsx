import React from 'react';
import './App.css';

import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

import { CssBaseline, ThemeProvider} from "@mui/material";
import { lightTheme } from "./assets/themes/theme";

import HomePage from './feature/home/HomePage';
import Dashboard from './feature/dashboard/Dashboard';
import SignInPage from './feature/user/SignInPage';
import SignUpPage from './feature/user/SignUpPage';
import NotFoundPage from './NotFoundPage';
import ResetPassword from "./feature/user/ResetPassword";
import Templates from "./feature/template/Templates";
import TemplatesOwned from "./feature/template/TemplatesOwned";
import TemplateForm from "./feature/template/TemplateForm";
import TemplateDetails from "./feature/template/TemplateDetails";
import TemplateEdit from "./feature/template/TemplateEdit";
import StampCards from "./feature/stamp_card/StampCards";
import StampCardDetails from "./feature/stamp_card/StempCardDetails";
import Archive from "./feature/archive/Archive";

export default function App()
{

    return (
<div className="App">
    <ThemeProvider theme={lightTheme}>
    <CssBaseline/>
            <Router>
                <Routes>
                    <Route path="*" element={<NotFoundPage />} />
                    <Route path="/" element={<HomePage />} />
                    <Route path="/reset-password" element={<ResetPassword />} />

                    <Route path="/signin" element={<SignInPage />} />
                    <Route path="/signup" element={<SignUpPage />} />
                    <Route path="/dashboard" element={<Dashboard/>} />

                    <Route path="/templates" element={<Templates/>} />
                    <Route path="/templates/owned" element={<TemplatesOwned/>} />
                    <Route path="/template/edit" element={<TemplateEdit/>} />
                    <Route path="/template/form" element={<TemplateForm/>} />
                    <Route path="/template/details" element={<TemplateDetails/>} />

                    <Route path="/stampcards" element={<StampCards/>} />
                    <Route path="/stampcard/details/:id" element={<StampCardDetails/>} />
                    <Route path="/stampcards/archive" element={<Archive/>} />
                </Routes>
            </Router>
    </ThemeProvider>
</div>
    );
}


