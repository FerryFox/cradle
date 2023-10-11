import React, {useState} from 'react';
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import {useLocation, useNavigate} from "react-router-dom";
import Container from "@mui/material/Container";
import Box from "@mui/material/Box";
import Template from "./Template";
import axios from "axios";
import DeleteConfirmation from "../../assets/popups/DeleteWithConfirm";
import {Badge, Divider, Toolbar} from "@mui/material";
import CardContent from "@mui/material/CardContent";
import Card from "@mui/material/Card";
import {TemplateModel} from "./model/models";
import Controller from "../core/Controller";
import Grid from "@mui/material/Grid";

export default function TemplateDetails()
{
    const location = useLocation();
    const [templateModel] = useState<TemplateModel>(location.state?.templateModel);
    const navigate = useNavigate();

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

return (
<>
<Controller title={"Template Details"} showSecondLine={false}/>
<Toolbar/>
    <Container>

    <Grid container spacing={2} justifyContent="center" sx={{mt : 2}}>
            <Grid item xs={6} sm={6} md={4} >
                <Template templateModel={templateModel} />
            </Grid>
    </Grid>


    <Box sx={{ display: 'flex', flexDirection: 'row', alignItems: 'center', marginTop: 2 }}>
        <Button onClick={() => navigate('/template/edit', { state: { templateModel: templateModel }})}
                variant={"contained"}>
            Edit
        </Button>

        <Button variant={"contained"}>
            Share
        </Button>
        <DeleteConfirmation onDelete={() => handleDelete(templateModel.id)} />
    </Box>

    <Card elevation={3} sx={{ mt: 2 }}>
        <CardContent>
            <Typography variant="h5" component="div">
                {templateModel.name}
            </Typography>
            <Divider sx={{ my: 1 }} />
            <Typography variant="body1" color="text.secondary">
                {templateModel.description}
            </Typography>

            <Box alignItems="center" justifyContent="center" sx={{ display: 'flex', mt: 2 }}>
                <Badge badgeContent={templateModel.defaultCount} color="primary" />
                <Typography variant="body2" sx={{ ml: 1, fontWeight: 'normal' }}>
                    Stamps
                </Typography>
            </Box>

            <Typography variant="body2" color="text.secondary" sx={{ mt: 2 }}>
                Date: {templateModel.createdDate}
            </Typography>

            <Typography variant="body2" color="text.secondary" sx={{ mt: 2 }}>
                Category: {templateModel.stampCardCategory}
            </Typography>

            <Box alignItems="center" justifyContent="center" sx={{ display: 'flex', mt: 2 }}>
                <Typography variant="body2" sx={{ ml: 1, fontWeight: 'normal' }}>
                    Security: {templateModel.stampCardSecurity}
                </Typography>
            </Box>

            <Typography variant="body2" color="text.secondary" sx={{ mt: 2 }}>
                Status: {templateModel.stampCardStatus}
            </Typography>
        </CardContent>
    </Card>
</Container>
</>);
}
