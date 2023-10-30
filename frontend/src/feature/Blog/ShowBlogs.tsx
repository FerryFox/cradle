import React from "react";
import {BlogDTO} from "./model/BlogDTO";
import {Button, CardActions, CardHeader, Divider, IconButton, Stack} from "@mui/material";
import AppUserShortCard from "../user/AppUserShortCard";
import Card from "@mui/material/Card";
import CardMedia from "@mui/material/CardMedia";
import CardContent from "@mui/material/CardContent";
import Typography from "@mui/material/Typography";
import Avatar from "@mui/material/Avatar";
import ChangeCircleIcon from "@mui/icons-material/ChangeCircle";
import PermContactCalendarIcon from "@mui/icons-material/PermContactCalendar";
import {useNavigate} from "react-router-dom";
import Grid from "@mui/material/Grid";

type ShowBlogsProps = {
    blogs: BlogDTO[];
}



export default function ShowBlogs( {blogs} : ShowBlogsProps)
{
    const date = new Date();
    const today = date.toLocaleDateString();
    const navigateTo = useNavigate();

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
                        <Typography variant={"h5"} align={"left"} sx={{px : 2 , py : 1}}>
                            {blog.title}
                        </Typography>

                        <Grid container sx={{px : 2}}>
                            <Grid item xs={6}>
                                <CardMedia >
                                    <img src={blog.pictureBase64} alt="" />
                                </CardMedia>
                            </Grid>
                            <Grid item xs={6}>
                                <Typography variant={"body2"}>
                                    {blog.content}
                                </Typography>
                            </Grid>
                        </Grid>

                       <CardContent>

                           <Typography align={"right"}>
                               {today}
                           </Typography>
                           <Divider></Divider>
                           <CardActions>
                               <Button variant={"contained"}> one </Button>
                               <Button variant={"contained"}> two </Button>
                           </CardActions>

                       </CardContent>

                    </Card>
                </div>
            ))}
        </Stack>
    )
}