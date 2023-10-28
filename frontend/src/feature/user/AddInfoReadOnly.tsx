import React from "react";
import {DEFAULT_ELEVATION} from "../../globalConfig";
import {Paper, Stack} from "@mui/material";
import Avatar from "@mui/material/Avatar";
import Typography from "@mui/material/Typography";
import {AdditionalInfoDTO} from "./model/models";


type AppInfoReadOnlyProps = {
    userInfo: AdditionalInfoDTO;
}
export default function AppInfoReadOnly( {userInfo} : AppInfoReadOnlyProps)
{
return(
<Paper elevation={DEFAULT_ELEVATION} sx={{mt : 3 , py : 2, px : 2 }}>
    <Stack spacing={2} alignItems={"center"}>
            <Avatar src={userInfo.picture} sx={{ width: 200, height: 200 }}></Avatar>
            <Typography variant={"body1"} sx={{mt: 1}}>
                {userInfo.name}
            </Typography>

            {userInfo.bio &&
                (
                    <Typography variant="body2" alignItems={"left"} >
                        {userInfo.bio}
                    </Typography>
                )
            }
    </Stack>
</Paper>)
}