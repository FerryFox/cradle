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

function TemplateForm()
{
    const [imageSrc, setImageSrc] = useState(null);
    const navigate = useNavigate();
    const [isShaking, setIsShaking] = useState(false);

    //values
    const [Category, setCategory] = useState([]);
    const [selectedCategory, setSelectedCategory] = useState('');
    useEffect(() => {
        axios('api/templates/categories')
            .then((response) => response.json())
            .then((data) => setCategory(data));
    }, []);
    const handleCategoryChange = (event) => {
        setSelectedCategory(event.target.value);
    };

    const [Security, setSecurity] = useState([]);
    const [selectedSecurity, setSelectedSecurity] = useState('');
    useEffect(() => {
        axios('api/templates/security')
            .then((response) => response.json())
            .then((data) => setSecurity(data));
    }, []);
    const handleSecurityChange = (event) => {
        setSelectedSecurity(event.target.value);
    };

    const [Status, setStatus] = useState([]);
    const [selectedStatus, setSelectedStatus] = useState('');
    useEffect(() => {
        axios('api/templates/status')
            .then((response) => response.json())
            .then((data) => setStatus(data));
    }, []);
    const handleSecurityStatus = (event) => {
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

    const handleSubmit = async (event) =>
    {
        event.preventDefault();
        try
        {
            const data = new FormData(event.currentTarget);

            const response = await axios.post('/api/templates/create',
        {
                email: data.get('email'),
                password: data.get('password'),
            });

            if (response.status === 201) // Created
            {
                navigate('/templates-owned');
            }
            else
            {
                handleShake();
            }
        }
        catch (error)
        {
            handleShake();
            console.error('Error logging in:', error.response ? error.response.data : error.message);
        }
    };

return (
    <Container className={isShaking ? 'shake' : ''} component="main" maxWidth="xs">
    <Box sx={{marginTop: 8, display: 'flex', flexDirection: 'column', alignItems: 'center',}}>

        <Typography component="h1" variant="h5">
            New Template for a Stamp Card
        </Typography>

        <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
            <TextField margin="normal" required fullWidth
                       id="name"
                       label="name"
                       name="name"
                       autoComplete="name"
                       autoFocus/>

            <TextField margin="normal" required fullWidth
                       name="discription"
                       label="discription"
                       id="discription" />

            <div>
                <input type="file" accept="image/*" onChange={handleImageChange} />
                {imageSrc && <img src={imageSrc} alt="Selected" style={{ maxWidth: '300px', marginTop: '20px' }} />}
            </div>

            <div>
                <label>
                    Select a Category:
                    <select value={selectedCategory} onChange={handleCategoryChange}>
                        <option value="" disabled>Select a Category</option>
                        {Category.map((category) => (
                            <option key={category} value={category}>
                                {category}
                            </option>
                        ))}
                    </select>
                </label>
            </div>

            <Button type="submit" fullWidth variant="contained" sx={{ mt: 3, mb: 2 }}>
                Generate Template
            </Button>
        </Box>
    </Box>
</Container>
);
}

export default TemplateForm;