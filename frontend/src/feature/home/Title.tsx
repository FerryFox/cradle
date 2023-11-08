import Card from "@mui/material/Card";
import Box from "@mui/material/Box";
import CardContent from "@mui/material/CardContent";
import {Typography} from "@mui/material";
import React from "react";
import {DEFAULT_ELEVATION} from "../../globalConfig";



export default function Title( )
{

    return(
        <Card elevation={DEFAULT_ELEVATION} sx={{ mt : 1}}>
            <Box
                sx={{
                    width: '100%',
                    height: '40vh',
                    backgroundImage: `url('https://images.nightcafe.studio/jobs/GvuoWquMBm3xh7q84M3f/GvuoWquMBm3xh7q84M3f--4--k2jld.jpg?tr=w-1600,c-at_max')`, // replace with your image path
                    backgroundSize: 'cover',
                    backgroundPosition: 'center',
                }}>
            </Box>
            <CardContent sx={{ backgroundColor: 'transparent' }}>
                <Typography color={"secondary"} variant={"h2"}>
                    Welcome to StamPede
                </Typography>
            </CardContent>
        </Card>
    )
}
