import {Mail} from "./model/models";
import {Button, ButtonGroup, Divider, Paper, Stack} from "@mui/material";
import Grid from "@mui/material/Grid";
import AppUserShortCard from "../user/AppUserShortCard";
import {DEFAULT_ELEVATION} from "../../globalConfig";
import Typography from "@mui/material/Typography";
import Template from "../template/Template";
import React from "react";
import DeleteForeverIcon from '@mui/icons-material/DeleteForever';
import ReplayIcon from '@mui/icons-material/Replay';
import TaskIcon from '@mui/icons-material/Task';
import axios from "axios";

type NotReadMailsProps = {
    handleGetCardClick: ( templateId: number, clickedMail: Mail) => void;
    handleReadClick: (mail: Mail) => void;
    handleDeleteClick: (mail: Mail) => void;
    notRead : Mail[];
}

export default function NotReadMails({ notRead ,handleGetCardClick, handleReadClick, handleDeleteClick}: NotReadMailsProps)
{


return (
    <div>
        {notRead.map((mail) => {
            // Wrapper function that captures the specific 'mail'
            const handleThisMailClick = (templateId: number) => {
                handleGetCardClick(templateId, mail);
            };

            return (
                <div key={mail.id}>
                <Paper elevation={6} sx={{mb : 4, mt : 2, py : 1 }}>
                    <Stack alignContent={"center"} sx={{px : 1, py : 1}} >
                        <Grid container spacing={2}>
                            <Grid item xs={12}>
                                <AppUserShortCard appUser={mail.sender}/>
                            </Grid>
                            <Grid item xs={6}>
                                <Paper elevation={DEFAULT_ELEVATION}>
                                    <Typography variant={"body2"} sx={{py : 1, px :0}} >
                                        {mail.text}
                                    </Typography>
                                </Paper>
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