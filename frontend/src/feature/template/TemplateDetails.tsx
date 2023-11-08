import React, {useState} from 'react';
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import {useLocation, useNavigate} from "react-router-dom";
import Container from "@mui/material/Container";
import Template from "./Template";
import axios from "axios";
import { Divider, Paper, Stack, Toolbar} from "@mui/material";
import CardContent from "@mui/material/CardContent";
import Card from "@mui/material/Card";
import {TemplateModel} from "./model/models";
import Controller from "../core/Controller";
import Grid from "@mui/material/Grid";
import {DEFAULT_ELEVATION} from "../../globalConfig";
import {createStampCardFromTemplateId} from "../../assets/service/stampCardService";


export default function TemplateDetails()
{
    const location = useLocation();
    const [templateModel] = useState<TemplateModel>(location.state?.templateModel);
    const navigate = useNavigate();
    
    const createdDate = new Date(templateModel.createdDate);
    const lastModifiedDate = new Date(templateModel.lastModifiedDate);
    const expirationDate = new Date(templateModel.expirationDate);
    const [gotCard, setGotCard] = useState(false);

    const handleDelete = (id : number) => {
        axios
            .delete(`/api/templates/delete/${id}`)
            .then(() => {
                navigate('/templates/owned')
            })
            .catch((error) => {
                console.error('Error deleting:', error);
            });
    };

    const handleGetCardClick = async (templateId: number) => {
        try {
            await createStampCardFromTemplateId(templateId);
            setGotCard(true);
        } catch (error) {
            console.error("Error creating the stamp card:", error);
        }
    };

return (
<>
<Controller title={"Template Details"} showSecondLine={false}/>
<Toolbar/>
    <Container>

    <Grid container spacing={2} justifyContent="center" sx={{mt : 2}}>
        <Grid item xs={6}  sx={{ mt :7, alignItems: 'center' }} >
            <Template templateModel={templateModel} />
        </Grid>
        <Grid item xs={6} >
            <Paper  elevation={DEFAULT_ELEVATION} sx={{pt : 2 , pb: 2, mb : 2, px: 1}}>
                <Stack direction="column" spacing={2} alignItems="center">

                    <Typography>
                        Share your Stamp Card with the world!
                    </Typography>

                    <Button
                        variant="contained"
                        sx={{ width: '80%' }}
                        color={gotCard ? "secondary" : "primary"}
                        onClick={() => handleGetCardClick(templateModel.id)}
                    >
                        Get
                    </Button>

                    <Button variant={"contained"} sx={{ width: '80%' }}
                            onClick={() => navigate("/new-mail/" + templateModel.id) }>
                        Share
                    </Button>
                </Stack>
            </Paper>

            <Paper elevation={DEFAULT_ELEVATION} sx={{pt : 2 , pb: 2, mb : 2, px: 1}}>
                <Stack direction="column" spacing={2} alignItems="center">

                    <Typography>
                        Manage your Stamp Card here!
                    </Typography>

                    <Button onClick={() => handleDelete(templateModel.id)}
                        color={"secondary"}
                        variant={"contained"}
                        sx={{ width: '80%' }}>

                        Delete
                    </Button>

                    <Button onClick={() => navigate('/template/edit', { state: { templateModel: templateModel }})}
                            color={"secondary"}
                            variant={"contained"}
                            sx={{ width: '80%' }}>
                        Edit
                    </Button>
                </Stack>
            </Paper>
        </Grid>
    </Grid>

    <Card elevation={DEFAULT_ELEVATION} sx={{ mt: 2 }}>
        <CardContent>
            <Typography variant="h4" sx={{textAlign : "right"}}>
                {templateModel.name}
            </Typography>
            <Divider color={"primary"} sx={{ my: 1,  }} />

        <Typography variant="h6" sx={{textAlign : "left"}}>
            Basic Information
        </Typography>
        <Paper elevation={DEFAULT_ELEVATION} >
            <Stack direction="column" spacing={2} alignItems="left"  sx={{ mx: 2, my : 2 , py :2}} >
            <Typography variant="body2" sx={{textAlign : "left"}}>
                Promise : {templateModel.promise}
            </Typography>

            <Typography variant="body2" sx={{textAlign : "left"}}>
                Stamps : {templateModel.defaultCount}
            </Typography>

            <Typography variant="body2" sx={{textAlign : "left"}}>
                Description : {templateModel.description}
            </Typography>

            <Typography variant="body2" sx={{textAlign : "left"}}>
                Creator : {templateModel.createdBy}
            </Typography>
            </Stack>
        </Paper>

        <Typography variant="h6" sx={{textAlign : "left"}}>
            Security Information
        </Typography>
        <Paper elevation={DEFAULT_ELEVATION} >
            <Stack direction="column" spacing={2} alignItems="left"  sx={{ mx: 2, my : 2 , py :2}} >
                <Typography variant="body2" sx={{textAlign : "left"}}>
                    Category : {templateModel.stampCardCategory}
                </Typography>

                <Typography variant="body2" sx={{textAlign : "left"}}>
                    Status : {templateModel.stampCardStatus}
                </Typography>

                <Typography variant="body2" sx={{textAlign : "left"}}>
                  Security : {templateModel.stampCardSecurity}
                </Typography>
            </Stack>
        </Paper>


        <Typography variant="h6" sx={{textAlign : "left"}}>
            Time Information
        </Typography>
        <Paper elevation={DEFAULT_ELEVATION} >
            <Stack direction="column" spacing={2} alignItems="left"  sx={{ mx: 2, my : 2, py :2 }}>
                <Typography variant="body2" sx={{textAlign : "left"}}>
                    Date of Creation : {createdDate?.toLocaleDateString()}
                </Typography>
                <Typography variant="body2" sx={{textAlign : "left"}}>
                   Date of Modification  : {lastModifiedDate?.toLocaleDateString()}
                </Typography>
                <Typography variant="body2" sx={{textAlign : "left"}}>
                    Date of Expiration : {expirationDate?.toLocaleDateString()}
                </Typography>
            </Stack>
        </Paper>

        <Typography variant="h6" sx={{textAlign : "left"}}>
            Picture Information
        </Typography>
            <Paper elevation={DEFAULT_ELEVATION} >
                <Stack direction="column" spacing={2} alignItems="left" sx={{ mx: 2, mt : 2, py :2 }}>
                        <img src={templateModel.image} alt={"Your Template Picture"}/>

                    <Button variant={"contained"}>
                        Edit
                    </Button>
                    <Button onClick={() => handleDelete(templateModel.id)} variant={"contained"}>
                        Delete
                    </Button>
                </Stack>
            </Paper>
        </CardContent>
    </Card>
</Container>
</>);
}
