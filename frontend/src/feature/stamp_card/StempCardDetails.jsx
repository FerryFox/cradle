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
        const initialStampCardModel = location.state?.stampCardModel;
        const [stampCardModel, setStampCardModel] = useState(initialStampCardModel);


        function onStampAttempt(stampField)
        {
            const token = localStorage.getItem('authToken');

            //security check
            const response = axios.post('/api/stamp/stampThisCard' , stampCardModel
            ,{
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`
                    }})
                .then(response => {
                    if (response.data === true) {
                        const updatedFields = stampCardModel.stampFields.map((field) =>
                            field.id === stampField.id ? { ...field, isStamped: true } : field
                        );

                        const updatedModel = {
                            ...stampCardModel,
                            stampFields: updatedFields
                        };
                        setStampCardModel(updatedModel);
                    }
                })
                .catch(error => {
                    console.error("Error during stamping:", error);
                    // Handle the error appropriately
                });

        }

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
                    <StampField stampFields={stampCardModel.stampFields} onStampAttempt={onStampAttempt} />
                </Grid>
            </Grid>
        </Container>
    </div>
    )
}

export default StampCardDetails;