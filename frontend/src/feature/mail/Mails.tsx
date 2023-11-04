import {Mail, MessageDTO} from "./model/models";
import {Button, ButtonGroup, Divider, IconButton, Paper, Stack, useTheme} from "@mui/material";
import Grid from "@mui/material/Grid";
import AppUserShortCard from "../user/AppUserShortCard";
import {DEFAULT_ELEVATION} from "../../globalConfig";
import Typography from "@mui/material/Typography";
import Template from "../template/Template";
import React, {useState} from "react";
import DeleteForeverIcon from '@mui/icons-material/DeleteForever';
import TaskIcon from '@mui/icons-material/Task';
import TextField from "@mui/material/TextField";
import SendIcon from '@mui/icons-material/Send';

type NotReadMailsProps = {
    handleGetCardClick: ( templateId: number, clickedMail: Mail) => void;
    handleReadClick?: (mail: Mail) => void;
    handleDeleteClick: (mail: Mail) => void;
    handleResponse: (id: number, message : MessageDTO) => void;
    notRead : Mail[];
}

type FormErrors = {
    text?: string;
}

export default function Mails({ notRead ,
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
                                    {mail?.conversation.map((conversation) => (
                                        <div key={conversation.id + "receiver"}
                                             style={{
                                                 display: 'flex',
                                                 justifyContent: !conversation.senderMassage ? 'flex-end' : 'flex-start', // Right if sender, left if not
                                             }}>
                                            <Paper elevation={DEFAULT_ELEVATION}
                                                   sx={{
                                                       bgcolor: conversation.senderMassage ? theme.palette.grey[500] : theme.palette.primary.main,
                                                       maxWidth: '70%',
                                                   }}>
                                                <Typography variant="body2" sx={{ py: 1, px: 2 }}>
                                                    {conversation.text}
                                                </Typography>
                                            </Paper>
                                        </div>
                                    ))}

                                    <Paper elevation={DEFAULT_ELEVATION} sx={{ display: 'flex', alignItems: 'center' }}>
                                        <TextField
                                            id={`outlined-multiline-static-${mail.id}`}
                                            label="Response"
                                            multiline
                                            rows={1}
                                            variant="outlined"
                                            fullWidth
                                            value={text}
                                            onChange={(event) => setText(event.target.value)}
                                        />
                                        <IconButton onClick={() => {
                                            sendResponse(mail.id, text);
                                            setText("");
                                        }}>
                                            <SendIcon />
                                        </IconButton>
                                    </Paper>
                                </Stack>
                            </Grid>

                            <Grid item xs={12}>
                                {mail.templateResponse && (
                                    <>
                                        <Divider variant={"middle"} color={"primary"} sx={{my : 2}}></Divider>
                                        <Template templateModel={mail.templateResponse}
                                                  getButton={handleThisMailClick} />
                                    </>

                                )}
                            </Grid>
                            <Grid item xs={12} sx={{pt : 2}}>
                                <ButtonGroup variant="contained" aria-label="outlined primary button group">
                                    {handleReadClick && (
                                        <Button startIcon={<TaskIcon/>} onClick={() => handleReadClick(mail)} >Mark as Read</Button>
                                    )
                                    }
                                    <Button startIcon={<DeleteForeverIcon/>} onClick={() => handleDeleteClick(mail)} color={"secondary"}>Delete</Button>
                                </ButtonGroup>
                            </Grid>
                        </Grid>

                    </Stack>
                    </Paper>
                </div>
            );
        })}
    </div>
    )
}