import React, {useState} from "react";
import {Mail, MessageDTO} from "./model/models";
import {IconButton, Paper, Stack, useTheme} from "@mui/material";
import Grid from "@mui/material/Grid";
import AppUserShortCard from "../user/AppUserShortCard";
import {DEFAULT_ELEVATION} from "../../globalConfig";
import Typography from "@mui/material/Typography";
import TextField from "@mui/material/TextField";
import SendIcon from "@mui/icons-material/Send";

type SendMailsProps = {
    mails : Mail[];
    handleResponse: (id: number, message : MessageDTO) => void;
    handleReadClick?: (mail: Mail) => void;
    handleDeleteClick: (mail: Mail) => void;

}

export default function SendMails({ mails, handleResponse, handleDeleteClick, handleReadClick }: SendMailsProps) {
    const theme = useTheme();
    const [text, setText] = useState<string>("");

    const sendThis = (id: number, text: string) => {
        const message: MessageDTO = {
            text: text,
            originalSender: true
        };
        handleResponse( id, message);
    };

    return (
        <div>
            {mails.map((mail) => (
                <div key={mail.id}>
                    <Paper elevation={6} sx={{ mb: 4, mt: 5, py: 2 }}>
                        <Stack alignItems="center" sx={{ px: 1, py: 1 }}>
                            <Grid container spacing={2}>
                                <Grid item xs={12}>
                                    <AppUserShortCard appUser={mail.receiver} />
                                </Grid>
                                <Grid item xs={12}>
                                    <Stack spacing={2}>
                                        {mail?.conversation.map((conversation) => (
                                            <div key={conversation.id + "sender"}
                                                 style={{
                                                     display: 'flex',
                                                     justifyContent: conversation.senderMassage ? 'flex-end' : 'flex-start', // Right if sender, left if not
                                                 }}>
                                                <Paper elevation={DEFAULT_ELEVATION}
                                                       sx={{
                                                           bgcolor: !conversation.senderMassage ? theme.palette.grey[500] : theme.palette.primary.main,
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
                                                sendThis(mail.id, text);
                                                setText("");
                                            }}>
                                                <SendIcon />
                                            </IconButton>
                                        </Paper>
                                    </Stack>
                                </Grid>
                            </Grid>
                        </Stack>
                    </Paper>
                </div>
            ))}
        </div>
    );
}