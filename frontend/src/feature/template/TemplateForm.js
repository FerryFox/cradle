import React, {useEffect, useState} from 'react';
import PropTypes from 'prop-types';
import {useNavigate} from "react-router-dom";
import axios from "axios";
import Container from "@mui/material/Container";
import Box from "@mui/material/Box";
import Avatar from "@mui/material/Avatar";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Typography from "@mui/material/Typography";
import TextField from "@mui/material/TextField";
import FormControlLabel from "@mui/material/FormControlLabel";
import Checkbox from "@mui/material/Checkbox";
import Button from "@mui/material/Button";
import Grid from "@mui/material/Grid";
import {Divider, FormControl, InputLabel, MenuItem, Select} from "@mui/material";

function resizeImage(file, maxWidth, maxHeight, callback)
{
    const reader = new FileReader();

    reader.onload = function(event) {
        const img = new Image();

        img.onload = function() {
            const canvas = document.createElement('canvas');

            let width = img.width;
            let height = img.height;

            if (width > height) {
                if (width > maxWidth) {
                    height *= maxWidth / width;
                    width = maxWidth;
                }
            } else {
                if (height > maxHeight) {
                    width *= maxHeight / height;
                    height = maxHeight;
                }
            }

            canvas.width = width;
            canvas.height = height;

            const ctx = canvas.getContext("2d");
            ctx.drawImage(img, 0, 0, width, height);

            callback(canvas.toDataURL("image/jpeg"));
        }

        img.src = event.target.result;
    }

    reader.readAsDataURL(file);
}

function TemplateForm()
{
    const [imageSrc, setImageSrc] = useState(null);
    const navigate = useNavigate();
    const [isShaking, setIsShaking] = useState(false);

    //values
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
    const handleStatusChanged = (event) => {
        setSelectedStatus(event.target.value);
    };

    const handleShake = () => {
        setIsShaking(true);
        setTimeout(() => {
            setIsShaking(false);
        }, 820);  // match the duration of the shake animation
    };

    const handleImageChange = (e) => {
        const file = e.target.files[0];

        if (file) {
            const reader = new FileReader();

            reader.onload = function(event) {
                setImageSrc(event.target.result);
            };
            reader.readAsDataURL(file);
        }
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        const token = localStorage.getItem('authToken');
        const file = document.getElementById('contained-button-file').files[0];

        resizeImage(file, 300, 300, async (resizedImageUrl) => {
            try {
                const payload = {
                    name: event.target.name.value,
                    description: event.target.description.value,
                    image: resizedImageUrl,  // Using the resized image here
                    stampCardCategory: selectedCategory,
                    stampCardSecurity: selectedSecurity,
                    stampCardStatus: selectedStatus,
                };

                const response = await axios.post('/api/templates/new-template', payload, {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`
                    }
                });

                if (response.status === 200) // Created
                {
                    navigate('/templates-owned');
                } else {
                    handleShake();
                }
            } catch (error) {
                handleShake();
                console.error('Error logging in:', error.response ? error.response.data : error.message);
            }
        });
    };

return (
    <Container className={isShaking ? 'shake' : ''} component="main" maxWidth="xs">
    <Box sx={{marginTop: 8, display: 'flex', flexDirection: 'column', alignItems: 'center',}}>

        <Typography component="h1" variant="h5">
            Create a Template for your Stamp Card
        </Typography>

        <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 , mb : 2}}>
            <TextField margin="normal" required fullWidth
                       id="name"
                       label="name"
                       name="name"
                       autoComplete="name"
                       autoFocus/>

            <TextField margin="normal" required fullWidth sx={{ marginBottom: 2 }}
                       name="description"
                       label="description"
                       id="description" />

            <Divider sx={{ marginBottom: 2 }}/>

            <FormControl fullWidth sx={{ marginBottom: 2 }}>
            <InputLabel id="category-wheel-lable-id">Category</InputLabel>
            <Select
                labelId="category-wheel-lable-id"
                id="category-wheel-id"
                value={selectedCategory}
                label="Category"
                onChange={handleCategoryChange}>

                {Category.map((category, index) => (
                    <MenuItem key={index} value={category}>{category}</MenuItem>
                ))}

            </Select>
            </FormControl>

            <FormControl fullWidth sx={{ marginBottom: 2 }}>
                <InputLabel id="status-wheel-lable-id">Status</InputLabel>
                <Select
                    labelId="status-wheel-lable-id"
                    id="category-wheel-id"
                    value={selectedStatus}
                    label="Category"
                    onChange={handleStatusChanged}>

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
                    value={selectedSecurity}
                    label="Security"
                    onChange={handleSecurityChange}>

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

            <Button type="submit" fullWidth variant="contained" sx={{ mt: 3, mb: 2 }}>
                Generate Template
            </Button>
        </Box>
    </Box>
</Container>
);
}

export default TemplateForm;


