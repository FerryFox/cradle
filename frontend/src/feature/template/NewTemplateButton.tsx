import CardMedia from "@mui/material/CardMedia";
import CardContent from "@mui/material/CardContent";
import Typography from "@mui/material/Typography";
import Card from "@mui/material/Card";
import React from "react";
import {Divider} from "@mui/material";

export default function NewTemplateButton()
{
    return (
        <Card sx={{
            p: 0,
            borderRadius: 2,
            position: 'relative',
            minWidth:"50%",
            height: '34vh',}}>
            <CardMedia
                sx={{ height: "17vh" }}
                image={"https://images.nightcafe.studio/jobs/fZkzPhEQ5QOxvoZ7C20g/fZkzPhEQ5QOxvoZ7C20g--1--3xhzv.jpg?tr=w-1600,c-at_max"}
                title="green iguana"
            />

            <CardContent
                sx={{ display: 'flex',
                      flexDirection: 'column',
                      height: '100%',
                      fontWeight: 'bold' }}>
                <Typography gutterBottom variant="h5" align="left">
                        Create a Card
                </Typography>

                <Divider color={"error"}  style={{ marginBottom: '8px'}}/>

                <Typography variant="body2"  style={{  textAlign: 'left' }} >
                    Create a new template and share it with the world
                </Typography>

                <Typography  variant="body2"
                             color={"secondary"}
                             style={{
                                 position: 'absolute',
                                 bottom: '8px',
                                 right: '8px'}}>

                    CREATE
                </Typography>
            </CardContent>
        </Card>
    );
}

