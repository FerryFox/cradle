import CardMedia from "@mui/material/CardMedia";
import CardContent from "@mui/material/CardContent";
import Typography from "@mui/material/Typography";
import CardActions from "@mui/material/CardActions";
import Button from "@mui/material/Button";
import {createStampCardFromTemplateId} from "../../assets/service/stampCardService";
import Card from "@mui/material/Card";
import React from "react";

function NewTemplateButton()
{

    return (
        <Card sx={{
            p: 0,
            borderRadius: 2,
            minWidth:"50%",
            height: '25vh',}}>
            <CardMedia
                sx={{ height: "15vh" }}
                image={"https://images.nightcafe.studio/jobs/fZkzPhEQ5QOxvoZ7C20g/fZkzPhEQ5QOxvoZ7C20g--1--3xhzv.jpg?tr=w-1600,c-at_max"}
                title="green iguana"
            />

            <CardContent sx={{ display: 'flex', flexDirection: 'column', height: '100%' }}>
                <Typography gutterBottom variant="body2" component="div"  sx={{
                    fontSize: {
                        xs: '0.75rem', // Adjust as needed
                        sm: '0.5rem',},
                    textAlign: 'left',
                }} >

                        Be creative

                </Typography>

                <Typography variant="body2" color="text.secondary" sx={{
                    fontSize: {
                        xs: '0.5rem', // Adjust as needed
                        sm: '0.4rem',},
                    textAlign: 'left',
                }} >

                    Create a new template from scratch and share it with the world

                </Typography>
                <Typography variant="body2" color="text.secondary" sx={{ mt :1,
                    fontSize: {
                        xs: '0.5rem', // Adjust as needed
                        sm: '0.4rem',},
                    textAlign: 'left',
                }} >

                </Typography>
            </CardContent>

            <CardActions>
                <Button onClick={() => {createStampCardFromTemplateId(template.id)}}
                        variant={"contained"}
                        size="small">
                    Get this Card
                </Button>
            </CardActions>
        </Card>


    );
}

export default NewTemplateButton;