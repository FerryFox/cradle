import React, {ChangeEvent, useRef, useState} from "react";
import {Button, Divider, FormControl, Stack} from "@mui/material";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import 'react-image-crop/dist/ReactCrop.css';
import ReactCrop, { type Crop } from 'react-image-crop'
import Typography from "@mui/material/Typography";

type Props = {
    oldImage: string;
    onImageChange: (croppedImage: string) => void;
    stepBack: () => void;
    handleNext : () => void;
};

export default function ImageForm({ onImageChange, stepBack, oldImage, handleNext }: Props) {
    const [crop, setCrop] = useState<Crop>({ height: 50, unit: '%', x: 15, y: 10, width: 50 });
    const [imageSrc, setImageSrc] = useState<string>(oldImage);
    const [originalImageSrc, setOriginalImageSrc] = useState<string>('');
    const imageRef = useRef<HTMLImageElement>(null);

    const [error, setError] = useState<string>('');

    const handleImageChange = (event: ChangeEvent<HTMLInputElement>) => {
        if (!event.target.files) return;
        const file = event.target.files[0];

        if (file) {
            const reader = new FileReader();

            reader.onload = function (event: ProgressEvent<FileReader>) {
                // Ensure that result is a string before calling setImageSrc
                if (typeof event.target!.result === 'string') {
                    const image = new Image();

                    image.onload = function () {
                        // @ts-ignore
                        setImageSrc(event.target!.result);
                        // @ts-ignore
                        setOriginalImageSrc(event.target!.result);
                    };

                    // Set the image source
                    image.src = event.target!.result;
                }
            };
            reader.readAsDataURL(file);
        }
    };

    const revertToOriginal = () => {
        setImageSrc(originalImageSrc);
    }

    const handleCropChange = (newCrop: Crop) => {
        setCrop({ ...newCrop, height: newCrop.width });
    };

    const handleCropButton = () => {
        // so this imageSrc is the image as string.
        console.log("Crop width : " + crop.width + " + Crop height : " +  crop.height + " at position x : " + crop.x + "and y : " + crop.y);

        //I need the image from the <ReactCrop> tag
        if (!imageRef.current || !crop.width || !crop.height) {
            setError("Nothing to crop selected")
            handleShake()
            return Promise.reject('Crop size not set');
        }

        console.log("original : "  +  imageRef.current.naturalWidth + "/" + imageRef.current.naturalHeight);

        const canvas = document.createElement('canvas');
        const scaleX = imageRef.current.naturalWidth / imageRef.current.width;
        const scaleY = imageRef.current.naturalHeight / imageRef.current.height;
        canvas.width = crop.width * scaleX;
        canvas.height = crop.height * scaleY;

        const ctx = canvas.getContext('2d');
        if (!ctx) {
            return Promise.reject('Canvas Context not found');
        }

        ctx.drawImage(
            imageRef.current,
            crop.x * scaleX,
            crop.y * scaleY,
            crop.width * scaleX,
            crop.height * scaleY,
            0,
            0,
            canvas.width,
            canvas.height
        );
        const base64Image = canvas.toDataURL('image/jpeg');

        checkIfImageIsApproximatelySquare(base64Image).then((isApproxSquare) => {
            if (isApproxSquare)
            {
                setError("");
                setImageSrc(base64Image);
                const NewTemplateImage = {
                    image: base64Image,
                    fileName: ""
                }
                onImageChange(base64Image);
            } else
            {
                setError("Image should be a square");
            }
        });
    };

    const [isShaking, setIsShaking] = React.useState<boolean>(false);
    const handleSubmit = () =>
    {
        checkIfImageIsApproximatelySquare(imageSrc).then((isApproxSquare) => {
            if (isApproxSquare) {
                handleNext();
            } else {
                setError("Image should be a square");
                handleShake();
            }
        }).catch((error) => {
            setError("No image selected");
            handleShake();

        });
    }

    function checkIfImageIsApproximatelySquare(dataUrl : string, tolerance = 0.1) {
        return new Promise((resolve, reject) => {
            const image = new Image();
            image.onload = () => {
                const aspectRatio = image.width / image.height;
                const isApproxSquare = Math.abs(aspectRatio - 1) <= tolerance;
                resolve(isApproxSquare);
            };
            image.onerror = reject;
            image.src = dataUrl;
        });
    }

    const handleShake = () => {
        setIsShaking(true);
        setTimeout(() => {
            setIsShaking(false);
        }, 820);  // match the duration of the shake animation
    };

    return (
        <Box className={isShaking ? 'shake' : ''} component="form" onSubmit={handleSubmit} noValidate sx={{mt: 2}}>
            <Typography variant={"h6"}>
                Upload an image, and crop it to a square
            </Typography>
            {error && (
                <Typography variant={"body2"} color={"error"}>
                    {error}
                </Typography>
            )}

            <FormControl fullWidth sx={{my: 2}}>
                <input
                    accept="image/*"
                    style={{display: 'none'}}
                    id="contained-button-file"
                    type="file"
                    onChange={handleImageChange}
                />
                <label htmlFor="contained-button-file">
                    <Button variant="contained" color="primary" component="span">
                        Upload Image
                    </Button>
                </label>

                {imageSrc && (
                    <Box sx={{mt : 2}}>
                        <Stack spacing={1}>
                        <ReactCrop crop={crop} onChange={c => handleCropChange(c)} >
                            <img src={imageSrc}
                                 ref={imageRef}
                                 style={{
                                    maxWidth: '100%',
                                    maxHeight: '100%',
                                    objectFit: 'contain',
                                    objectPosition: 'center'}}/>
                        </ReactCrop>

                        <Button variant={"contained"} onClick={handleCropButton}>
                            crop image
                        </Button>
                            {originalImageSrc && (
                                <Button variant={"contained"} onClick={revertToOriginal}>
                                    revert to original
                                </Button>
                            )}

                    </Stack>
                    </Box>
                )}
            </FormControl>

            <Divider color={"primary"} sx={{my: 2}}/>

            <Grid container>
                <Grid item xs={6}>
                    <Button variant={"contained"} onClick={() => stepBack()}>
                        Back
                    </Button>
                </Grid>
                <Grid item xs={6}>
                    <Button variant={"contained"} onClick={handleSubmit}>
                        Next
                    </Button>
                </Grid>
            </Grid>
        </Box>
);
}