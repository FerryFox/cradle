import React, {ChangeEvent, useRef, useState} from "react";
import { Divider, IconButton} from "@mui/material";
import {AdditionalInfoDTO} from "./model/models";
import Typography from "@mui/material/Typography";
import {resizeAndCropImage} from "../../assets/picture/resizeAndCropImage";
import TextField from "@mui/material/TextField";
import {DEFAULT_ELEVATION} from "../../globalConfig";
import Avatar from "@mui/material/Avatar";
import Grid from "@mui/material/Grid";
import EditIcon from '@mui/icons-material/Edit';
import SaveIcon from '@mui/icons-material/Save';
import DeleteForeverIcon from '@mui/icons-material/DeleteForever';
import Card from "@mui/material/Card";
import CardMedia from "@mui/material/CardMedia";

type AddInfoEditProps = {
    userInfo: AdditionalInfoDTO,
    onUpdateUserInfo?: ( info : AdditionalInfoDTO) => void
}

type FormErrors = {
    bio?: string;
    status?: string;
}

export default function AddInfoEdit({userInfo, onUpdateUserInfo}: AddInfoEditProps)
{
    const [newAddInfo , setNewAddInfo] = useState<AdditionalInfoDTO>(userInfo);

    const [editBio , setEditBio] = useState<boolean>(false);
    const [editStatus , setEditStatus] = useState<boolean>(false);

    const fileInputRef = useRef<HTMLInputElement>(null);
    const [formErrors, setFormErrors] = useState<FormErrors>({});
    const handleImageChange = (event: ChangeEvent<HTMLInputElement>) => {
        if (!event.target.files) return;
        const file = event.target.files[0];

        if (file) {
            const reader = new FileReader();

            reader.onload = async function (event: ProgressEvent<FileReader>) {
                if (typeof event.target!.result === 'string') {
                    const resizedImageUrl = await resizeAndCropImage(file, 200, 200);
                    setNewAddInfo(prevState =>
                    {
                        const updatedUserInfo = { ...prevState, picture: resizedImageUrl };
                        handleSubmit(updatedUserInfo);
                        return updatedUserInfo;
                    });
                }
            }
            reader.readAsDataURL(file);
        }
    }

    const validateForm = (userInfo : AdditionalInfoDTO ) => {
        const errors: FormErrors = {};

        if(userInfo.bio && userInfo.bio.length > 251)
        {
            errors.bio = "Bio must be less than 250 characters";
        }

        if (userInfo.status && userInfo.status.length > 34) {
            errors.status = "Status must be less than 35 characters";
        }

        return errors;
    }

    const handleSubmit = async (updatedUserInfo : AdditionalInfoDTO) => {

        const errors = validateForm(updatedUserInfo);
        setFormErrors(errors);
        if(Object.keys(errors).length > 0) return;

        setEditStatus(false);
        setEditBio(false);
        onUpdateUserInfo?.(updatedUserInfo);
    }

    const discardChangesBio = () => {
        setNewAddInfo(prevState => ({ ...prevState, bio: userInfo.bio?? null  }))
        setEditBio(false)
    }

    const discardChangesStatus = () => {
        setNewAddInfo(prevState => ( { ...prevState, status: userInfo.status?? undefined  } ) );
        setEditStatus(false)
    }

return (
<Card elevation={DEFAULT_ELEVATION} sx={{py : 2, px : 2 }}>
    <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <Typography variant={"h5"} sx={{ mt: 1, textAlign: 'left', alignSelf: 'flex-start' }}>
            {userInfo.name?.split("#")[0]}
        </Typography>

        <Typography variant={"body2"} sx={{ mt: 1, textAlign: 'right', alignSelf: 'flex-end'}}>
           {"#" + userInfo.name?.split("#")[1].substring(0, 8)}
        </Typography>
    </div>

    <Divider color={"primary"} sx={{my : 1}}/>

    <div>
        {!editStatus ?
            // If the user is not editing the status, show the status and an edit button
            (<div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <Typography variant={"h6"} >
                    "{userInfo.status}"
                </Typography>

                <IconButton onClick={() => setEditStatus(true)}>
                    <EditIcon color={"primary"} />
                </IconButton>
            </div>)
            :
            // If the user is editing the status, show a text field with the status and a save button
            (<div>
                <Grid container >
                    <Grid item xs={11} >
                        <TextField fullWidth={true}
                                   sx = {{mt : 2}}
                                   variant="standard"
                                   id="bio"
                                   label="Your Status"
                                   name="bio"
                                   value={newAddInfo.status}
                                   error={!!formErrors.status}
                                   helperText={formErrors.status}
                                   onChange={(e) => {
                                       const updatedStatus = e.target.value;
                                       setNewAddInfo({ ...newAddInfo, status: updatedStatus})}}/>
                    </Grid>

                    <Grid item xs={1}>
                        <IconButton sx = {{mt : 2}}
                                    onClick={() => handleSubmit(newAddInfo)}>
                            <SaveIcon color={"primary"}/>
                        </IconButton>
                        <IconButton
                            onClick={discardChangesStatus}>
                            <DeleteForeverIcon color={"secondary"}/>
                        </IconButton>
                    </Grid>
                </Grid>
            </div>)
        }
    </div>


    <CardMedia sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', mb: 1 }}>
        <div onClick={() => fileInputRef.current?.click()}>
            <Avatar src={newAddInfo.picture} sx={{ width: 200, height: 200 }}></Avatar>
            <input
                ref={fileInputRef}
                type="file"
                style={{ display: 'none' }}
                onChange={handleImageChange}/>
        </div>
    </CardMedia>

    <div >
        {!editBio ?
            (<>
                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                    <Typography variant="body2">
                        {newAddInfo.bio}
                    </Typography>

                    <IconButton onClick={() => setEditBio(true)}>
                        <EditIcon color={"primary"}/>
                    </IconButton>
                </div>
                <Divider color={"primary"} sx={{my : 1}}/>
            </>)
            :
            (<>
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
                                       value={newAddInfo.bio}
                                       error={!!formErrors.bio}
                                       helperText={formErrors.bio}
                                       onChange={(e) => {
                                           const updatedStatus = e.target.value;
                                           setNewAddInfo({ ...newAddInfo, bio: updatedStatus})}}/>
                        </Grid>

                        <Grid item xs={1}>
                            <IconButton sx = {{mt : 2}}
                                onClick={() => handleSubmit(newAddInfo)}>
                                <SaveIcon color={"primary"}/>
                            </IconButton>
                            <IconButton
                                        onClick={discardChangesBio}>
                                <DeleteForeverIcon color={"secondary"}/>
                            </IconButton>
                        </Grid>
                    </Grid>
                </div>
            </>)
        }
    </div>
</Card>
    );
}