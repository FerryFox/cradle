import Typography from "@mui/material/Typography";
import React, {useEffect, useState} from "react";
import {Button, Divider} from "@mui/material";
import {NewTemplateComposer, TemplateModel} from "../model/models";
import {AppUserDTO} from "../../user/model/models";
import axios from "axios";
import Template from "../Template";
import Grid from "@mui/material/Grid";

type Props = {
    stepBack: () => void;
    handleSubmit : () => void;
    newTemplateModel : NewTemplateComposer;
};

export default function TemplatePreview( {stepBack, handleSubmit, newTemplateModel} : Props)
{
    const [template, setTemplate] = useState<TemplateModel | null>(null);
    useEffect(() => {
        console.log(newTemplateModel);
    }, [template]);

    function createAndSetTemplate(appUser: AppUserDTO, newTemplateModel: NewTemplateComposer)
    {
        const currentDate = new Date();

        console.log(appUser)

        setTemplate({
            id : 0,
            name : newTemplateModel.newBasicInformation.name,
            promise : newTemplateModel.newBasicInformation.promise,
            description : newTemplateModel.newBasicInformation.description,
            defaultCount : newTemplateModel.newBasicInformation.defaultCount,

            stampCardCategory : newTemplateModel.newBasicInformation.stampCardCategory,
            stampCardSecurity : newTemplateModel.newTemplateSecurity.stampCardSecurity,
            stampCardStatus : newTemplateModel.newTemplateSecurity.stampCardStatus,

            image : newTemplateModel.newTemplateImage.image,

            createdBy : appUser.appUserName + "#" + appUser.nameIdentifier,
            createdDate : currentDate.toISOString(),
            creator : appUser,
            userId : appUser.id,

            lastModifiedDate : currentDate.toISOString(),
            expirationDate: currentDate
        });
    }

    useEffect(() =>
    {
        axios.get<AppUserDTO>('/api/user/me')
            .then((response) => {
                createAndSetTemplate(response.data, newTemplateModel);
            })
    }, []);

    return (
        <div>

            <Typography color={"secondary"} variant={"h5"} sx={{my : 2}}>
                Template Preview
            </Typography>

            <Grid container>
                <Grid item xs={12} sx={{mx : 1}}>
                    {template && (<Template templateModel={template} />)}
                </Grid>
            </Grid>

            <Divider color={"primary"} sx={{ mb : 2, mt :3 }}/>

            <Grid container>
                <Grid item xs={6}>
                    <Button variant={"contained"} onClick={stepBack}>
                        Back
                    </Button>
                </Grid>
                <Grid item xs={6}>
                    <Button variant={"contained"} onClick={handleSubmit}>
                        Submit
                    </Button>
                </Grid>
            </Grid>
        </div>
    );
}