import React, {useEffect, useState} from "react";
import Controller from "../core/Controller";
import Container from "@mui/material/Container";
import {Button, Paper, Toolbar} from "@mui/material";
import Grid from "@mui/material/Grid";
import {useNavigate} from "react-router-dom";
import axios from "axios";
import {AppUserDTO} from "../user/model/models";
import Typography from "@mui/material/Typography";
import {TemplateModel} from "../template/model/models";
import Template from "../template/Template";
import AddInfoReadOnly from "../user/AddInfoReadOnly";
import {BlogDTO} from "../Blog/model/BlogDTO";
import ShowBlogs from "../Blog/ShowBlogs";


export default function Dashboard()
{
    const navigateTo = useNavigate();
    const token = localStorage.getItem("authToken");
    axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;

    const [appUser, setAppUser] = useState<AppUserDTO>();
    useEffect(() => {
        axios.get<AppUserDTO>("/api/user/me").then((response) => {
            setAppUser(response.data);
        });
    }, []);

    const [templates, setTemplateds] = useState<TemplateModel[]>([]);
    useEffect(() => {
        axios.get<TemplateModel[]>("/api/templates/latest-two").then((response) => {
            setTemplateds(response.data);
        });
    }, []);

    const [latestUser, setLatestUser] = useState<AppUserDTO>( {} as AppUserDTO);
    useEffect(() => {
        axios.get<AppUserDTO>("/api/user/latest-user").then((response) => {
            setLatestUser(response.data);
        });
    }, []);

    const [latestBlog, setLatestBlog] = useState<BlogDTO[]>( []);
    useEffect(() => {
        axios.get<BlogDTO[]>("/api/blog/last").then((response) => {
            setLatestBlog(response.data);
        });
    }, []);

return (
<>
<Controller title="Dashboard"/>
    <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
        <Toolbar />
        <Grid container spacing={3} >
            <Grid item xs={12} >

                <Paper sx={{ p: 2 }} >
                    <Typography align={"left"}>
                        Hello
                        <Typography component="span" sx={{ color: 'secondary.main' }}> {appUser?.appUserName} </Typography>
                        Welcome to Stampede, the innovative app where your loyalty pays off! Whether you're a cafe-hopper, a boutique enthusiast, or a service seeker, we're delighted to have you join our community.
                    </Typography>
                    <Button onClick={() => navigateTo("/profile")}>
                        Advance your profile
                    </Button>
                </Paper>
            </Grid>

            <Grid item xs={12}>
                <Paper sx={{ p: 2 }}>
                    <Typography variant={"body2"}>
                        See the newest cards below, or find more<Button onClick={() => navigateTo("/templates")}>
                        here
                    </Button>
                    </Typography>
                </Paper>
            </Grid>

            {templates.map((template) => (
                <Grid item xs={6} key={template.id}>
                    <Template key={template.id + "dash"} templateModel={template}/>
                </Grid>
            ))}



            <Grid item xs={12}>

                <Paper sx={{ p: 2 }} onClick={() => navigateTo("/template/form")}>
                    <Typography>
                        Become inspired and create your own card
                    </Typography>

                </Paper>
            </Grid>

            <Grid item xs={12}>
                <Paper sx={{ py: 2, my :1 }}>
                    <Typography>
                        See the latest Community Member, or find more<Button onClick={() => navigateTo("/friends")}>Here</Button>
                    </Typography>
                </Paper>
                {latestUser.addInfoDTO &&
                    <div onClick={() => navigateTo("/appuser/" + latestUser.id)}>
                        <AddInfoReadOnly userInfo={latestUser.addInfoDTO}/>
                    </div>
                  }
            </Grid>

            <Grid item xs={12}>
                <Paper sx={{ p: 2 }} onClick={() => navigateTo("/blog")}>
                    <Typography>
                        See what other people are collecting
                    </Typography>
                </Paper>
                <ShowBlogs blogs={latestBlog}/>
            </Grid>
        </Grid>
    </Container>
</>
    );
}
