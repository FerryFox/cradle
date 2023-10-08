import Card from "@mui/material/Card";
import { Divider, Typography} from "@mui/material";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import React from "react";
import {News} from "./model/models";
import {DEFAULT_ELEVATION} from "../../globalConfig";


type IntroductionCardProps = {
    news: News;
    order: number;
}

export default function IntroductionCard({ news, order } : IntroductionCardProps)
{

const isImageOnRight = order % 2 === 0;

    return(
        <Card elevation={DEFAULT_ELEVATION} sx={{
            display: 'flex',
            flexDirection: isImageOnRight ? 'row-reverse' : 'row',
            mb: 2, width: '100%',
            height : "22vh"}}>

            <CardMedia
                component="img"
                sx={{ width: "40%", objectFit: 'cover' }}
                image={news.imageUrl}
                alt="Feature"
            />
            <CardContent sx={{ flex: '1 0 auto', width: "50%" }}>
                <Typography color={"primary.dark"}  variant="h5" sx={{ fontWeight: 'bold' }}>
                    {news.title}
                </Typography>
                <Divider sx={{ mb: 1 , height: '5px' }}/>
                <Typography variant={"body2"}>
                    {news.description}
                </Typography>
            </CardContent>
        </Card>
        );
}

