import React, {ChangeEvent, useRef, useState} from "react";
import {BlogDTO} from "./model/BlogDTO";
import {DEFAULT_ELEVATION} from "../../globalConfig";

import {Button, CardActions} from "@mui/material";
import {resizeAndCropImage} from "../../assets/picture/resizeAndCropImage";
import TextField from "@mui/material/TextField";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import Grid from "@mui/material/Grid";


type BlogFormProps = {
    blog?: BlogDTO;
    onSubmit: (newBlog : BlogDTO) => void;
};

const defaultBlog: BlogDTO = {
    title: "",
    content: "",
    createdDate: new Date(),
    pictureBase64: "",
};

type FormErrors = {
    title?: string;
    content?: string;
}


export default function BlogForm({blog, onSubmit}: BlogFormProps)
{
    const [newBlog, setNewBlog] = useState<BlogDTO>(blog?? defaultBlog);
    const fileInputRef = useRef<HTMLInputElement>(null);
    const [formErrors, setFormErrors] = useState<FormErrors>({});


    const handleImageChange = (event: ChangeEvent<HTMLInputElement>) => {
        if (!event.target.files) return;
        const file = event.target.files[0];

        if (file) {
            const reader = new FileReader();

            reader.onload = async function (event: ProgressEvent<FileReader>) {
                if (typeof event.target!.result === 'string') {
                    const resizedImageUrl = await resizeAndCropImage(file, 400, 300);
                    setNewBlog(prevState => {
                        const blogPic = { ...prevState, pictureBase64: resizedImageUrl };
                        return blogPic;
                    });
                }
            }
            reader.readAsDataURL(file);
        }
    }

    const validateFormData = () => {
        let errors: FormErrors = {};

        if (!newBlog.title || newBlog.title.length > 30) {
            errors.title = 'Title is required and should not be longer than 30 characters.';
        }

        if (!newBlog.content || newBlog.content.length > 6201) {
            errors.content = 'Content is required and should not be longer than 500 characters.';
        }

        setFormErrors(errors);

        return errors;
    };

    function handleSubmit(newBlog: BlogDTO)
    {
        const errors = validateFormData();
        setFormErrors(errors);

        if (Object.keys(errors).length > 0) {
            return;
        }

        onSubmit(newBlog);
    }

    return (
<div>
    <Card elevation={DEFAULT_ELEVATION} sx={{mt : 1 , py : 1, px : 2 }}>
        <CardContent>
            <Grid container spacing={1} alignItems="center" justifyContent="center">
                <Grid item xs={12}>
                    <TextField
                        fullWidth
                        onChange={(e) =>
                        {setNewBlog(prevState => ({...prevState, title: e.target.value}))}}
                        value={newBlog.title}
                        label="Story Title"
                        error={!!formErrors.title}
                        helperText={formErrors.title}/>
                </Grid>
                <Grid item xs={12}>
                    <div
                        style={{
                            width: '100%',
                            height: 'auto',
                            overflow: 'hidden',
                            display: 'flex',
                            justifyContent: 'center',
                            alignItems: 'center',
                            border: '2px dashed gray', // Optional: Makes it look like a drag & drop area
                        }}
                        onClick={() => fileInputRef.current?.click()}>

                        {newBlog.pictureBase64 ? (
                            <img
                                style={{ width: "100%", height: '100%' }}
                                src={newBlog.pictureBase64}
                            />
                        ) : (
                            <Button style={{ padding: '10px 20px' }}>
                                Upload Image
                            </Button>
                        )}
                        <input
                            ref={fileInputRef}
                            type="file"
                            style={{ display: 'none' }}
                            onChange={handleImageChange}
                        />
                    </div>
                </Grid>
                <Grid item xs={12} >
                    <TextField
                        onChange={(e) =>
                        {setNewBlog(prevState => ({...prevState, content: e.target.value}))}}
                        value={newBlog.content}
                        label="Write your Story"
                        multiline
                        rows={4}
                        fullWidth
                        variant="standard"
                        error={!!formErrors.content}
                        helperText={formErrors.content}/>
                </Grid>
            </Grid>
        </CardContent>
        <CardActions>
            <Button variant={"text"} onClick={() => handleSubmit(newBlog)}>
                Submit
            </Button>
        </CardActions>
    </Card>
</div>
)
}