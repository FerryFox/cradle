import Typography from "@mui/material/Typography";
import React, {useState, useEffect} from "react";
import {Paper, Stack} from "@mui/material";
import {DEFAULT_ELEVATION} from "../../globalConfig";
import {StampCardModel} from "./model/models";

type StampCardExtraInfoProps = {
    stampCardModel: StampCardModel;
    message: string;
}

export default function StampCardExtraInfo({ stampCardModel, message } : StampCardExtraInfoProps) {
    const [stampsLeft, setStampsLeft] = useState(calculateStampsLeft());
    const [messageState, setMessageState] = useState(message || "Stamp this Card ");

    function calculateStampsLeft() {
        let initialStamps = stampCardModel.stampFields.length;
        stampCardModel.stampFields.forEach(stampField => {
            if (stampField.stamped) {
                initialStamps--;
            }
        });
        return initialStamps;
    }

    useEffect(() => {
        // If the card is full
        if(stampsLeft === 0) {
            setMessageState("This card is completed!");
        }
    }, [stampsLeft]);

    // Update the message state when the message prop changes
    useEffect(() => {
        if(message) {
            setMessageState(message);
        }
    }, [message]);

     // useEffect to watch for changes in stampCardModel and update stampsLeft accordingly
    useEffect(() => {
        setStampsLeft(calculateStampsLeft());
    }, [stampCardModel]);

return (
    <Stack spacing={2}>
        <Paper elevation={DEFAULT_ELEVATION} sx={{py : 1}}>
                <Typography variant={"body1"}>
                    Stamps left
                </Typography>
                <Typography variant={"h5"}>
                     {stampsLeft} / {stampCardModel.stampFields.length}
                </Typography>
        </Paper>

        <Paper elevation={DEFAULT_ELEVATION} sx={{py : 1 , px : 1}}>
            <Typography variant="body1">
                {messageState}
            </Typography>
        </Paper>

        <Paper elevation={DEFAULT_ELEVATION} sx={{py : 1}}>
            <Typography variant={"body1"}>
                {stampCardModel.templateModel.createdBy}
            </Typography>
        </Paper>
    </Stack>
    );
}

