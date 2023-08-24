import React from 'react';
import { BrowserRouter as Router, Route, Link, Routes } from 'react-router-dom';
import logo from './logo.svg';
import './App.css';

function App() {
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
