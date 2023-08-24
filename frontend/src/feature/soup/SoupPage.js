import React from 'react';

function SoupPage({ soup }) {
    return (
        <div>
            <h2>{soup.name}</h2>
            <p><strong>Ingredients:</strong> {soup.ingredients}</p>
        </div>
    );
}

export default SoupPage;