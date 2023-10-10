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
import Controller from "../core/Controller";

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


    function onStampAttempt(stampField) {
        const token = localStorage.getItem('authToken');

        axios.post('/api/stamp/stampThisCard', stampField, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        })
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

                    // Check if all stampFields are stamped
                    if (updatedFields.every(field => field.stamped)) {
                        // All stampFields are stamped, mark the StampCard as complete
                        axios.post('/api/stamp/markStampCardAsComplete', id , {
                            headers: {
                                'Content-Type': 'application/json',
                                'Authorization': `Bearer ${token}`
                            }
                        })
                            .then(response => {
                                if(response.status === 200)
                                {
                                    setStampCardModel(response.data);  // update stampCardModel with the returned response
                                }
                            })
                            .catch(error => {
                                console.error("Error marking StampCard as complete:", error);
                            });
                    }
                }
                setMessage(response.data.stampMessage);
            })
            .catch(error => {
                console.error("Error during stamping:", error);
            });
    }

    if (!stampCardModel) {
        return <div>Loading...</div>;
    }

return(
    <div>
        <Controller title="Details"/>
        <Container maxWidth={false}>

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