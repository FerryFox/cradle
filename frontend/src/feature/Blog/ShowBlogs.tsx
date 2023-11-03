import React from "react";
import {BlogDTO} from "./model/BlogDTO";
import {Button, CardActions, CardHeader, Divider, IconButton, Stack} from "@mui/material";;
import Card from "@mui/material/Card";
import CardMedia from "@mui/material/CardMedia";
import CardContent from "@mui/material/CardContent";
import Typography from "@mui/material/Typography";
import Avatar from "@mui/material/Avatar";
import FavoriteIcon from '@mui/icons-material/Favorite';
import PermContactCalendarIcon from "@mui/icons-material/PermContactCalendar";
import {useNavigate} from "react-router-dom";
import Grid from "@mui/material/Grid";
import CommentIcon from '@mui/icons-material/Comment';

type ShowBlogsProps = {
    blogs: BlogDTO[];
}



export default function ShowBlogs( {blogs} : ShowBlogsProps)
{
    const navigateTo = useNavigate()

    const convertedDate = (date: Date) => {
        const dateObj = new Date(date);

        const formattedDate = dateObj.toLocaleDateString();
        const formattedTime = dateObj.toLocaleTimeString();

        const dateTimeDisplay = `${formattedTime} - ${formattedDate}  `;
        return dateTimeDisplay;
    }


    const date = new Date();
    const today = date.toLocaleDateString();


    return(
        <Stack spacing={2}>
            {blogs.map((blog) => (
                <div key={blog.id}>

                    <Card>
                        <CardHeader
                            avatar={
                                <Avatar src={blog.appUser?.addInfoDTO?.picture}  >
                                    {blog.appUser?.appUserName.charAt(0)}{blog.appUser?.appUserName.charAt(1)}
                                </Avatar>
                            }

                            action={
                                    <IconButton onClick={() => navigateTo(`/appuser/${blog.appUser?.id}`)}>
                                        <PermContactCalendarIcon/>
                                    </IconButton>

                            }
                            title={blog.appUser?.appUserName}
                            subheader={blog.appUser?.nameIdentifier.substring(0, 8) + " " +
                            blog.appUser?.addInfoDTO?.status ? (blog.appUser?.addInfoDTO?.status ) : ("") }
                        />
                        <Divider variant={"middle"}></Divider>
                        <Typography variant={"h5"} align={"center"} sx={{px : 2 , py : 1}}>
                            {blog.title}
                        </Typography>

                        <CardMedia
                            component="img"
                            sx={{maxWidth: '100%', maxHeight: 300, px: 2, py: 1}}
                            src={blog.pictureBase64}
                            alt="Blog Image"
                        />


                        <Typography variant={"body2"} sx={{px : 2}}>
                            {blog.content}
                        </Typography>


                       <CardContent>
                           <Typography align={"right"}>
                               {convertedDate(blog.createdDate)}
                           </Typography>
                           <Divider></Divider>
                           <CardActions>
                               <IconButton size={"small"}>
                                   <FavoriteIcon/>
                               </IconButton>
                               <IconButton>
                                   <CommentIcon/>
                               </IconButton>
                           </CardActions>
                       </CardContent>
                    </Card>
                </div>
            ))}
        </Stack>
    )
}