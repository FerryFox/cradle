import React, {useEffect} from "react";
import {StampCardModel} from "./model/models";

import Typography from "@mui/material/Typography";
import {Button, Divider, Paper, Stack} from "@mui/material";
import {useNavigate} from "react-router-dom";
import {DEFAULT_ELEVATION} from "../../globalConfig";
import Grid from "@mui/material/Grid";
import CardMedia from "@mui/material/CardMedia";
import Card from "@mui/material/Card";

interface StampCardViewProps {
    stampCard: StampCardModel;
}


export default function StampCardView( { stampCard }: StampCardViewProps)
{
    const navigateTo = useNavigate();

    const [displayName , setDisplayName] = React.useState("");
    const [date , setDate] = React.useState("");

    useEffect(() => {
        if (stampCard.templateModel) {
            let namePart = stampCard.templateModel.createdBy.split('-')[0];
            let name = namePart.split('#')[0];
            setDisplayName(name);

            let dateObj = new Date(stampCard.templateModel.expirationDate);
            let isoString = dateObj.toISOString()
            setDate(isoString.split('T')[0]);
        }
    }, [stampCard.templateModel]);

return (
<Paper elevation={DEFAULT_ELEVATION} sx={{py :2 ,px: 2}}>
    <Grid container spacing={2} justifyContent="space-between" alignItems="center">
        <Grid item xs={6} >
            <Typography variant={"h4"} align={"left"}>
                {stampCard.templateModel.name}
            </Typography>
        </Grid>

        <Grid item xs={6} >
            <Typography variant={"body1"} align={"right"}>
                {stampCard.templateModel.stampCardCategory}
            </Typography>
        </Grid>
    </Grid>

    <Divider color={"primary"} sx={{mb : 2}}></Divider>

    <Grid container spacing={2} justifyContent="space-between" alignItems="center">
        <Grid item xs={6}>
            <Card>
            <CardMedia
                sx={{ height: "20vh" }}
                image={stampCard.templateModel.image}
                title="green iguana"
            />
            </Card>
        </Grid>

        <Grid item xs={6}>
            <Stack spacing={2}>
                <Typography variant={"body1"} align={"left"} >
                    {stampCard.templateModel.promise}
                </Typography>
                <Typography variant={"body1"} align={"left"}>
                    by  {displayName}
                </Typography>
                <Typography variant={"body1"} align={"left"}>
                    expire : {date}
                </Typography>

                <Button color={"secondary"} variant={"contained"} fullWidth
                    onClick={ () => { navigateTo(`/stampcard/details/${stampCard.id}` )}}>

                    See Details
                </Button>
            </Stack>
        </Grid>
    </Grid>

    <Divider color={"primary"} sx={{my : 1}}></Divider>
</Paper>
    )
}