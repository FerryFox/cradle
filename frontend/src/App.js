import React, {useEffect, useState} from 'react';
import { BrowserRouter as Router, Route, Link, Routes } from 'react-router-dom';
import './App.css';
import Dashboard from './feature/dashboard/Dashboard';
import {createTheme, CssBaseline, ThemeProvider} from "@mui/material";
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
        <ThemeProvider theme={darkTheme}>
        <CssBaseline enableColorScheme/>
        <Router>
            <div className="App">
                <Dashboard></Dashboard>
            </div>
        </Router>
        </ThemeProvider>
    );
}

export default App;
