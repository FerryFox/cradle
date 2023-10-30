import React, {ChangeEvent, useRef, useState} from "react";
import {BlogDTO} from "./model/BlogDTO";
import {DEFAULT_ELEVATION} from "../../globalConfig";
import Avatar from "@mui/material/Avatar";
import {Button, Paper, Stack} from "@mui/material";
import {resizeAndCropImage} from "../../assets/picture/resizeAndCropImage";
import TextField from "@mui/material/TextField";


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

export default function BlogForm({blog, onSubmit}: BlogFormProps)
{
    const [newBlog, setNewBlog] = useState<BlogDTO>(blog || defaultBlog);
    const fileInputRef = useRef<HTMLInputElement>(null);


    const handleImageChange = (event: ChangeEvent<HTMLInputElement>) => {
        if (!event.target.files) return;
        const file = event.target.files[0];

        if (file) {
            const reader = new FileReader();

            reader.onload = async function (event: ProgressEvent<FileReader>) {
                if (typeof event.target!.result === 'string') {
                    const resizedImageUrl = await resizeAndCropImage(file, 200, 200);
                    setNewBlog(prevState => {
                        const blogPic = { ...prevState, pictureBase64: resizedImageUrl };
                        return blogPic;
                    });
                }
            }
            reader.readAsDataURL(file);
        }
    }



return (
<div>
    <Paper elevation={DEFAULT_ELEVATION} sx={{mt : 3 , py : 2, px : 2 }}>
        <Stack spacing={2} alignItems={"center"}>
            <TextField
                onChange={(e) =>
                    {setNewBlog(prevState => ({...prevState, title: e.target.value}))}}
                value={newBlog.title}
                label="Title"/>


            <div onClick={() => fileInputRef.current?.click()}>
                <Avatar variant={"square"} src={newBlog.pictureBase64} sx={{ width: 200, height: 200 }}></Avatar>
                <input
                    ref={fileInputRef}
                    type="file"
                    style={{ display: 'none' }}
                    onChange={handleImageChange}
                />
            </div>

            <TextField
                onChange={(e) =>
                    {setNewBlog(prevState => ({...prevState, content: e.target.value}))}}
                value={newBlog.content}
                label="Content"
                multiline
                rows={4}
                fullWidth
                variant="filled"/>

            <Button onClick={() => onSubmit(newBlog)}>
                Submit
            </Button>
        </Stack>
    </Paper>
</div>
)
}