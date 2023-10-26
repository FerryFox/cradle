import React, {useState} from "react";
import Profile from "./Profile";
import Controller from "../core/Controller";
import {Button, Paper, Stack, Toolbar} from "@mui/material";
import Container from "@mui/material/Container";
import SearchFriends from "./SearchFriends";
import {DEFAULT_ELEVATION} from "../../globalConfig";

export default function ProfilePage() {

    const [showSearchFriends, setShowSearchFriends] = useState(false);

return (
<>
    <Controller title={"Profile"} showBackButton/>
    <Container>
        <Toolbar></Toolbar>
        <Toolbar></Toolbar>
<Stack spacing={2}>
    <Profile/>

    {showSearchFriends ?
        (
                <SearchFriends/>
        )
        :
        (
        <Paper elevation={DEFAULT_ELEVATION}>
            <Button onClick={() => setShowSearchFriends(!showSearchFriends)}>
                Search For Friends
            </Button>
        </Paper>
        )}
</Stack>

    </Container>
</>
    );
}