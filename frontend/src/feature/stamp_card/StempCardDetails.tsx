import Container from "@mui/material/Container";
import {Button, Paper, Toolbar} from "@mui/material";
import Template from "../template/Template";
import Grid from "@mui/material/Grid";
import StampField from "./StampField";
import axios from "axios";
import {useNavigate, useParams} from "react-router-dom";
import StampCardExtraInfo from "./StampCardExtraInfo";
import Controller from "../core/Controller";
import {StampCardModel, StampFieldModel} from "./model/models";
import React, {useEffect, useState} from "react";
import RedeemButton from "./RedeemButton";
import {DEFAULT_ELEVATION} from "../../globalConfig";


export default function StampCardDetails()
{
    const { id } = useParams<{ id: string }>();
    const [stampCardModel, setStampCardModel] = useState<StampCardModel>();
    const navigateTo = useNavigate();
    const [message, setMessage] = useState("");

    useEffect(() => {
        axios.get<StampCardModel>(`/api/stampcard/${id}`)
            .then(response => {
                setStampCardModel(response.data);
            })
            .catch(error => {
                console.error("Error fetching stamp card:", error);
            });
    }, [id]);

    function onStampAttempt(stampField: StampFieldModel)
    {
        const token = localStorage.getItem('authToken');

        if (!stampCardModel) {
            console.error("stampCardModel is not defined");
            return;
        }

        axios.post('/api/stamp/stampThisCard', stampField, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        })
            .then(response => {
                if (response.data.stampAble === true) {
                    const updatedFields = stampCardModel.stampFields.map((field: StampFieldModel) =>
                        field.id === stampField.id ? { ...field, stamped: true } : field
                    );

                    const updatedModel = {
                        ...stampCardModel,
                        stampFields: updatedFields
                    };

                    setStampCardModel(updatedModel);

                    if (updatedFields.every(field => field.stamped)) {
                        axios.post('/api/stamp/markStampCardAsComplete', stampCardModel.id, {
                            headers: {
                                'Content-Type': 'application/json',
                                'Authorization': `Bearer ${token}`
                            }
                        })
                            .then(response => {
                                if (response.status === 200) {
                                    setStampCardModel(response.data);
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

    const redeemCard = () => {
        const token = localStorage.getItem('authToken');

        axios.post('/api/stamp/markStampCardAsRedeemed', stampCardModel?.id, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        })
            .then(response => {
                if (response.status === 200) {
                    setStampCardModel(response.data);
                    navigateTo("/stampcards");
                }
            })
            .catch(error => {
                console.error("Error marking StampCard as complete:", error);
            });
    }

    const deleteStampCard = (id: number) => {
        const token = localStorage.getItem('authToken');

        if (!token) {
            console.error("No auth token found");
            return;
        }

        axios.delete(`/api/stampcard/${id}`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })
            .then(response => {
                if (response.status === 204)
                {
                    navigateTo("/stampcards");
                }
            })
            .catch(error => {
                console.error("Error deleting StampCard:", error);
            });
    }

    if (!stampCardModel) {
        return <div>Loading...</div>;
    }

return(
    <div>
        <Controller title="Details" showBackButton/>
        <Container maxWidth={false}>
            <Toolbar/>
            <Toolbar/>

            <Grid key={stampCardModel.id} container spacing={3} justifyContent="center" >
                <Grid item xs={6} >
                    <Template templateModel={stampCardModel.templateModel} />
                </Grid>

                <Grid item xs={6}>
                   <StampCardExtraInfo stampCardModel={stampCardModel} message={message}  />
                </Grid>

                <Grid item xs={12}>
                    {stampCardModel.completed ?
                        (
                            <RedeemButton redeemCard={redeemCard} />

                        )
                            :
                        (
                            <Grid item xs={12}>
                                <StampField stampFields={stampCardModel.stampFields} onStampAttempt={onStampAttempt} />
                            </Grid>
                        )
                    }
                </Grid>

                <Grid item xs={12}>
                    <Paper elevation={DEFAULT_ELEVATION} sx={{py : 1 , px : 1}}>
                        <Button
                            onClick={() => deleteStampCard(stampCardModel.id)}
                            variant={"contained"}  color={"error"}>
                            Stop Collecting and Delete this Card
                        </Button>
                    </Paper>
                </Grid>
            </Grid>
        </Container>
    </div>
    )
}
