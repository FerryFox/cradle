import Container from "@mui/material/Container";
import BottomController from "../core/BottomController";
import AppBarComponent from "../core/AppBarComponent";
import {Toolbar} from "@mui/material";
import {useLocation, useNavigate} from "react-router-dom";
import Template from "../template/Template";
import Grid from "@mui/material/Grid";
import StampField from "./StampField";



function StempCardDetails()
{
    const location = useLocation();
    const stampCardModel = location.state?.stampCardModel;

return(
    <div>
        <BottomController/>
        <Container>
            <AppBarComponent showMenuButtonElseBack={false} title="Details"/>
            <Toolbar/>

            <Grid container spacing={4} justifyContent="center" sx={{mt :1}}>
                <Grid item xs={6} key={stampCardModel.id} >
                    <Template templateModel={stampCardModel.templateModel} />
                </Grid>
                <Grid item xs={6}>
                    <p> Stamp Card Details</p>
                </Grid>
                <Grid item xs={12}>
                    <StampField count={stampCardModel.templateModel.defaultCount} />
                </Grid>
            </Grid>
        </Container>
    </div>
    )
}

export default StempCardDetails;