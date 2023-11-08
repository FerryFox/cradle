import React, {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {NewMail} from "./model/models";
import {AppUserDTO} from "../user/model/models";
import axios from "axios";
import AppUserShortCard from "../user/AppUserShortCard";
import AddIcon from '@mui/icons-material/Add';
import Controller from "../core/Controller";
import {Button, Divider, Paper, Toolbar} from "@mui/material";
import Typography from "@mui/material/Typography";
import {DEFAULT_ELEVATION} from "../../globalConfig";
import {TemplateModel} from "../template/model/models";
import Template from "../template/Template";
import Grid from "@mui/material/Grid";
import TextField from "@mui/material/TextField";

type ErrorForm = {
    message?: string;
    user?: string;
}

type RouteParams = {
    templateId?: string;
    recipientId?: string;
};

export default function MailForm( ) {

    const navigateTo = useNavigate();
    const { templateId, recipientId } = useParams<RouteParams>();

    const [newMail, setNewMail] = useState<NewMail>();
    const [appUsers , setAppUsers] = useState<AppUserDTO[]>([]);

    const [recipient, setRecipient] = useState<AppUserDTO>();

    const [toggleUser, setToggleUser] = useState<boolean>(true);

    const [templates, setTemplates] = useState<TemplateModel[]>([]);
    const [template, setTemplate] = useState<TemplateModel>();
    const [toggleTemplate, setToggleTemplate] = useState<boolean>(true);

    const [message, setMessage] = useState<string>("");

    const [errorForm, setErrorForm] = useState<ErrorForm>({});

    const validateForm = () => {
        const newError: ErrorForm = {};
        if (!message) {
            newError.message = "Message is required!";
        }
        // Check if message exceeds 200 characters
        else if (message.length > 200) {
            newError.message = "Message must be 200 characters or less.";
        }
        if (!recipient)
        {
            newError.user = "Recipient is required!";
        }
        return newError;
    }

    const handleSummit = () => {

        const newError = validateForm();
        if (Object.keys(newError).length > 0)
        {
            setErrorForm(newError);
        }
        else
        {
            if (recipient === undefined) return;
            const newMail: NewMail =
            {
                text: message,
                receiverId: recipient.id,
                templateId: template?.id as number
            }
            axios.post<NewMail>("/api/mails/send", newMail)
                .then((response) => {
                    setNewMail(response.data);
                    navigateTo(-1);
                })
                .catch((error) => {
                    console.error(error);
                });
        }
    }

    const handleUserSelected = (id : string) =>
    {
        const selectedUser = appUsers.find((appUser) => appUser.id.toString() === id);
                if (selectedUser)
                {
                    setRecipient(selectedUser);
                    setToggleUser(!toggleUser)
                }
    }

    const handTemplateSelected = (id : number) =>
    {
        const selectedTemplate = templates.find((template) => template.id === id);
        if (selectedTemplate)
        {
            setTemplate(selectedTemplate);
            setToggleTemplate(!toggleTemplate)
        }
    }

    useEffect(() => {
        axios.get<AppUserDTO[]>("/api/user/get-users")
            .then((response) =>
            {
                setAppUsers(response.data);
            });
    }, []);

    useEffect(() => {
        axios.get<TemplateModel[]>("/api/templates/my" )
            .then((response) =>
            {
                setTemplates(response.data);
            });
    }, []);

    useEffect(() => {
        // Only attempt to set the template if templateId is provided
        if (templateId) {
            const selectedTemplate = templates.find(template => template.id.toString() === templateId);
            if (selectedTemplate) {
                setTemplate(selectedTemplate);
            } else {
                // If no template matches the id, fetch it from the server
                axios.get<TemplateModel>(`/api/templates/${templateId}`)
                    .then(response => {
                        setTemplate(response.data);
                    })
                    .catch(error => {
                        // Handle the error appropriately
                        console.error('Error fetching the template:', error);
                        // You might want to set some error state here
                    });
            }
        }
    }, [templateId, templates]);

    useEffect(() => {
        if(recipientId)
        {
            axios.get<AppUserDTO>(`/api/user/${recipientId}`)
                .then((response) =>
                {
                    setRecipient(response.data);
                });
        }
    }, [recipientId]);

return (
<>
    <Controller title={"New Mail"} showBackButton/>
    <Toolbar/>
    <Toolbar/>
    <Paper elevation={DEFAULT_ELEVATION} sx={{py : 2, px : 1, mb :2 }}>
        <TextField
            value={message}
            onChange={(event) =>
            {
                setMessage(event.target.value);
            }}
            fullWidth
            multiline
            rows={4}
            label={"Write Your Message"}
            autoFocus={true}
            error={!!errorForm.message}
            helperText={errorForm.message}
        />

        <Button onClick={handleSummit}>
            Send
        </Button>
    </Paper>

    <Paper elevation={DEFAULT_ELEVATION} sx={{py : 2, px : 1, mb :2 }}>
        <Typography align={"left"}>
            Send to :
        </Typography>
        {recipient ?
            (
                <AppUserShortCard
                    key={recipient.id + "recipient"}
                    appUser={recipient}
                    buttons={[
                            {
                                onClick: () => {},
                                startIcon: <AddIcon/>,
                                label: "Remove",
                                color: "primary"
                            }
                    ]}/>
            )
                :
            (
                <>
                    <Typography>
                        No recipient selected!
                    </Typography>
                    {errorForm.user && <Typography color={"error"}>{errorForm.user}</Typography>}
                </>

            )
        }

        <Divider variant={"middle"} color={"primary"} sx={{my : 2}}/>

        {toggleUser ?
            (
                <>
                <Button onClick={() => setToggleUser(!toggleUser)}>
                    Search Users
                </Button>
                </>
            )
                :
            (
                <>
                {appUsers?.map((appUser) => (
                    <AppUserShortCard
                        key={appUser.id + "have"}
                        appUser={appUser}
                        buttons={[
                            {
                                onClick: handleUserSelected,
                                startIcon: <AddIcon/>,
                                label: "Select",
                                color: "primary"
                            }
                        ]}
                    />
                ))}
                </>
            )
        }
    </Paper>

    <Paper elevation={DEFAULT_ELEVATION} sx={{py : 2, px : 1}}>
        <Typography align={"left"}>
            Template :
        </Typography>
        {template ?
            (
               <Template templateModel={template}/>
            )
                :
            (
                <Typography>
                    No template selected!
                </Typography>
            )
        }

        <Divider variant={"middle"} color={"primary"} sx={{my : 2}}/>

        {toggleTemplate ?
            (
                <>
                    <Button onClick={() => setToggleTemplate(!toggleTemplate)}>
                        Search Templates
                    </Button>
                </>
            )
            :
            (
                <>
                <Grid container spacing={1}>
                    {templates && templates.map((template) => (
                    <>

                            <Grid item xs={6}>
                                <Template templateModel={template}/>
                                <Button onClick={() => handTemplateSelected(template.id)}>
                                    Choose
                                </Button>
                            </Grid>
                    </>)
                    )}
                </Grid>
                </>
            )
        }
    </Paper>
</>
    );
}