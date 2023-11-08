import Typography from "@mui/material/Typography";
import React from "react";
import {Mail} from "./model/models";
import Controller from "../core/Controller";
import {Paper, Stack} from "@mui/material";
import AppUserShortCard from "../user/AppUserShortCard";
import Container from "@mui/material/Container";
import {DEFAULT_ELEVATION} from "../../globalConfig";
import Template from "../template/Template";


type NotReadMailsProps = {
    mail: Mail[];
}
export default function RedeemNotice( {mail}: NotReadMailsProps)
{

return (
    <div>
    <Controller title={"Redeem Notice"} />
        <Container>

        {mail?.length === 0 && (
        <Typography>
            You have no redeem notice.
        </Typography>
        )}

        {mail?.map((mail) => (
        <>
            <Paper elevation={DEFAULT_ELEVATION} sx={{py : 2, px :2, my :5}}>
                <Stack spacing={2}>
                    <Typography variant="h6" color={"secondary"} sx={{ mb: 5 }}>
                        {mail.conversation[0].text}
                    </Typography>
                    <Template templateModel={mail.templateResponse} />
                    <AppUserShortCard appUser={mail.sender} />
                </Stack>
            </Paper>
        </>
        ))}
        </Container>
    </div>
);
}