import React from 'react';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import {TemplateModel} from "./models/TemplateModel";

function Template({ TemplateModel })
{
    return (
        <Card sx={{ maxWidth: 345 }}>
            <CardMedia
                sx={{ height: 140 }}
                image={`data:image/jpeg;base64,${TemplateModel.image}`}
                title="green iguana"
            />
            <CardContent>
                <Typography gutterBottom variant="h5" component="div">
                    {TemplateModel.name}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                    {TemplateModel.description}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                     {TemplateModel.stampCardCategory}
                </Typography>
            </CardContent>
            <CardActions>

            </CardActions>
        </Card>
    );
}

export default Template;