import React, {useEffect, useState} from "react";
import Controller from "../core/Controller";
import {Paper, Tab, Tabs, Toolbar} from "@mui/material";
import Typography from "@mui/material/Typography";
import axios from "axios";
import {Mail, MailText, MessageDTO} from "./model/models";
import {createStampCardFromTemplateId} from "../../assets/service/stampCardService";
import NotReadMails from "./NotReadMails";
import SendMails from "./SendMails";
import {DEFAULT_ELEVATION} from "../../globalConfig";
import {useNavigate} from "react-router-dom";

export default function MailPage() {

    const [mails, setMails] = useState<Mail[]>([]);
    const [tabValue, setTabValue] = React.useState('tab1');
    const [read, setRead] = useState<Mail[]>([]);
    const [notRead, setNotRead] = useState<Mail[]>([]);
    const [sendMails, setSendMails] = useState<Mail[]>([]);
    const navigateTo = useNavigate();

    const handleTapChange = (event: React.SyntheticEvent, newValue: string) => {
        setTabValue(newValue);
    };

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


    useEffect(() => {
        axios.get<Mail[]>("/api/mails/your-send-mails").then((response) =>
        setSendMails(response.data));
    }, []);

    const handleReadClick = (clickedMail: Mail) => {
        axios.post("/api/mails/mark-as-read/" + clickedMail.id).then(
            () => {
                const updatedNotRead = notRead.filter(mail => mail.id !== clickedMail.id);
                const updatedRead = [...read, clickedMail];
                setNotRead(updatedNotRead);
                setRead(updatedRead);
            }
        );
    }

    const handleDeleteClick = (clickedMail: Mail) => {
        axios.delete("/api/mails/delete/" + clickedMail.id).then(
            () => {
                const updatedNotRead = notRead.filter(mail => mail.id !== clickedMail.id);
                const updatedRead = read.filter(mail => mail.id !== clickedMail.id);
                setNotRead(updatedNotRead);
                setRead(updatedRead);
            }
        );
    }

    const handleOriginalSenderDelete = (clickedMail : Mail) => {
        axios.delete("/api/mails/delete-originator/" + clickedMail.id).then(
            () => {
                const updatedSendMails = sendMails.filter(mail => mail.id !== clickedMail.id);
                setSendMails(updatedSendMails);
            }
        );
    }

    const handleResponse = (mailId: number, messageDTO: MessageDTO) => {
        axios.post<MailText[]>("/api/mails/respond/" + mailId.toString(), messageDTO, {

        }).then(response => {
            const updatedConversation = response.data;
            const updatedMails = mails.map(mail => {
                // Use the spread operator to avoid mutating the original mail object
                if (mail.id === mailId) {
                    return {
                        ...mail,
                        conversation: updatedConversation,
                    };
                }
                return mail;
            });

            // Update the state with the new array of mails
            setMails(updatedMails);

            // If you need to update read and notRead as well, follow a similar approach:
            setRead(currentRead => currentRead.map(mail => {
                if (mail.id === mailId) {
                    return {
                        ...mail,
                        conversation: updatedConversation,
                    };
                }
                return mail;
            }));

            setNotRead(currentNotRead => currentNotRead.map(mail => {
                if (mail.id === mailId) {
                    return {
                        ...mail,
                        conversation: updatedConversation,
                    };
                }
                return mail;
            }));

            setSendMails(currentSendMails => currentSendMails.map(mail => {
                if (mail.id === mailId) {
                    return {
                        ...mail,
                        conversation: updatedConversation,
                    };
                }
                return mail;
            }));

        }).catch(error => {
            // Handle error
            console.error("Error responding to mail:", error);
        });
    };


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

            <Tabs
                value={tabValue}
                onChange={handleTapChange}
                textColor="secondary"
                variant="fullWidth"
                sx={{ py: 2 }}
                indicatorColor="secondary">

                <Tab value="tab1" label={`Recent (${notRead.length})`} />
                <Tab value="tab2" label={`Old  (${read.length})`}/>
                <Tab value="tab3" label={`Send (${sendMails.length})`}/>
            </Tabs>


            <Paper elevation={DEFAULT_ELEVATION} sx={{py : 2, mx : 0, mb : 2}}>
                <Typography onClick={() => navigateTo("/new-mail/")}>
                    Write a new mail
                </Typography>
            </Paper>

            {tabValue === 'tab1' &&
                <>
                    <NotReadMails notRead={notRead}
                                  handleReadClick={handleReadClick}
                                  handleGetCardClick={handleGetCardClick}
                                  handleDeleteClick={handleDeleteClick}
                                  handleResponse={handleResponse}
                                 />
                </> }
            {tabValue === 'tab2' &&
                <>
                    <NotReadMails notRead={read}
                              handleDeleteClick={handleDeleteClick}
                              handleResponse={handleResponse}
                              handleGetCardClick={handleGetCardClick}
                    />
                </>}
            {tabValue === 'tab3' &&
                <>
                    <SendMails
                        mails={sendMails}
                        handleResponse={handleResponse}
                        handleDeleteClick={handleOriginalSenderDelete}
                    />

                </>
            }
        </>
    );
}
