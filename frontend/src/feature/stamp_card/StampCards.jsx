import AppBarComponent from "../core/AppBarComponent";
import {Toolbar} from "@mui/material";
import {useEffect, useState} from "react";
import axios from "axios";
import Template from "../template/Template";
import Grid from "@mui/material/Grid";
import Container from "@mui/material/Container";
import Button from "@mui/material/Button";
import {useNavigate} from "react-router-dom";
import BottomController from "../core/BottomController";
import * as React from "react";
import DrawerComponent from "../core/DrawerComponent";

function StampCards()
{
    const [stempCards, setStempCards] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const navigator = useNavigate();

    const navigate = useNavigate();
    const [open, setOpen] = React.useState(false);
    const toggleDrawer = () => {setOpen(!open);}

    useEffect(() =>
    {
        setLoading(true);
        setError(null);

        axios.get('/api/stampcard/all')
            .then(response =>
            {
                setStempCards(response.data);
                setLoading(false);
            })
            .catch(error =>
            {
                console.error("Error fetching data:", error);
                setError(error);
                setLoading(false);
            });
    }, []);

return(
<div>
    <BottomController/>
    <Container>
        <AppBarComponent showMenuButtonElseBack={true} title="Your Stamp Cards"/>
        <DrawerComponent open={open} toggleDrawer={toggleDrawer} navigate={navigate} />
        <Toolbar/>

        <Grid container spacing={4} justifyContent="center" sx={{mt : 1}} >
            {stempCards.map( (card) => (

                <Grid item xs={6}  key={card.id} >
                    <Template templateModel={card.templateModel} />
                    <Button variant={"contained"}
                            onClick={() => { navigator(`/stampcard/details/${card.id}` )}}
                    >
                        Stamp this card  </Button>
                </Grid>
            ))}
        </Grid>
    </Container>
</div>
    )
}
export default StampCards;
