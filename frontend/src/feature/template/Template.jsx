import React from 'react';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';

function Template({ templateModel })
{
    return (
        <Card sx={{
            p: 0,
            borderRadius: 2,
            minWidth:"50%",
            height: '25vh',}}>
            <CardMedia
                sx={{ height: "15vh" }}
                image={`data:image/jpeg;base64,${templateModel.image}`}
                title="green iguana"
            />

            <CardContent sx={{ display: 'flex', flexDirection: 'column', height: '100%' }}>
                <Typography gutterBottom variant="body2" component="div"  sx={{
                    fontSize: {
                        xs: '0.75rem', // Adjust as needed
                        sm: '0.5rem',},
                    textAlign: 'left',
                }} >
                    {templateModel.name}
                </Typography>

                <Typography variant="body2" color="text.secondary" sx={{
                    fontSize: {
                        xs: '0.5rem', // Adjust as needed
                        sm: '0.4rem',},
                    textAlign: 'left',
                }} >
                    {templateModel.description}

                </Typography>
                <Typography  variant="body2" color="text.secondary" sx={{ mt :1,
                    fontSize: {
                        xs: '0.5rem', // Adjust as needed
                        sm: '0.4rem',},
                    textAlign: 'left',
                }} >
                    {templateModel.stampCardCategory}
                </Typography>
            </CardContent>

            <CardActions>
                <Button
                    variant={"contained"}
                    size="small">
                    Get this Card
                </Button>
            </CardActions>
        </Card>
    );
}

export default Template;