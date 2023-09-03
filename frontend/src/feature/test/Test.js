import React from 'react';
import axios from 'axios';

function Test() {
    const handleButtonClick = async () => {
        try {
            const response = await axios.get('http://localhost:8080/greet', {
                headers: {
                    'Content-Type': 'application/json'
                },
                params: {
                    name: 'John'
                }
            });

            console.log(response.data);
        } catch (error) {
            console.error('Error fetching data:', error.response ? error.response.data : error.message);
        }
    };

    return (
        <div>
            <button onClick={handleButtonClick}>
                Fetch Greeting
            </button>
        </div>
    );
}

export default Test;