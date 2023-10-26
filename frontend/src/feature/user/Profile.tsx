import React, {ChangeEvent, useEffect, useRef, useState} from "react";
import {Button, Divider, IconButton, Paper, Stack, Toolbar} from "@mui/material";
import Avatar from "@mui/material/Avatar";
import Grid from "@mui/material/Grid";
import {AdditionalInfoDTO} from "./model/models";
import axios from "axios";
import Typography from "@mui/material/Typography";
import {resizeAndCropImage} from "../../assets/picture/resizeAndCropImage";
import TextField from "@mui/material/TextField";
import ChangeCircleIcon from '@mui/icons-material/ChangeCircle';
import {DEFAULT_ELEVATION} from "../../globalConfig";

export default function Profile()
{
    const [userInfo, setUserInfo] =
       useState<AdditionalInfoDTO>({});

    const [name , setName] = useState<string>( "");
    const [id , setId] = useState<string>( "");

    const [bio , setBio] = useState<string>(userInfo.bio ?? "");

    const [loading, setLoading] = React.useState(true);
    const [error, setError] = React.useState(false);
    const token = localStorage.getItem("authToken");

    const fileInputRef = useRef<HTMLInputElement>(null);


    const handleImageChange = (event :ChangeEvent<HTMLInputElement>) => {
        if (!event.target.files) return;
        const file = event.target.files[0];

        if (file) {
            const reader = new FileReader();

            reader.onload = async function (event: ProgressEvent<FileReader>) {
                if (typeof event.target!.result === 'string') {
                    const resizedImageUrl = await resizeAndCropImage(file, 200, 200);
                    setUserInfo(prevState => {
                        const updatedUserInfo = { ...prevState, picture: resizedImageUrl };
                        handleSubmit(updatedUserInfo); // Call the handleSubmit method with the updated user info
                        return updatedUserInfo;
                    });
                }
            };
            reader.readAsDataURL(file);
        }
    }

    useEffect(() => {
        if (!token) {return;}
        axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
        axios.get<AdditionalInfoDTO>("/api/user/my-additional-info")
            .then((response) => {
                setUserInfo(response.data);
                setBio(response.data.bio ?? "")

                const str = response.data.name;
                const parts = str?.split('#');
                const name = parts ? parts[0] : "";
                const id = parts ? parts[1] : "";
                setName(name);
                setId(id);
            }
        ).catch((error) => {
            setError(true)
        }).finally(() => {
            setLoading(false);
        });
    }, []);

    const handleSubmit = async (updatedUserInfo : AdditionalInfoDTO) => {
        axios.post<AdditionalInfoDTO>("/api/user/additional-info", updatedUserInfo)
            .then((response) => {
                setUserInfo(response.data)
            }).catch((error) => {
            setError(true);
        }).finally(() => {

        });
    }

return (
<Paper elevation={DEFAULT_ELEVATION} sx={{mt : 3 , py : 2, px : 2 }}>
    <Stack spacing={2} alignItems={"center"}>
        <div onClick={() => fileInputRef.current?.click()}>
                <Avatar src={userInfo.picture} sx={{ width: 200, height: 200 }}></Avatar>
            <input
                ref={fileInputRef}
                type="file"
                style={{ display: 'none' }}
                onChange={handleImageChange}
            />
            <Typography variant={"body1"} sx={{mt: 1}}>
                {name} #{id.substring(0, 8)}
            </Typography>
        </div>

        <div>
        {userInfo.bio ?
            (
                <>
                    <Typography variant="body2" alignItems={"left"} >
                        {userInfo.bio}
                    </Typography>

                    <Button
                        onClick={() => setUserInfo(prevState => (
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
                                        setUserInfo(prevState => {
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