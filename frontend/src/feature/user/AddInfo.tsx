import React, {ChangeEvent, useEffect, useRef, useState} from "react";
import {Button, IconButton, Paper, Stack} from "@mui/material";
import {AdditionalInfoDTO} from "./model/models";
import Typography from "@mui/material/Typography";
import {resizeAndCropImage} from "../../assets/picture/resizeAndCropImage";
import TextField from "@mui/material/TextField";
import ChangeCircleIcon from '@mui/icons-material/ChangeCircle';
import {DEFAULT_ELEVATION} from "../../globalConfig";
import Avatar from "@mui/material/Avatar";
import Grid from "@mui/material/Grid";

export default function AddInfo({userInfo, onUpdateUserInfo}: {userInfo: AdditionalInfoDTO|undefined, onUpdateUserInfo: (info: AdditionalInfoDTO) => void})
{
    const [editInfo , setEditInfo] = useState<AdditionalInfoDTO>(userInfo as AdditionalInfoDTO);

    const [id , setId] = useState<string>( "");
    const [name , setName] = useState<string>( "");
    const [bio , setBio] = useState<string>("");
    const [picture , setPicture] = useState<string>("");
    const fileInputRef = useRef<HTMLInputElement>(null);

    const token = localStorage.getItem("authToken");

    const handleImageChange = (event: ChangeEvent<HTMLInputElement>) => {
        if (!event.target.files) return;
        const file = event.target.files[0];

        if (file) {
            const reader = new FileReader();

            reader.onload = async function (event: ProgressEvent<FileReader>) {
                if (typeof event.target!.result === 'string') {
                    const resizedImageUrl = await resizeAndCropImage(file, 200, 200);
                    setEditInfo(prevState => {
                        const updatedUserInfo = { ...prevState, picture: resizedImageUrl };
                        handleSubmit(updatedUserInfo);  // Call the handleSubmit method with the updated user info including the new image
                        return updatedUserInfo;
                    });
                }
            }
            reader.readAsDataURL(file);
        }
    }


    const handleSubmit = async (updatedUserInfo : AdditionalInfoDTO) => {
        if (!token) {return;}
        onUpdateUserInfo(updatedUserInfo); // Use the function passed down from the parent to update the user info
    }

return (
<Paper elevation={DEFAULT_ELEVATION} sx={{mt : 3 , py : 2, px : 2 }}>
    <Stack spacing={2} alignItems={"center"}>
        <div onClick={() => fileInputRef.current?.click()}>
                <Avatar src={editInfo.picture} sx={{ width: 200, height: 200 }}></Avatar>
            <input
                ref={fileInputRef}
                type="file"
                style={{ display: 'none' }}
                onChange={handleImageChange}
            />
            <Typography variant={"body1"} sx={{mt: 1}}>
                {name}
            </Typography>
        </div>

        <div>
        {editInfo.bio ?
            (
                <>
                    <Typography variant="body2" alignItems={"left"} >
                        {editInfo.bio}
                    </Typography>

                    <Button
                        onClick={() => setEditInfo(prevState => (
                        { ...prevState, bio: null  }))}>

                        Edit Bio
                    </Button>
                </>
            )
            :
            (
                <>
                    <Typography variant="body2" alignItems={"left"} >
                        "Everyone has a unique story, and we believe that sharing it can make the world feel a little bit closer. This is your space to introduce yourself, share your passions, hobbies, and what makes you, you!"
                    </Typography>
                    <div>
                        <Grid container >
                            <Grid item xs={11} >
                                <TextField multiline={true}
                                           fullWidth={true}
                                           sx = {{mt : 2}}
                                           variant="standard"
                                           id="bio"
                                           label="Write about yourself"
                                           name="bio"
                                           value={bio}
                                           onChange={e =>
                                               setBio(e.target.value)}/>
                            </Grid>
                            <Grid item xs={1}>
                                <IconButton sx = {{mt : 2}}
                                    onClick={() => {
                                        setEditInfo(prevState => {
                                            const updatedUserInfo = { ...prevState, bio: bio };
                                            handleSubmit(updatedUserInfo);  // Call the handleSubmit method with the updated user info
                                            return updatedUserInfo;
                                        });}}>
                                    <ChangeCircleIcon color={"primary"}/>
                                </IconButton>
                            </Grid>
                        </Grid>
                    </div>
                </>
            )
        }
        </div>
    </Stack>
</Paper>
    );
}