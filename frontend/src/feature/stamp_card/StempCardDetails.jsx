import Container from "@mui/material/Container";
import BottomController from "../core/BottomController";
import AppBarComponent from "../core/AppBarComponent";
import {Toolbar} from "@mui/material";
import {useLocation, useNavigate} from "react-router-dom";
import Template from "../template/Template";
import Grid from "@mui/material/Grid";
import StampField from "./StampField";
import {useEffect, useState} from "react";
import axios from "axios";



function StampCardDetails()
{
        const location = useLocation();
        const stampCardModel = location.state?.stampCardModel;
        const [stampFields, setStampFields] = useState([]);
        const [loading, setLoading] = useState(false);
        const [error, setError] = useState(null);

        function onStampAttempt(stampField)
        {
            setStampFields((prevFields) =>
                prevFields.map((field) =>
                    field.id === stampField.id ? { ...field, isStamped: true } : field)
            );
        }

    useEffect(() => {
        if (stampCardModel) {
            setLoading(true);
            setError(null);

            axios.get('/api/stampcard/fields/' + stampCardModel.id)
                .then(response => {
                    setStampFields(response.data);
                    setLoading(false);
                })
                .catch(error => {
                    console.error("Error fetching data:", error);
                    setError(error);
                    setLoading(false);
                });
        }
    }, []);

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
                    <StampField stampFields={stampFields} onStampAttempt={onStampAttempt} />
                </Grid>
            </Grid>
        </Container>
    </div>
    )
}

export default StampCardDetails;