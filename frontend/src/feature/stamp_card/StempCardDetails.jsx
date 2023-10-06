import Container from "@mui/material/Container";
import BottomController from "../core/BottomController";
import AppBarComponent from "../core/AppBarComponent";
import {Toolbar} from "@mui/material";
import Template from "../template/Template";
import Grid from "@mui/material/Grid";
import StampField from "./StampField";
import {useEffect, useState} from "react";
import axios from "axios";
import { useParams } from "react-router-dom";
import StampCardExtraInfo from "./StampCardExtraInfo";

function StampCardDetails()
{
    const { id } = useParams();
    const [stampCardModel, setStampCardModel] = useState();
    const [message, setMessage] = useState("");


    useEffect(() => {
        axios.get(`/api/stampcard/${id}`)
            .then(response => {
                setStampCardModel(response.data);
            })
            .catch(error => {
                console.error("Error fetching stamp card:", error);
            });
    }, [id]);


    function onStampAttempt(stampField)
    {
        const token = localStorage.getItem('authToken');

        //security check
        const response = axios.post('/api/stamp/stampThisCard' , stampField
        ,{
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }})
            .then(response => {
                if (response.data.stampAble === true) {
                    const updatedFields = stampCardModel.stampFields.map((field) =>
                        field.id === stampField.id ? { ...field, stamped: true } : field
                    );

                    const updatedModel = {
                        ...stampCardModel,
                        stampFields: updatedFields
                    };
                    setStampCardModel(updatedModel);
                }
                setMessage(response.data.stampMessage);
            })
            .catch(error => {
                console.error("Error during stamping:", error);
                // Handle the error appropriately
            });
    }

    if (!stampCardModel) {
        return <div>Loading...</div>;
    }

return(
    <div>
        <BottomController/>
        <Container maxWidth={false}>
            <AppBarComponent showMenuButtonElseBack={false} title="Details"/>
            <Toolbar/>

            <Grid container spacing={4} justifyContent="center" sx={{mt :1}}>
                <Grid item xs={6} key={stampCardModel.id} >
                    <Template templateModel={stampCardModel.templateModel} />
                </Grid>
                <Grid item xs={6}>
                   <StampCardExtraInfo stampCardModel={stampCardModel} message={message}  />
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