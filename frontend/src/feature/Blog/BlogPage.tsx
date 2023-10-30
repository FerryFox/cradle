import React, {useEffect, useMemo, useState} from "react";
import Controller from "../core/Controller";
import {Button, ButtonGroup, Toolbar} from "@mui/material";
import BlogForm from "./BlogForm";
import Container from "@mui/material/Container";
import {BlogDTO} from "./model/BlogDTO";
import axios from "axios";
import ShowBlogs from "./ShowBlogs";


export default function BlogPage() {
    const [blogs, setBlogs] = useState<BlogDTO[]>([]);
    const [activeTab, setActiveTab] = useState('tab1');
    const token = localStorage.getItem("authToken");

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
            setActiveTab("tab1");
        }).catch((error) => {
            console.log(error);
        });
    }

    return (
        <>
            <Controller title={"Story"} />
            <Toolbar></Toolbar>
            <Container>
                <ButtonGroup variant="text" aria-label="outlined primary button group" sx={{ py: 2 }}>
                    <Button onClick={() => setActiveTab("tab1")}>Latest Stories </Button>
                    <Button onClick={() => setActiveTab("tab2")}>Hot Topics</Button>
                    <Button onClick={() => setActiveTab("tab3")}>Share Your Tale</Button>
                </ButtonGroup>

                {activeTab === 'tab1' && <ShowBlogs blogs={timeSortedBlogs} />}
                {activeTab === 'tab2' && <ShowBlogs blogs={hotSortedBlogs} />}
                {activeTab === 'tab3' && <BlogForm onSubmit={handleSubmit} />}
            </Container>
        </>
    )
}
