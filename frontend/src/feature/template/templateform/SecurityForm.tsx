import Typography from "@mui/material/Typography";
import React from "react";
import Grid from "@mui/material/Grid";
import {Button} from "@mui/material";
import Box from "@mui/material/Box";

type Props = {
    stepBack: () => void;
    handleNext : () => void;
};


export default function SecurityForm( {stepBack, handleNext} : Props) {
    const [isShaking, setIsShaking] = React.useState<boolean>(false);
    const handleShake = () => {
        setIsShaking(true);
        setTimeout(() => {
            setIsShaking(false);
        }, 820);  // match the duration of the shake animation
    };

    const onSubmit = () => {
        handleNext();
    }

    return (
        <Box className={isShaking ? 'shake' : ''} component="form" onSubmit={handleNext} noValidate sx={{mt: 2}}>
            <Typography>
                Security form
            </Typography>
            <Grid container>
                <Grid item xs={6} >
                    <Button variant={"contained"} color={"primary"} onClick={stepBack}>
                        Back
                    </Button>
                </Grid>
                <Grid item xs={6}>
                    <Button type={"submit"} variant={"contained"} onClick={handleNext}>
                        Next
                    </Button>
                </Grid>
            </Grid>
        </Box>
    );
}