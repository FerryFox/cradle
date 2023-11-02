import {Mail, MessageDTO} from "./model/models";
import {Button, ButtonGroup, Divider, IconButton, Paper, Stack, useTheme} from "@mui/material";
import Grid from "@mui/material/Grid";
import AppUserShortCard from "../user/AppUserShortCard";
import {DEFAULT_ELEVATION} from "../../globalConfig";
import Typography from "@mui/material/Typography";
import Template from "../template/Template";
import React, {useState} from "react";
import DeleteForeverIcon from '@mui/icons-material/DeleteForever';
import ReplayIcon from '@mui/icons-material/Replay';
import TaskIcon from '@mui/icons-material/Task';
import TextField from "@mui/material/TextField";
import SendIcon from '@mui/icons-material/Send';

type NotReadMailsProps = {
    handleGetCardClick: ( templateId: number, clickedMail: Mail) => void;
    handleReadClick: (mail: Mail) => void;
    handleDeleteClick: (mail: Mail) => void;
    handleResponse: (id: number, message : MessageDTO) => void;
    notRead : Mail[];
}

type FormErrors = {
    text?: string;
}

export default function NotReadMails({ notRead ,
                                         handleGetCardClick,
                                         handleReadClick,
                                         handleDeleteClick,
                                         handleResponse}: NotReadMailsProps)
{
    const theme = useTheme();
    const [text, setText] = useState<string>("");

return (
    <div>
        {notRead.map((mail) => {
            // Wrapper function that captures the specific 'mail'
            const handleThisMailClick = (templateId: number) => {
                handleGetCardClick(templateId, mail);
            };

            const validate = (text: string): FormErrors => {
                let errors: FormErrors = {};

                // Check for a non-empty string
                if (!text.trim()) {
                    errors.text = "Text is required!";
                }
                // Check if the text is greater than or equal to 200 characters
                else if (text.length >= 200) {
                    errors.text = "Text must be fewer than 200 characters!";
                }

                return errors;
            };

            const sendResponse = (id: number, newText : string) =>
            {
                const errors = validate(newText);
                if (Object.keys(errors).length !== 0) {
                    return;
                }

                const message : MessageDTO = {
                    text : newText,
                    originalSender : false
                };

                handleResponse(id, message);
            }

            return (
                <div key={mail.id}>
                <Paper elevation={6} sx={{mb : 4, mt : 2, py : 1 }}>
                    <Stack alignContent={"center"} sx={{px : 1, py : 1}} >
                        <Grid container spacing={2}>
                            <Grid item xs={12}>
                                <AppUserShortCard appUser={mail.sender}/>
                            </Grid>
                            <Grid item xs={12}>
                                <Stack spacing={2}>
                                    {mail?.conversation.map(conversation => (
                                            <>
                                                <Paper elevation={DEFAULT_ELEVATION} key={conversation.id}
                                                       sx={{
                                                           bgcolor: conversation.senderMassage ?
                                                               theme.palette.grey[500] :
                                                               theme.palette.primary.main }}>

                                                    <Typography variant={"body2"} sx={{py : 1, px :0}}  >
                                                        {conversation.text}
                                                    </Typography>
                                                </Paper>
                                                </>))}

                                    <Paper elevation={DEFAULT_ELEVATION}>
                                        <TextField
                                            id="outlined-multiline-static"
                                            label="Response"
                                            multiline
                                            rows={1}
                                            defaultValue=""
                                            variant="outlined"
                                            fullWidth
                                            value={text}
                                            onChange={(event) =>
                                                setText(event.target.value)}
                                        />
                                        <IconButton>
                                            <SendIcon onClick={() => {
                                                sendResponse(mail.id, text);
                                                setText("");
                                            }}/>
                                        </IconButton>
                                    </Paper>
                                </Stack>
                            </Grid>
                            <Grid item xs={6}>
                                {mail.templateResponse && (
                                    <Template templateModel={mail.templateResponse}
                                              getButton={handleThisMailClick} />
                                )}
                            </Grid>
                        </Grid>
                        <Divider sx={{mt : 2}} variant={"middle"} color={"warning"}></Divider>
                        <Grid item xs={12} sx={{pt : 2}}>
                            <ButtonGroup variant="contained" aria-label="outlined primary button group">
                                <Button startIcon={<ReplayIcon/>}>Replay</Button>
                                <Button startIcon={<TaskIcon/>} onClick={() => handleReadClick(mail)} >Mark as Read</Button>
                                <Button startIcon={<DeleteForeverIcon/>} onClick={() => handleDeleteClick(mail)} color={"secondary"}>Delete</Button>
                            </ButtonGroup>
                        </Grid>
                    </Stack>
                    </Paper>
                </div>
            );
        })}
    </div>
    )
}