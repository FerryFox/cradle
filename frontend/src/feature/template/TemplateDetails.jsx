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
import {Badge, ButtonGroup, Divider} from "@mui/material";
import CardContent from "@mui/material/CardContent";
import Card from "@mui/material/Card";
import * as PropTypes from "prop-types";
import {green, red} from "@mui/material/colors";
import TopController from "../core/TopController";


function SecurityIcon(props) {
    return null;
}

SecurityIcon.propTypes = {color: PropTypes.any};

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
    <TopController></TopController>
    <Box display="flex" alignItems="center" justifyContent={"center"}  >
        <Template TemplateModel={templateModel} />
    </Box>

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
                <SecurityIcon color={templateModel.stampCardSecurity === "TRUSTUSER" ? green[500] : red[500]} />
                <Typography variant="body2" sx={{ ml: 1, fontWeight: 'normal' }}>
                    Security: {templateModel.stampCardSecurity}
                </Typography>
            </Box>

            <Typography variant="body2" color="text.secondary" sx={{ mt: 2 }}>
                Status: {templateModel.stampCardStatus}
            </Typography>

            <Typography variant="body2" color="text.secondary" sx={{ mt: 2 }}>
                updated ?: {templateModel.lastModifiedDate}
            </Typography>
        </CardContent>
    </Card>


</Container>);
}

export default TemplateDetails;