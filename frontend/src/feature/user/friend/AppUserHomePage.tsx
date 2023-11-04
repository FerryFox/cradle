import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import axios from "axios";
import Controller from "../../core/Controller";
import {Divider, Stack, Toolbar} from "@mui/material";
import {AppUserDTO} from "../model/models";
import Template from "../../template/Template";
import Grid from "@mui/material/Grid";
import AddInfoReadOnly from "../AddInfoReadOnly";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import {createStampCardFromTemplateId} from "../../../assets/service/stampCardService";

export default function AppUserHomePage(){

    const { id } = useParams<{ id: string }>();
    const [appUser, setAppUser] = useState<AppUserDTO>({} as AppUserDTO);

    useEffect(() => {
        axios.get(`/api/user/${id}`).then(response => {
            setAppUser(response.data);
        })
    }, []);

    const handleGetCardClick = async (templateId: number) => {
        try {
            await createStampCardFromTemplateId(templateId);
        } catch (error) {
            console.error("Error creating the stamp card:", error);
        }
    };

return(
<>
    <Controller title={"User"} showBackButton/>
    <Toolbar/>
    <Toolbar/>
    <Container>
    <Stack spacing={2}>
        {appUser.addInfoDTO && <AddInfoReadOnly userInfo={appUser.addInfoDTO}/>}

        {appUser.templates &&
            <div >
                <Typography variant={"h5"} alignItems={"left"}>
                    Templates
                </Typography>
                <Divider color={"primary"}></Divider>
            </div>
        }
        {appUser.templates?.map((template) => (
            <Grid key={template.id + "t"}>
                <Grid item xs={12} >
                    <Template templateModel={ template } getButton={handleGetCardClick} />
                </Grid>
            </Grid>
        ))}
    </Stack>
    </Container>
</>
)
}