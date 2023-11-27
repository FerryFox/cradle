import Typography from "@mui/material/Typography";
import React from "react";
import {Button, Divider} from "@mui/material";

type Props = {
    stepBack: () => void;
    handleSubmit : () => void;};


export default function TemplatePreview( {stepBack, handleSubmit} : Props) {
    return (
        <div>

            <Typography variant={"h6"}>
                Template Preview
            </Typography>


            <Divider color={"primary"} sx={{ my : 2 }}/>
            <Button variant={"text"} onClick={stepBack}>
                Back
            </Button>
            <Button variant={"text"} onClick={handleSubmit}>
                Submit
            </Button>
        </div>
    );
}