function Introduction({ feature })
{
    return(
        <div style={{ display: 'flex', flexDirection: feature.id % 2 === 0 ? 'row-reverse' : 'row', marginBottom: '20px' }}>
            <div style={{ width: '200px', height: '200px', background: `url(${feature.url}) no-repeat center center`, backgroundSize: 'cover' }}>
                {/* Feature Image */}
            </div>
            <div style={{ flex: 1, padding: '0 20px' }}>
                <h2>{feature.title}</h2>
                <p>{feature.description}</p>
            </div>
        </div>
    );
}

export default Introduction;