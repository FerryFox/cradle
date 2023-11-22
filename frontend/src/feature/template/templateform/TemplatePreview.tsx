import Typography from "@mui/material/Typography";
import React from "react";
import {Button} from "@mui/material";

type Props = {
    stepBack: () => void;
    handleSubmit : () => void;};


export default function TemplatePreview( {stepBack, handleSubmit} : Props) {
    return (
        <div>
            <Typography variant={"h6"}>
                Template Preview
            </Typography>
            <Button variant={"text"} onClick={stepBack}>
                Back
            </Button>
            <Button variant={"text"} onClick={handleSubmit}>
                Submit
            </Button>
        </div>
    );
}