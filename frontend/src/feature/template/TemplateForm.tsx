import React, {ChangeEvent, useEffect, useState} from 'react';
import {useNavigate} from "react-router-dom";
import axios from "axios";
import Container from "@mui/material/Container";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import {Divider, FormControl, InputLabel, MenuItem, Select, SelectChangeEvent, Toolbar} from "@mui/material";
import {resizeAndCropImage} from "../../assets/picture/resizeAndCropImage";
import Controller from "../core/Controller";
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { DatePicker, LocalizationProvider } from '@mui/x-date-pickers';
import {Simulate} from "react-dom/test-utils";
import error = Simulate.error;

export default function TemplateForm()
{
    const currentDate = new Date();
    currentDate.setFullYear(currentDate.getFullYear() + 1);
    const [selectedDate, setSelectedDate] = useState(currentDate);

    const [imageSrc, setImageSrc] = useState<string | null>(null);
    const navigate = useNavigate();
    const [isShaking, setIsShaking] = useState<boolean>(false);
    const [number, setValue] = useState<number>(10);
    const [timeGate, setTimeGate] = useState<number>(0);

    //values
    const [Category, setCategory] = useState<string[]>([]);
    const [selectedCategory, setSelectedCategory] = useState('');
    useEffect(() => {
        axios('/api/templates/categories')
            .then((response) => setCategory(response.data))
    }, []);


    const [Security, setSecurity] = useState([]);
    const [selectedSecurity, setSelectedSecurity] = useState('');
    useEffect(() => {
        axios('/api/templates/security')
            .then((response) => setSecurity(response.data));
    }, []);


    const [Status, setStatus] = useState([]);
    const [selectedStatus, setSelectedStatus] = useState('');
    useEffect(() => {
        axios('/api/templates/status')
            .then((response) => setStatus(response.data))
           ;
    }, []);


    const handleShake = () => {
        setIsShaking(true);
        setTimeout(() => {
            setIsShaking(false);
        }, 820);  // match the duration of the shake animation
    };

    const handleImageChange = (e :ChangeEvent<HTMLInputElement>) => {
        const file = e.target.files && e.target.files[0];

        if (file) {
            const reader = new FileReader();

            reader.onload = function(event: ProgressEvent<FileReader>) {
                if (typeof event.target!.result === 'string') {
                    setImageSrc(event.target!.result);
                }
            };
            reader.readAsDataURL(file);
        }
    }

    const handleSubmit = async (event : React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        console.log("handleSubmit triggered");
        const token = localStorage.getItem('authToken');
        const fileInput = document.getElementById('contained-button-file') as HTMLInputElement;
        const file = fileInput?.files?.[0];

        const formData = new FormData(event.currentTarget);
        const nameValue = formData.get('name') as string;
        const promiseValue = formData.get('promise') as string;
        const descriptionValue = formData.get('description') as string;

        const date = selectedDate.toISOString();

        if (!file) return;
        console.log("file found");
        resizeAndCropImage(file, 300, 200, async (resizedImageUrl :string) => {
            try {
                console.log("resizeAndCropImage triggered");
                const payload =
                    {
                    name: nameValue,
                    promise: promiseValue,
                    description: descriptionValue,
                    image: resizedImageUrl,
                    fileName : file.name,
                    defaultCount : number,

                    stampCardCategory: selectedCategory,
                    stampCardSecurity: selectedSecurity,
                    stampCardStatus: selectedStatus,

                    securityTimeGateDuration : `PT${timeGate}H`,
                    expirationDate : date
                    }



                const response = await axios.post('/api/templates/new-template', payload, {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`
                    }
                });
                console.log("axios.post triggered");
                console.log(payload);

                if (response.status === 201) // Created
                {
                    navigate('/templates/owned');
                } else {
                    handleShake();
                }
            } catch (error) {
                console.log(error);
                handleShake();
            }
        });
    };

return (
<LocalizationProvider dateAdapter={AdapterDateFns}>
<Controller title={'Create a Template'}/>
    <Container className={isShaking ? 'shake' : ''} component="main" maxWidth="xs">
    <Toolbar/>

    <Box sx={{marginTop: 8, display: 'flex', flexDirection: 'column', alignItems: 'center',}}>

        <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 , mb : 2}}>

            <Typography variant={"h5"} align="right">
                Basic  Information
            </Typography>

            <Divider color={"primary"} sx={{ marginBottom: 2 }}/>

            <TextField margin="normal" required fullWidth
                       id="name"
                       label="name"
                       name="name"
                       autoComplete="name"
                       autoFocus/>

            <TextField margin="normal" required fullWidth sx={{ marginBottom: 2 }}
                       name="promise"
                       label="promise"
                       id="promise" />

            <TextField margin="normal" required fullWidth sx={{ marginBottom: 2 }}
                       name="description"
                       label="description"
                       id="description" />

            <TextField margin="normal" required fullWidth sx={{ marginBottom: 2 }}
                label="Stamps per Card"
                variant="outlined"
                type="number"
                value={number}
                onChange={(e : React.ChangeEvent<HTMLInputElement>) => setValue(Number(e.target.value))}
            />

            <Typography variant={"h5"} align="right">
                Security & Category
            </Typography>
            <Divider color={"primary"} sx={{ marginBottom: 2 }}/>


            <DatePicker
                label="Expiration Date"
                openTo="year"
                value={selectedDate}
                onChange={(newDate) => setSelectedDate(newDate as Date)}
                components={{
                    TextField: (props) => <TextField {...props} fullWidth sx={{mb : 2}}/>
                }}
            />

            <FormControl fullWidth sx={{ marginBottom: 2 }}>
            <InputLabel id="category-wheel-lable-id">Category</InputLabel>
            <Select
                labelId="category-wheel-lable-id"
                id="category-wheel-id"
                value={selectedCategory}
                label="Category"
                onChange={(event: SelectChangeEvent) => setSelectedCategory(event.target.value)}>

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
                    onChange={(event: SelectChangeEvent) => setSelectedStatus(event.target.value)}>

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
                    onChange={(event: SelectChangeEvent) => setSelectedSecurity(event.target.value)}>

                    {Security.map((security, index) => (
                        <MenuItem key={index} value={security}>{security}</MenuItem>
                    ))}
                </Select>

                {selectedSecurity === 'TIMEGATE' &&
                    <TextField margin="normal"
                               required
                               fullWidth
                               label="Hours"
                               variant="outlined"
                               type="number"
                               value={timeGate}
                               onChange={(e : ChangeEvent<HTMLInputElement>) => setTimeGate(Number(e.target.value))}
                               sx={{ marginBottom: 2 }}/>
                }
            </FormControl>

            <Typography variant={"h5"} align="right">
               Set Picture
            </Typography>
            <Divider color={"primary"} sx={{ marginBottom: 2 }}/>

            <FormControl fullWidth sx={{ marginBottom: 2 }}>
                <input
                    accept="image/*"
                    style={{ display: 'none' }}
                    id="contained-button-file"
                    type="file"
                    onChange={handleImageChange}
                />
                <label htmlFor="contained-button-file">
                    <Button variant="contained" color={"secondary"} component="span">
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
</LocalizationProvider>
);
}




