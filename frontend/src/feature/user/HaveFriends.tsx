import {AppUserDTO} from "./model/models";
import React from "react";
import AppUserShortCard from "./AppUserShortCard";
import {DEFAULT_ELEVATION} from "../../globalConfig";
import { Paper, Stack} from "@mui/material";
import Typography from "@mui/material/Typography";
import DeleteIcon from '@mui/icons-material/Delete';


type HaveFriendsProps = {
    friends: AppUserDTO[];
    deleteFriend: (friendId: string) => void;
}

export default function HaveFriends( {friends, deleteFriend} : HaveFriendsProps)
{
    const removefromAppUsersAndAddToFriends = (id: string) => {
        deleteFriend(id);
    }

return (
<>
    <Paper elevation={DEFAULT_ELEVATION} sx={{py : 2 , px : 2}}>
        <Stack spacing={1}>
            <Typography  align="left" variant={"body2"}>
                Your friends
            </Typography>

            {friends.map((friend) => (
                <AppUserShortCard
                    key={friend.id + "have"}
                    appUser={friend}
                    buttons={[
                        {
                            onClick: removefromAppUsersAndAddToFriends,
                            startIcon: <DeleteIcon />,
                            label: "Delete",
                            color: "warning"
                        }
                    ]}
                />
            ))}
        </Stack>
    </Paper>
</>
);
}