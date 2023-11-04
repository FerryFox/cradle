import React, {useEffect, useMemo, useState} from "react";
import Controller from "../core/Controller";
import { Tab, Tabs, Toolbar} from "@mui/material";
import BlogForm from "./BlogForm";
import Container from "@mui/material/Container";
import {BlogDTO} from "./model/BlogDTO";
import axios from "axios";
import ShowBlogs from "./ShowBlogs";


export default function BlogPage() {
    const [blogs, setBlogs] = useState<BlogDTO[]>([]);
    const token = localStorage.getItem("authToken");

    const [tabValue, setTabValue] = React.useState('tab1');

    const handleChange = (event: React.SyntheticEvent, newValue: string) => {
        setTabValue(newValue);
    };

    const hotSortedBlogs = useMemo(() => {
        return [...blogs].sort((a, b) => a.title.localeCompare(b.title));
    }, [blogs]);

    const timeSortedBlogs = useMemo(() => {
        return [...blogs].sort((a, b) => new Date(b.createdDate).getTime() - new Date(a.createdDate).getTime());
    }, [blogs]);

    useEffect(() => {
        axios.get<BlogDTO[]>("api/blog/get-latest", {
            headers: {Authorization: `Bearer ${token}`}
        }).then((response) => {
            setBlogs(response.data);
        }).catch((error) => {
            console.log(error);
        });
    }, [token]);

    const handleSubmit = async (newBlog: BlogDTO) => {
        if (!token) { return; }
        await axios.post<BlogDTO>("api/blog/create", newBlog, {
            headers: { Authorization: `Bearer ${token}` }
        }).then((response) => {
            setBlogs(prevState => [...prevState, response.data]);
            setTabValue("tab1");
        }).catch((error) => {
            console.log(error);
        });
    }

    return (
        <>
            <Controller title={"Story"} />
            <Toolbar></Toolbar>
            <Container>

                <Tabs
                    value={tabValue}
                    onChange={handleChange}
                    textColor="secondary"
                    variant="fullWidth"
                    sx={{ py: 2 }}
                    indicatorColor="secondary">

                    <Tab value="tab1" label="Latest Stories" />
                    <Tab value="tab2" label="Hot Topics" />
                    <Tab value="tab3" label="Share Your Tale" />
                </Tabs>

                {tabValue === 'tab1' && <ShowBlogs blogs={timeSortedBlogs} />}
                {tabValue === 'tab2' && <ShowBlogs blogs={hotSortedBlogs} />}
                {tabValue === 'tab3' && <BlogForm onSubmit={handleSubmit} />}
            </Container>
        </>
    )
}
