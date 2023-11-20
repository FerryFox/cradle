import Typography from "@mui/material/Typography";
import React from "react";
import Grid from "@mui/material/Grid";
import {Button} from "@mui/material";

export default function SecurityForm() {
    return (
        <>
            <Typography>
                Security form
            </Typography>
            <Grid container>
                <Grid item xs={6} >
                    <Button variant={"contained"} color={"secondary"}>
                        Stop Creating
                    </Button>
                </Grid>
                <Grid item xs={6}>
                    <Button type={"submit"} variant={"contained"}>
                        Next
                    </Button>
                </Grid>
            </Grid>
        </>
    );
}