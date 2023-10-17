import React, {useEffect, useState} from "react";
import {StampCardModel} from "../stamp_card/model/models";
import {useNavigate} from "react-router-dom";
import Controller from "../core/Controller";
import {Button, Divider, Toolbar} from "@mui/material";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import Grid from "@mui/material/Grid";
import StampCardView from "../stamp_card/StampCardView";
import axios from "axios";
import ArchiveCard from "./ArchiveCard";

export default function Archive()
{
    const [stampCards, setStampCards] = useState<StampCardModel[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>();
    const navigateTo = useNavigate();

    useEffect(() => {
        const fetchStampCards = async () => {
            try {
                const response = await axios.get('/api/stampcard/archived');
                setStampCards(response.data);
            } catch (error) {
                setError("Error fetching stamp cards.");
                console.error("Error fetching stamp cards:", error);
            } finally {
                setLoading(false);
            }
        };
        fetchStampCards().catch();
    }, []);

    if (loading) return ( <div> Loading ... </div>)
    if (error) return ( <div>Error loading data. Please try again later.</div> );

return(
<>
<Controller title="Archive"/>
    <Container>
        <Toolbar/>
        <Toolbar/>
        {stampCards.length === 0 && (
            <>
                <Typography variant={"h4"} align={"center"}>
                    No stamp cards found. Get Your next Stamp Card
                </Typography>
                <Button
                    onClick={() => navigateTo('/templates')}
                    variant={"contained"}
                    color={"primary"}
                    sx={{mt : 3}}>
                    Get
                </Button>
            </>
        )}

        {stampCards.length > 0 && (
            <>
                <Typography variant={"h3"} align={"right"}>
                    Redemmed Stamp Cards
                </Typography>
                <Divider color={"secondary"}></Divider>
                <Grid container spacing={4} justifyContent="center" sx={{mt : 1}}>
                    {stampCards.map((card) => (
                        <Grid item xs={12} key={card.id}>
                            <ArchiveCard stampCard={card} />
                        </Grid>
                    ))}
                </Grid>
            </>)}
    </Container>
</>);
}