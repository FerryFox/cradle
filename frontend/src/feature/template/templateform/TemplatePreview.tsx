import Typography from "@mui/material/Typography";
import React, {useEffect, useState} from "react";
import {Button, Divider} from "@mui/material";
import {NewTemplateComposer, TemplateModel} from "../model/models";
import {AppUserDTO} from "../../user/model/models";
import axios from "axios";
import Template from "../Template";

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

            <Typography variant={"h6"}>
                Template Preview
            </Typography>

            {template && (<Template templateModel={template} />)}


            <Divider color={"primary"} sx={{ my : 2 }}/>
            <Button variant={"text"} onClick={stepBack}>
                Back
            </Button>
            <Button variant={"text"} onClick={handleSubmit}>
                Submit
            </Button>
        </div>
    );
}