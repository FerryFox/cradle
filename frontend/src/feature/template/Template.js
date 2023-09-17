import React from 'react';
import PropTypes from 'prop-types';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';

function Template({ template }) {
    return (
        <Card sx={{ maxWidth: 345 }}>
            <CardMedia
                sx={{ height: 140 }}
                image={`data:image/jpeg;base64,${template.image}`}
                title="green iguana"
            />
            <CardContent>
                <Typography gutterBottom variant="h5" component="div">
                    {template.name}
                </Typography>
                <Typography gutterBottom variant="h6" component="div">
                     by {template.createdBy}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                    {template.description}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                   Debug  = {template.stampCardSecurity} : {template.stampCardStatus}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                    Debug  = {template.id} : {template.stampCardCategory}
                </Typography>
            </CardContent>
            <CardActions>
                <Button size="small">Get!</Button>
            </CardActions>
        </Card>
    );
}

// Using PropTypes to validate the data structure
Template.propTypes = {
    template: PropTypes.shape({
        id: PropTypes.number.isRequired,
        name: PropTypes.string.isRequired,
        description: PropTypes.string.isRequired,
        image: PropTypes.string.isRequired,
        createdBy: PropTypes.string.isRequired,
        stampCardCategory: PropTypes.string.isRequired,
        stampCardSecurity: PropTypes.string.isRequired,
        stampCardStatus: PropTypes.string.isRequired,
    }).isRequired,
};

export default Template;