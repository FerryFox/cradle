import {Button, Paper, Stack} from "@mui/material";
import Box from "@mui/material/Box";
import React from "react";
import {DEFAULT_ELEVATION} from "../../globalConfig";

type RedeemButtonProps = {
    redeemCard: () => void;
};

export default function RedeemButton({ redeemCard }: RedeemButtonProps)
{

    return (
    <>
        <Paper elevation={DEFAULT_ELEVATION} sx={{px : 2, py : 2 }}>
            <Stack>
                <Button onClick={redeemCard}
                    variant="contained"
                    color="primary">

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
    </>);
}