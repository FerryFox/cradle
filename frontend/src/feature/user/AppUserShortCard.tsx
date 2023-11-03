import React, {useState} from "react";
import {Button, CardActions, CardHeader, Divider, IconButton} from "@mui/material";
import Avatar from "@mui/material/Avatar";
import ChangeCircleIcon from "@mui/icons-material/ChangeCircle";
import {AppUserDTO} from "./model/models";
import Card from "@mui/material/Card";
import {DEFAULT_ELEVATION} from "../../globalConfig";
import CardContent from "@mui/material/CardContent";
import Typography from "@mui/material/Typography";
import {useNavigate} from "react-router-dom";
import PermContactCalendarIcon from '@mui/icons-material/PermContactCalendar';
import PersonSearchIcon from '@mui/icons-material/PersonSearch';


type ButtonConfig = {
    onClick: (id: string) => void;
    startIcon: React.ReactNode;
    label: string;
    color?: "inherit" | "primary" | "secondary" | "success" | "error" | "info" | "warning" ;
};

type AppUserShortCardProps = {
    appUser: AppUserDTO;
    buttons?: ButtonConfig[];
    small?: boolean;
};

export default function AppUserShortCard({appUser, buttons, small } : AppUserShortCardProps )
{
    const [showMore, setShowMore] = useState(false);
    const navigateTo = useNavigate();

return (
<>
    <Card elevation={DEFAULT_ELEVATION}>
            <CardHeader
                avatar={
                    <Avatar src={appUser.addInfoDTO?.picture}>
                        {appUser.appUserName.charAt(0)}{appUser.appUserName.charAt(1)}
                    </Avatar>
                }

                action={
                    !small ? (
                        <IconButton color={showMore?"secondary":"primary"} size={"large"} onClick={() => setShowMore(!showMore)}>
                            <ChangeCircleIcon />
                        </IconButton>
                    ) : (
                        <IconButton color={"primary"} size={"large"} onClick={() => navigateTo(`/appuser/${appUser.id}`)}>
                            <PermContactCalendarIcon/>
                        </IconButton>
                    )
                }
                title={appUser.appUserName}
                subheader={appUser.nameIdentifier.substring(0, 8) + " " +
                           appUser.addInfoDTO?.status ? (appUser.addInfoDTO?.status ) : ("") }
                sx={{ pb: showMore ? 0 : 2, pt : 2 }}/>

        {showMore &&
            <>
            <CardContent>
                <Divider sx={{mb : 1}} color={"primary"}/>
                <Typography variant={"body2"} align={"left"}>
                    {appUser.addInfoDTO?.bio}
                </Typography>

                        <Typography variant={"body2"} align={"left"}>
                            #{appUser.nameIdentifier.substring(0, 8)}
                        </Typography>
            </CardContent>
            <CardActions sx={{ px: 1, justifyContent: 'flex-end'}}>
                <Button startIcon={<PersonSearchIcon/>} variant={"contained"} size={"small"} onClick={() => navigateTo(`/appuser/${appUser.id}`) }>
                    Details
                </Button>
                {buttons && buttons.map((button, index) => (
                    <Button
                        key={index}
                        startIcon={button.startIcon}
                        size="small"
                        variant="contained"
                        color={button.color ?? "primary"}
                        onClick={() => button.onClick(appUser.id.toString())}
                    >
                        {button.label}
                    </Button>
                ))}
            </CardActions>
            </>
        }
    </Card>
</>
);
}