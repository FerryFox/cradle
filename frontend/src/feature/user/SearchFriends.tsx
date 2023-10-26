import React, {useEffect, useState} from "react";
import axios from "axios";
import {AppUserDTO} from "./model/models";
import AppUserShortCard from "./AppUserShortCard";
import {Button, Icon, IconButton, Paper, Stack} from "@mui/material";
import {DEFAULT_ELEVATION} from "../../globalConfig";
import Typography from "@mui/material/Typography";
import TextField from "@mui/material/TextField";
import Grid from "@mui/material/Grid";
import SearchSharpIcon from '@mui/icons-material/SearchSharp';

export default function SearchFriends()
{
    const token = localStorage.getItem("authToken");
    const [appUsers , setAppUsers] = useState<AppUserDTO[]>([]);

    const [searchTerm, setSearchTerm] = useState("");

    useEffect(() => {
        if (!token) {return;}
        axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;

        axios.get<AppUserDTO[]>("/api/user/get-users")
            .then((response) =>
            {
                setAppUsers(response.data);
            });
    }, []);

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
                    <TextField fullWidth variant={"standard"}

                    />
                </Grid>
            </Grid>

            {appUsers && appUsers.map((appUser) => (
                        <AppUserShortCard key={appUser.id}  appUser={appUser}/>
            ))}
        </Stack>
    </Paper>
</>
    );
}