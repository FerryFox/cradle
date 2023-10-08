import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import {useState, useEffect} from "react";
import Button from "@mui/material/Button"; // <-- Import useEffect

function StampCardExtraInfo({ stampCardModel, message }) {
    const [stampsLeft, setStampsLeft] = useState(calculateStampsLeft());

    function calculateStampsLeft() {
        let initialStamps = stampCardModel.stampFields.length;
        stampCardModel.stampFields.forEach(stampField => {
            if (stampField.stamped) {
                initialStamps--;
            }
        });
        return initialStamps;
    }

    // useEffect to watch for changes in stampCardModel and update stampsLeft accordingly
    useEffect(() => {
        setStampsLeft(calculateStampsLeft());
    }, [stampCardModel]);

    return (
        <Box border={1} borderColor="red">
            <Typography variant={"body2"}>
                Test extra info
            </Typography>

            <Typography variant={"body2"}>
                {stampsLeft}
            </Typography>

            <Typography variant={"body2"}>
                {message}
            </Typography>


            {stampsLeft === 0 &&
                (
                    <Button variant={"contained"}>
                        Redeem!
                    </Button>
                )

            }
            <Button >

            </Button>
        </Box>
    );
}

export default StampCardExtraInfo;
