import React from 'react';
import { Link } from 'react-router-dom';


function HomePage() {
    return (
        <div className="home-container">
            <h1>Welcome to Our Website</h1>

            <div className="navigation-links">
                <Link to="/signin">Sign In</Link>
                <Link to="/signup">Sign Up</Link>
                <Link to="/dashboard">Dashboard</Link>
            </div>
        </div>
    );
}

export default HomePage;