import React, {useEffect, useState} from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import './App.css';
import {createTheme, CssBaseline, ThemeProvider} from "@mui/material";
import HomePage from './HomePage';
import Dashboard from './feature/dashboard/Dashboard';
import SignInPage from './feature/user/SignInPage';
import SignUpPage from './feature/user/SignUpPage';
import NotFoundPage from './NotFoundPage';

function App() {
    //code
    const [soup, setSoup] = useState(null);

    const darkTheme = createTheme({
        palette: {
            mode: 'dark',
            // ...other palette configurations if needed
        },
    });

    useEffect(() => {
        fetch("/api/soups/1") // Fetching the soup with ID 1 as an example
            .then((response) => response.json())
            .then((data) => setSoup(data))
            .catch((error) => console.error("Error fetching soup:", error));
    }, []);

    //view
return (
<div className="App">
    <ThemeProvider theme={darkTheme}>
    <CssBaseline enableColorScheme/>

            <Router>
                <Routes>
                    <Route path="/" element={<HomePage />} />
                    <Route path="/signin" element={<SignInPage />} />
                    <Route path="/signup" element={<SignUpPage />} />
                    <Route path="/dashboard" element={<Dashboard />} />
                    <Route path="*" element={<NotFoundPage />} />
                </Routes>
            </Router>


    </ThemeProvider>
</div>
    );
}

export default App;
