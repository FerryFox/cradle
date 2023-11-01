import React from "react"
import {Mail} from "./model/models";
import {Paper, Stack} from "@mui/material";
import Grid from "@mui/material/Grid";
import AppUserShortCard from "../user/AppUserShortCard";
import {DEFAULT_ELEVATION} from "../../globalConfig";
import Typography from "@mui/material/Typography";
import Template from "../template/Template";

type OldMailsProps = {
    oldMails: Mail[];
}

export default function OldMails({ oldMails }: OldMailsProps) {  // Destructure the props here
    return (
        <div>
            {oldMails.map((mail) => (
                <div key={mail.id}>
                    <Stack alignContent={"center"} sx={{px : 1, py : 1}} >
                        <Grid container spacing={2}>
                            <Grid item xs={12}>
                                <AppUserShortCard appUser={mail.sender}/>
                            </Grid>
                            <Grid item xs={6}>
                                <Paper elevation={DEFAULT_ELEVATION}>
                                    <Typography variant={"body2"}>
                                        {mail.text}
                                    </Typography>
                                </Paper>
                            </Grid>
                            <Grid item xs={6}>
                                {mail.templateResponse && (
                                    <Template templateModel={mail.templateResponse} />
                                )}
                            </Grid>
                        </Grid>
                    </Stack>
                </div>
            ))}
        </div>
    )
}
