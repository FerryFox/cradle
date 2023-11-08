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
import Box from "@mui/material/Box";
import Card from "@mui/material/Card";
import {DEFAULT_ELEVATION} from "../../globalConfig";



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
        <Toolbar></Toolbar>

        <Card elevation={DEFAULT_ELEVATION} sx={{ mt : 0, mb : 4}}>
            <Box
                sx={{
                    width: '100%',
                    height: '15vh',
                    backgroundImage: `url('https://images.nightcafe.studio/jobs/GvuoWquMBm3xh7q84M3f/GvuoWquMBm3xh7q84M3f--4--k2jld.jpg?tr=w-1600,c-at_max')`, // replace with your image path
                    backgroundSize: 'cover',
                    backgroundPosition: 'center',
                }}>
            </Box>


                <Paper sx={{ p: 2 }}>
                    <Typography align={"left"}>
                        Hello
                        <Typography component="span" sx={{ color: 'secondary.main' }}> {appUser?.appUserName} </Typography>
                        Welcome to Stampede, the innovative app where your loyalty pays off!
                    </Typography>
                    <Button onClick={() => navigateTo("/profile")}>
                        Advance your profile
                    </Button>
                </Paper>
        </Card>

        <Grid container spacing={1} >
            <Grid item xs={12}>
                <Paper sx={{ pt: 2, pb : 2, backgroundColor: (theme) => theme.palette.primary.main,}}>
                    <Typography variant={"body2"}>
                        See the newest cards below, or find more<Button color={"secondary"} onClick={() => navigateTo("/templates")}>
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

            <Grid item xs={12} sx={{mt :2}}>
                <Paper sx={{ pb: 2, pt:2, mt : 1, mb :1, backgroundColor: (theme) => theme.palette.primary.main, }}>
                    <Typography variant={"body2"}>
                        See the latest Member, or find more<Button color={"secondary"} onClick={() => navigateTo("/friends")}>Here</Button>
                    </Typography>
                </Paper>
                {latestUser.addInfoDTO &&
                    <div onClick={() => navigateTo("/appuser/" + latestUser.id)}>
                        <AddInfoReadOnly userInfo={latestUser.addInfoDTO}/>
                    </div>
                  }
            </Grid>

            <Grid item xs={12} sx={{mt :3}}>
                <Paper sx={{ pb: 2, pt :2, backgroundColor: (theme) => theme.palette.primary.main, }} >
                    <Typography variant={"body2"}>
                        See what other people are saying, or find more<Button color={"secondary"} onClick={() => navigateTo("/blog")}>Here</Button>
                    </Typography>
                </Paper>
                <Box sx={{pt :1}}>
                    <ShowBlogs blogs={latestBlog}/>
                </Box>
            </Grid>
        </Grid>
    </Container>
</>
    );
}
