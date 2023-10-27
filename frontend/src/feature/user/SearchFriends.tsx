import React, {useEffect, useState} from "react";
import axios from "axios";
import {AppUserDTO} from "./model/models";
import AppUserShortCard from "./AppUserShortCard";
import {IconButton, Paper, Stack} from "@mui/material";
import {DEFAULT_ELEVATION} from "../../globalConfig";
import Typography from "@mui/material/Typography";
import TextField from "@mui/material/TextField";
import Grid from "@mui/material/Grid";
import SearchSharpIcon from '@mui/icons-material/SearchSharp';
import FollowTheSignsIcon from '@mui/icons-material/FollowTheSigns';

type SearchFriendsProps = {
    addFriend: (friendId: string) => void;
}



export default function SearchFriends( {addFriend} : SearchFriendsProps)
{
    const token = localStorage.getItem("authToken");
    const [appUsers , setAppUsers] = useState<AppUserDTO[]>([]);

    const [searchTerm, setSearchTerm] = useState<string>("");

    useEffect(() => {
        if (!token) {return;}
        axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;

        axios.get<AppUserDTO[]>("/api/user/get-users")
            .then((response) =>
            {
                setAppUsers(response.data);
            });
    }, []);


    const removefromAppUsersAndAddToFriends = (id: string) => {
        addFriend(id);
        setAppUsers(appUsers.filter((appUser) => appUser.id.toString() !== id));

    }

    const filteredAppUsers = appUsers.filter((appUser) => {
        return appUser.nameIdentifier.toLowerCase().includes(searchTerm.toLowerCase()) ||
            appUser.appUserName.toLowerCase().includes(searchTerm.toLowerCase());
    });

return (
<>
    <Paper elevation={DEFAULT_ELEVATION} sx={{py : 2 , px : 2}}>
        <Stack spacing={1}>
            <Typography  align="left" variant={"body2"}>
                Persons you may be interested in
            </Typography>

            <Grid container>
                <Grid item xs={2}>
                    <IconButton>
                        <SearchSharpIcon/>
                    </IconButton>
                </Grid>
                <Grid item xs={10}>
                    <TextField
                        fullWidth
                        variant={"standard"}
                        value={searchTerm}
                        onChange={(event) =>
                        {
                            setSearchTerm(event.target.value);

                        }}
                    />
                </Grid>
            </Grid>

            {filteredAppUsers && filteredAppUsers.map((appUser) => (
                <AppUserShortCard
                    key={appUser.id + "search"}
                    appUser={appUser}
                    buttons={[
                        {
                            onClick: removefromAppUsersAndAddToFriends,
                            startIcon: <FollowTheSignsIcon />,
                            label: "Follow",
                            color: "primary"
                        }
                    ]}
                />
            ))}
        </Stack>
    </Paper>
</>
    );
}