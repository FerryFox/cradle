import {Button, Paper, Stack} from "@mui/material";

import Box from "@mui/material/Box";
import React from "react";
import {DEFAULT_ELEVATION} from "../../globalConfig";

export default function RedeemButton()
{
return (
    <>
        <Paper elevation={DEFAULT_ELEVATION} sx={{px : 2, py : 2 }}>
            <Stack>
                <Button variant="contained" color="primary">
                    Collect your prize!
                </Button>
                <Box sx={{
                    backgroundImage: `url(https://images.nightcafe.studio/jobs/YMAEkGzjgnSxbRzi2zSu/YMAEkGzjgnSxbRzi2zSu--1--1dugm.jpg?tr=w-1600,c-at_max)`,
                    backgroundPosition: 'center',
                    backgroundSize: 'cover',
                    backgroundRepeat: 'no-repeat',
                    height: "60vh",
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center'}}>
                </Box>
            </Stack>
        </Paper>

    </>

    );
}