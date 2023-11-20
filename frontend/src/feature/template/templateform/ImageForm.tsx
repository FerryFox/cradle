import React, {ChangeEvent, useState} from "react";
import Typography from "@mui/material/Typography";
import {Button, Divider, FormControl} from "@mui/material";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";

type Props = {
    onImageChange: () => void;
    stepBack: () => void;

}
export default function ImageFrom( {onImageChange, stepBack} : Props)
{
    const [imageSrc, setImageSrc] = useState<string | null>(null);

    const handleImageChange = (event :ChangeEvent<HTMLInputElement>) => {
        if (!event.target.files) return;
        const file = event.target.files[0];

        if (file) {
            const reader = new FileReader();

            reader.onload = function(event: ProgressEvent<FileReader>) {
                if (typeof event.target!.result === 'string') {
                    setImageSrc(event.target!.result);
                }
            };
            reader.readAsDataURL(file);
        }
    }

    const [isShaking, setIsShaking] = React.useState<boolean>(false);
    const handleSubmit = (e : React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        onImageChange();
    }

    const handleShake = () => {
        setIsShaking(true);
        setTimeout(() => {
            setIsShaking(false);
        }, 820);  // match the duration of the shake animation
    };

return (
<>
    <Box className={isShaking ? 'shake' : ''} component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 2}}>
        <FormControl fullWidth sx={{ marginBottom: 2 }}>
            <input
                accept="image/*"
                style={{ display: 'none' }}
                id="contained-button-file"
                type="file"
                onChange={handleImageChange}
            />
            <label htmlFor="contained-button-file">
                <Button variant="contained" color={"primary"} component="span">
                    Upload Image
                </Button>
            </label>

            {imageSrc && (
                <Box
                    sx={{
                        display: 'flex',
                        justifyContent: 'center',
                        mt: 2,
                        height: 340,
                        backgroundImage: `url(${imageSrc})`,
                        backgroundSize: 'cover',
                        backgroundPosition: 'center'
                    }}
                ></Box>
            )}
        </FormControl>
        <Divider color={"primary"} sx={{ my : 2 }}/>

        <Grid container>
            <Grid item xs={6}>
                <Button onClick={() => stepBack()}>
                    Back
                </Button>
            </Grid>
            <Grid item xs={6}>
                <Button type={"submit"}>
                    Submit
                </Button>
            </Grid>
        </Grid>
    </Box>
</>
);
}