import React, {useEffect, useState} from "react";
import {StampCardModel} from "./model/models";
import {useNavigate} from "react-router-dom";
import Controller from "../core/Controller";
import Container from "@mui/material/Container";
import {Button, Toolbar} from "@mui/material";
import Grid from "@mui/material/Grid";
import axios from "axios";
import StampCardView from "./StampCardView";

export default function StampCards()
{
    const [stampCards, setStampCards] = useState<StampCardModel[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>();
    const navigateTo = useNavigate();

    useEffect(() => {
        const fetchStampCards = async () => {
            try {
                const response = await axios.get('/api/stampcard/all');
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
<div>
    <Controller title="Stamp Cards"/>
    <Container>
        <Toolbar/>
        <Grid container spacing={4} justifyContent="center" sx={{mt : 1}} >
            {stampCards.map( (card) => (
                <Grid item xs={12}  key={card.id} >
                    <StampCardView stampCard={card}  />
                </Grid>
            ))}
        </Grid>
    </Container>
</div>
    )
}

