import React, {useEffect, useState} from 'react';
import { BrowserRouter as Router, Route, Link, Routes } from 'react-router-dom';
import logo from './logo.svg';
import './App.css';
import SoupPage from "./feature/soup/SoupPage";
function App() {
    //code
    const [soup, setSoup] = useState(null);

    useEffect(() => {
        fetch("/api/soups/1") // Fetching the soup with ID 1 as an example
            .then((response) => response.json())
            .then((data) => setSoup(data))
            .catch((error) => console.error("Error fetching soup:", error));
    }, []);

    console.log("Soup value:", soup);
    //view
    return (
        <Router>
            <div className="App">
                <header className="App-header">
                    <img src={logo} className="App-logo" alt="logo" />
                    <nav>
                        <ul>
                            <li><Link to="/">Home</Link></li>
                            <li><Link to="/about">About</Link></li>
                        </ul>
                    </nav>
                    <Routes>
                        <Route path="/" element={<HomePage />} />
                        <Route path="/about" element={<AboutPage />} />
                    </Routes>
                </header>
                {soup && <SoupPage soup={soup} />}
            </div>
        </Router>
    );
}


function HomePage() {
    return (
        <div>
            <p>Welcome to the Home Page</p>
        </div>
    );
}

function AboutPage() {
    return (
        <div>
            <p>Welcome to the About Page</p>
        </div>
    );
}

export default App;
