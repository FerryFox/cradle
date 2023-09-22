import React from 'react';
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import {useLocation} from "react-router-dom";
import Container from "@mui/material/Container";
import Box from "@mui/material/Box";
import Template from "./Template";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import DeleteConfirmation from "../../assets/popups/DeleteWithConfirm";

function TemplateDetails()
{
    const location = useLocation();
    const templateModel = location.state?.templateModel;
    const navigate = useNavigate();

    const handleDelete = (id) => {
        axios
            .delete(`/api/templates/delete/${id}`)
            .then((response) => {
                navigate('/templates/owned')
            })
            .catch((error) => {
                console.error('Error deleting:', error);
            });
    };

return (
<Container>
    <Box sx={{marginTop: 8, display: 'flex', flexDirection: 'column', alignItems: 'center', border: '1px solid red'}}>
        <Template TemplateModel={templateModel} />
    </Box>

    <Box sx={{marginTop: 3, display: 'flex', flexDirection: 'column', alignItems: 'center', border: '1px solid red'}}>
        <Typography  variant="body1">
            Name : {templateModel.name}
        </Typography>
        <Typography component="h5" variant="body1">
            Description : {templateModel.description}
        </Typography>
        <Typography component="h5" variant="body1">
            Stamps : {templateModel.defaultCount}
        </Typography>
        <Typography component="h5" variant="body1">
            Date : {templateModel.createdDate}
        </Typography>
        <Typography component="h5" variant="body1">
            Category : {templateModel.stampCardCategory}
        </Typography>
        <Typography component="h5" variant="body1">
            Security : {templateModel.stampCardSecurity}
        </Typography>
        <Typography component="h5" variant="body1">
            Status : {templateModel.stampCardStatus}
        </Typography>
    </Box>

    <Box sx={{marginTop: 3, display: 'flex', flexDirection: 'column', alignItems: 'center', border: '1px solid red'}}>

        <Button onClick={() => navigate('/template/edit', { state: { templateModel: templateModel }})}
            variant={"contained"}>
                Edit
        </Button>

        <DeleteConfirmation onDelete={() => handleDelete(templateModel.id)} />
    </Box>
</Container>);
}

export default TemplateDetails;