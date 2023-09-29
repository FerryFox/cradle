import React, {useEffect, useState} from "react";
import {useLocation, useNavigate} from "react-router-dom";
import TextField from "@mui/material/TextField";
import Container from "@mui/material/Container";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import {Divider, FormControl, InputLabel, MenuItem, Select} from "@mui/material";
import axios from "axios";
import Button from "@mui/material/Button";


function TemplateEdit()
{
    const location = useLocation();
    const navigate = useNavigate();
    const originalTemplateModel = location.state?.templateModel;
    const [imageSrc, setImageSrc] = useState(originalTemplateModel?.image || '');

    useEffect(() => {
        console.log("Original Image Base64:", originalTemplateModel?.image);
        if (originalTemplateModel?.image) {
            const base64Header = `data:image/jpeg;base64,`; // adjust the mime type if you know it's different
            setImageSrc(base64Header + originalTemplateModel.image);
        }
    }, [originalTemplateModel]);

    const [editedTemplateModel, setEditedTemplateModel] = useState({ ...originalTemplateModel });
    const hasChanges = JSON.stringify(originalTemplateModel) !== JSON.stringify(editedTemplateModel);

    const [Category, setCategory] = useState([]);
    const [selectedCategory, setSelectedCategory] = useState('');
    useEffect(() => {
        axios('/api/templates/categories')
            .then((response) => setCategory(response.data))
    }, []);
    const handleCategoryChange = (event) => {
        setSelectedCategory(event.target.value);
    };

    const [Security, setSecurity] = useState([]);
    const [selectedSecurity, setSelectedSecurity] = useState('');
    useEffect(() => {
        axios('/api/templates/security')
            .then((response) => setSecurity(response.data));
    }, []);
    const handleSecurityChange = (event) => {
        setSelectedSecurity(event.target.value);
    };

    const [Status, setStatus] = useState([]);
    const [selectedStatus, setSelectedStatus] = useState('');
    useEffect(() => {
        axios('/api/templates/status')
            .then((response) => setStatus(response.data))
        ;
    }, []);

    const handleTemplateChange = (name, value) => {
        setEditedTemplateModel(prevModel => ({ ...prevModel, [name]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const token = localStorage.getItem('authToken');

        const response = await axios.put('/api/templates', editedTemplateModel,
    {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        })
            .then(response => {
            console.log('Template updated successfully', response.data);
            // Navigate to the desired route after successful update.
            navigate('/templates/owned');
        })
            .catch(error => {
                console.error('There was an error updating the template!', error);
            });;
    };

    const handleImageChange = (e) => {
        const file = e.target.files[0];

        if (file) {
            const reader = new FileReader();

            reader.onload = function (event) {
                setImageSrc(event.target.result);
                // Update editedTemplateModel with the new image src.
                setEditedTemplateModel((prevModel) => ({ ...prevModel, image: event.target.result }));
            };

            reader.readAsDataURL(file);
        }
    }

return (
<Container component="main" maxWidth="xs">
    <Box sx={{marginTop: 8, display: 'flex', flexDirection: 'column', alignItems: 'center',}}>
        <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 , mb : 2}}>

            <Typography component="h1" variant="h5">
                Edit your Template
            </Typography>

            <Box sx={{ display: 'flex', gap: 2, marginBottom: 2 }}>
                <Button variant={"contained"} onClick={() => navigate('/templates/owned')}>
                    {hasChanges ? "Discard" : "Back"}
                </Button>

                {hasChanges && (
                    <Button type="submit" variant={"contained"} color={"warning"}>
                        Save
                    </Button>
                )}
            </Box>

            <TextField margin="normal" required fullWidth sx={{ marginBottom: 2 }}
                value={editedTemplateModel.name}
                label="Name"
                onChange={(e) =>
                    handleTemplateChange('name', e.target.value)}/>

            <TextField margin="normal" required fullWidth sx={{ marginBottom: 2 }}
                   value={editedTemplateModel.description}
                   label="Description"
                   onChange={(e) =>
                       handleTemplateChange('description', e.target.value)}/>

            <TextField margin="normal" required fullWidth sx={{ marginBottom: 2 }}
                   value={editedTemplateModel.defaultCount}
                   label="Number"
                       onChange={(e) =>
                           handleTemplateChange('defaultCount', e.target.value)}/>

            <Divider sx={{ marginBottom: 2 }}/>

            <FormControl fullWidth sx={{ marginBottom: 2 }}>
                <InputLabel id="category-wheel-lable-id">Category</InputLabel>
                <Select
                    labelId="category-wheel-lable-id"
                    id="category-wheel-id"
                    value={editedTemplateModel.stampCardCategory}
                    label="Category"
                    onChange={(e) =>
                        handleTemplateChange('stampCardCategory', e.target.value)}>

                {Category.map((category, index) => (
                    <MenuItem key={index} value={category}>{category}</MenuItem>
                ))}
                </Select>
            </FormControl>

            <FormControl fullWidth sx={{ marginBottom: 2 }}>
                <InputLabel id="category-wheel-lable-id">Status</InputLabel>
                <Select
                    labelId="category-wheel-lable-id"
                    id="status-wheel-id"
                    value={editedTemplateModel.stampCardStatus}
                    label="Status"
                    onChange={(e) =>
                        handleTemplateChange('stampCardStatus', e.target.value)}>

                    {Status.map((status, index) => (
                        <MenuItem key={index} value={status}>{status}</MenuItem>
                    ))}
                </Select>
            </FormControl>

            <FormControl fullWidth sx={{ marginBottom: 2 }}>
                <InputLabel id="security-wheel-lable-id">Security</InputLabel>
                <Select
                    labelId="security-wheel-lable-id"
                    id="security-wheel-id"
                    value={editedTemplateModel.stampCardSecurity}
                    label="Security"
                    onChange={(e) =>
                        handleTemplateChange('stampCardSecurity', e.target.value)}>

                    {Security.map((security, index) => (
                        <MenuItem key={index} value={security}>{security}</MenuItem>
                    ))}
                </Select>
            </FormControl>

            <FormControl fullWidth sx={{ marginBottom: 2 }}>
                <input
                    accept="image/*"
                    style={{ display: 'none' }}
                    id="contained-button-file"
                    type="file"
                    onChange={handleImageChange}
                />
                <label htmlFor="contained-button-file">
                    <Button variant="contained" color="primary" component="span">
                        Upload Image
                    </Button>
                </label>

                {imageSrc && (
                    <Box
                        sx={{
                            display: 'flex',
                            justifyContent: 'center',
                            mt: 2,
                            height: 140,
                            backgroundImage: `url(${imageSrc})`,
                            backgroundSize: 'cover',
                            backgroundPosition: 'center'
                        }}
                    ></Box>
                )}
            </FormControl>
        </Box>
    </Box>
</Container>
    );
}
export default TemplateEdit;