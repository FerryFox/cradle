import CardMedia from "@mui/material/CardMedia";
import React, {useState} from "react";
import {Button, CardActions, CardHeader, Divider, IconButton} from "@mui/material";
import Avatar from "@mui/material/Avatar";
import ChangeCircleIcon from "@mui/icons-material/ChangeCircle";
import {AppUserDTO} from "./model/models";
import Card from "@mui/material/Card";
import {DEFAULT_ELEVATION} from "../../globalConfig";
import CardContent from "@mui/material/CardContent";
import Typography from "@mui/material/Typography";
import FollowTheSignsIcon from '@mui/icons-material/FollowTheSigns';
import MessageIcon from '@mui/icons-material/Message';


export default function AppUserShortCard({appUser} : {appUser : AppUserDTO})
{
    const [showMore, setShowMore] = useState(false);

return (
<>
    <Card elevation={DEFAULT_ELEVATION}>
            <CardHeader
                avatar={
                    <Avatar src={appUser.addInfoDTO?.picture}  >
                        {appUser.appUserName.charAt(0)}{appUser.appUserName.charAt(1)}
                    </Avatar>
                }
                action={
                    <IconButton onClick={() => setShowMore(!showMore)}>
                        <ChangeCircleIcon/>
                    </IconButton>
                }
                title={appUser.appUserName}
                subheader={appUser.nameIdentifier.substring(0, 8) + " " +
                           appUser.addInfoDTO?.status ? (appUser.addInfoDTO?.status ) : ("") }
            />

        {showMore &&
            <>
            <CardContent>
                <Divider sx={{mb : 2}}/>
                <Typography variant={"body2"} align={"left"}>
                    {appUser.addInfoDTO?.bio}
                </Typography>

                        <Typography variant={"body2"} align={"left"}>
                            # {appUser.nameIdentifier.substring(0, 8)}
                        </Typography>
            </CardContent>
            <CardActions sx={{ px: 1, justifyContent: 'flex-end'}}>
                <Button startIcon={<FollowTheSignsIcon/>} size={"small"} variant={"contained"}>
                    Follow
                </Button>
                <Button startIcon={<MessageIcon/>} size={"small"} variant={"contained"}>
                    Message
                </Button>
            </CardActions>
            </>
        }
    </Card>
</>
);
}