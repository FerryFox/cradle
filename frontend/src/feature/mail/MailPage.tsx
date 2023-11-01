import React, {useEffect, useState} from "react";
import Controller from "../core/Controller";
import {Divider, Paper, Stack, Toolbar} from "@mui/material";
import Typography from "@mui/material/Typography";
import axios from "axios";
import {Mail} from "./model/models";
import {createStampCardFromTemplateId} from "../../assets/service/stampCardService";
import NotReadMails from "./NotReadMails";
import OldMails from "./OldMails";

export default function MailPage() {

    const [mails, setMails] = useState<Mail[]>([]);
    const [read, setRead] = useState<Mail[]>([]);
    const [notRead, setNotRead] = useState<Mail[]>([]);

    useEffect(() => {
        axios.get<Mail[]>("/api/mails/all").then((response) => {
            const fetchedMails = response.data;
            const readMails = fetchedMails.filter(mail => mail.read);
            const unreadMails = fetchedMails.filter(mail => !mail.read);

            setMails(fetchedMails);
            setRead(readMails);
            setNotRead(unreadMails);
        });
    }, []);

    const handleGetCardClick = async (templateId: number, clickedMail: Mail) => {
        try {
            await createStampCardFromTemplateId(templateId);

            const updatedNotRead = notRead.filter(mail => mail.id !== clickedMail.id);

            const updatedRead = [...read, clickedMail];

            setNotRead(updatedNotRead);
            setRead(updatedRead);

        } catch (error) {
            console.error("Error creating the stamp card:", error);
        }
    };

    return (
        <>
            <Controller title={"Mails"} showBackButton/>
            <Toolbar></Toolbar>
            <Toolbar></Toolbar>

            <Typography variant={"h3"}>
                New Mails
            </Typography>
            <Divider color={"primary"}></Divider>
            <NotReadMails  handleGetCardClick={handleGetCardClick} notRead={notRead}/>

            <Typography variant={"h3"}>
                Old Mails
            </Typography>
            <Divider color={"primary"}></Divider>
            <OldMails oldMails={read}/>
        </>
    );
}
