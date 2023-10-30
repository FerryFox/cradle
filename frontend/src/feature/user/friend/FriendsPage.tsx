import React, {useEffect, useState} from "react";
import {AppUserDTO} from "../model/models";
import Controller from "../../core/Controller";
import Container from "@mui/material/Container";
import {Button, Paper, Stack, Toolbar} from "@mui/material";
import axios from "axios";
import HaveFriends from "../HaveFriends";
import SearchFriends from "../SearchFriends";
import {DEFAULT_ELEVATION} from "../../../globalConfig";

export default function FriendsPage(){
    const [appUser, setAppUser] =
        useState<AppUserDTO>({} as AppUserDTO);

    const [showSearchFriends, setShowSearchFriends] = useState(false);
    const token = localStorage.getItem("authToken");

    useEffect(() => {
        if (!token) {return;}
        axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
        axios.get<AppUserDTO>("/api/user/me")
            .then((response) => {
                    setAppUser(response.data);
                }
            );
    }, []);

    const addFriend = async (friendId: string) => {
        axios.post<AppUserDTO>("/api/user/add-friend/" + friendId)
            .then((response) => {
                setAppUser(prevAppUser => {
                    // Check if friends is undefined. If it is, initialize it as an empty array
                    const currentFriends = prevAppUser.friends?? [];
                    return {
                        ...prevAppUser,
                        friends: [...currentFriends, response.data]
                    };
                });
            });
    };

    const deleteFriend = async (friendId: string) => {
        axios.delete("/api/user/delete-friend/" + friendId)
            .then(() => {
                setAppUser(prevAppUser => {
                    const currentFriends = prevAppUser.friends?? [];
                    return {
                        ...prevAppUser,
                        friends: currentFriends.filter((friend) => friend.id.toString() !== friendId)
                    };
                });
            });
    };

    return (
        <>
            <Controller title={"Friends"}/>
            <Container>
                <Toolbar></Toolbar>
                <Toolbar></Toolbar>
                <Stack spacing={2}>

                    {appUser.friends && appUser.friends.length > 0 &&
                        <HaveFriends friends={appUser.friends} deleteFriend={deleteFriend}/>
                    }

                    {showSearchFriends ?
                        (
                            <SearchFriends addFriend={addFriend}/>
                        )
                        :
                        (
                            <Paper elevation={DEFAULT_ELEVATION}>
                                <Button onClick={() => setShowSearchFriends(!showSearchFriends)}>
                                    Search For Friends
                                </Button>
                            </Paper>
                        )
                    }
                </Stack>
            </Container>
        </>);
}