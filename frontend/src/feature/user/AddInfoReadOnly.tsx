import React from "react";
import {DEFAULT_ELEVATION} from "../../globalConfig";
import {Divider, Paper, Stack} from "@mui/material";
import Avatar from "@mui/material/Avatar";
import Typography from "@mui/material/Typography";
import {AdditionalInfoDTO} from "./model/models";
import Card from "@mui/material/Card";
import CardMedia from "@mui/material/CardMedia";


type AppInfoReadOnlyProps = {
    userInfo: AdditionalInfoDTO;
}
export default function AppInfoReadOnly( {userInfo} : AppInfoReadOnlyProps)
{
return(
    <Card elevation={DEFAULT_ELEVATION} sx={{py : 2, px : 2 }}>
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
            <Typography variant={"h5"} sx={{ mt: 1, textAlign: 'left', alignSelf: 'flex-start' }}>
                {userInfo.name?.split("#")[0]}
            </Typography>

            <Typography variant={"body2"} sx={{ mt: 1, textAlign: 'right', alignSelf: 'flex-end'}}>
                {"#" + userInfo.name?.split("#")[1].substring(0, 8)}
            </Typography>
        </div>

        <Divider color={"primary"} sx={{my : 1}}/>

        <Typography variant={"h6"} >
            "{userInfo.status}"
        </Typography>

        <CardMedia sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', mb: 1 }}>
            <div>
                <Avatar src={userInfo.picture} sx={{ width: 200, height: 200 }}></Avatar>
            </div>
        </CardMedia>

        <Typography variant="body2">
            {userInfo.bio}
        </Typography>
    </Card>
)
}