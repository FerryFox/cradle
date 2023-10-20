import React, {useEffect} from "react";
import {Divider, Paper, Stack} from "@mui/material";
import {DEFAULT_ELEVATION} from "../../globalConfig";
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";
import Card from "@mui/material/Card";
import CardMedia from "@mui/material/CardMedia";
import {StampCardModel} from "../stamp_card/model/models";

interface ArchiveCardViewProps {
    stampCard: StampCardModel;
}

export default function ArchiveCard( { stampCard }: ArchiveCardViewProps)
{
    const [displayName , setDisplayName] = React.useState("");
    const [date , setDate] = React.useState("");

    useEffect(() => {
        if (stampCard.templateModel) {
            let namePart = stampCard.templateModel.createdBy.split('-')[0];
            let name = namePart.split('#')[0];
            setDisplayName(name);

            let dateObj = new Date(stampCard.redeemDate);
            let isoString = dateObj.toISOString()
            setDate(isoString.split('T')[0]);
        }
    }, [stampCard.templateModel]);

    return (
    <div style={{ filter: 'grayscale(100%)'}}>
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
                            image={`data:image/jpeg;base64,${stampCard.templateModel.image}`}
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
                            collected : {date}
                        </Typography>
                    </Stack>
                </Grid>
            </Grid>

            <Divider color={"primary"} sx={{my : 1}}></Divider>
        </Paper>
    </div>
    )
}