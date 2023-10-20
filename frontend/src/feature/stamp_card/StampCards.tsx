import React, {useEffect, useState} from "react";
import {StampCardModel} from "./model/models";
import Controller from "../core/Controller";
import Container from "@mui/material/Container";
import {Button, Divider, Toolbar} from "@mui/material";
import Grid from "@mui/material/Grid";
import axios from "axios";
import StampCardView from "./StampCardView";
import Typography from "@mui/material/Typography";
import {useNavigate} from "react-router-dom";

export default function StampCards()
{
    const [stampCards, setStampCards] = useState<StampCardModel[]>([]);

    const active = stampCards.filter((card) => !card.completed && !card.redeemed);
    const completed = stampCards.filter((card) => card.completed);

    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>();
    const navigateTo = useNavigate();

    useEffect(() => {
        const fetchStampCards = async () => {
            try {
                const token = localStorage.getItem('authToken');
                const response = await axios.get('/api/stampcard/allactive', {
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                });
                setStampCards(response.data);
            } catch (error) {
                setError("Error fetching stamp cards.");
                console.error("Error fetching stamp cards:", error);
            } finally {
                setLoading(false);
            }
        };
        fetchStampCards();
    }, []);

    if (loading) return ( <div> Loading ... </div>)
    if (error) return ( <div>Error loading data. Please try again later.</div> );

return(
<div>
<Controller title="Stamp Cards" showBackButton={false}/>
    <Container>
        <Toolbar/>
        <Toolbar/>
        {active.length === 0 && completed.length === 0 &&
            (<>
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
            )
        }

        {completed.length > 0 && (
            <>
                <Typography variant={"h3"} align={"right"}>
                    Ready to Claim
                </Typography>
                <Divider color={"secondary"}></Divider>
                <Grid container spacing={4} justifyContent="center" sx={{mt : 1}}>
                    {completed.map((card) => (
                        <Grid item xs={12} key={card.id}>
                            <StampCardView stampCard={card} />
                        </Grid>
                    ))}
                </Grid>
            </>
        )}

        {active.length > 0 && (
            <>
            <Typography variant={"h3"} align={"right"} sx={{my : 3}}>
                Continue Collecting
            </Typography>
            <Divider color={"secondary"}></Divider>
            <Grid container spacing={4} justifyContent="center" sx={{mt : 1}} >
                {active.map( (card) => (
                    <Grid item xs={12}  key={card.id} >
                        <StampCardView stampCard={card}  />
                    </Grid>
                ))}
            </Grid>
            </>)}
    </Container>
</div>
    )
}

