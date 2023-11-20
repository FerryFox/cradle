import React, {ChangeEvent, useEffect, useState} from 'react';
import {useNavigate} from "react-router-dom";
import axios from "axios";
import Container from "@mui/material/Container";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import {
    Divider,
    FormControl,
    FormHelperText,
    InputLabel,
    MenuItem,
    Select,
    SelectChangeEvent,
    Toolbar
} from "@mui/material";
import {resizeAndCropImage} from "../../assets/picture/resizeAndCropImage";
import Controller from "../core/Controller";
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { DatePicker, LocalizationProvider } from '@mui/x-date-pickers';
import {NewTemplate, SecurityTimeGate} from "./model/models";

type FormErrors = {
    name?: string;
    promise? : string;
    description? : string;
    defaultCount? : string;

    stampCardCategory? : string;
    stampCardSecurity? : string;
    stampCardStatus? : string;

    image? : string;
    fileName? : string;

    expirationDate? : string

    //TimeGate
    timeGateNumber? : string;
}

export default function TemplateForm()
{
    const navigate = useNavigate();
    const [isShaking, setIsShaking] = useState<boolean>(false);

    const [newTemplate, setNewTemplate] = useState<NewTemplate>({} as NewTemplate);
    const [formErrors, setFormErrors] = useState<FormErrors>({});

    const currentDate = new Date();
    currentDate.setFullYear(currentDate.getFullYear() + 1);
    const [selectedDate, setSelectedDate] = useState<Date>(currentDate);

    const [imageSrc, setImageSrc] = useState<string | null>(null);

    const [securityTimeGate, setSecurityTimeGate] = useState<SecurityTimeGate>({timeGateNumber : 0});

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

    const handleImageChange = (event :ChangeEvent<HTMLInputElement>) => {
        if (!event.target.files) return;
        const file = event.target.files[0];

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

    const validateFormData = () => {
        let errors :FormErrors = {};

        if (!newTemplate.name || newTemplate.name.length > 21) {
            errors.name = 'Name is required and should not be longer then 20 characters';
        }

        if (!newTemplate.promise || newTemplate.promise.length > 21) {
            errors.promise = 'Promise is required and should not be longer then 20 characters';
        }

        if (!newTemplate.description || newTemplate.description.length > 81) {
            errors.description = 'Description is required and should not be longer then 80 characters';
        }

        if(!newTemplate.defaultCount || newTemplate.defaultCount < 1 || newTemplate.defaultCount > 101){
            errors.defaultCount = 'Stamps should be at least 1 and not more then 100';
        }

        if (!selectedCategory) {
            errors.stampCardCategory = 'Category is required';
        }

        if (!selectedSecurity) {
            errors.stampCardSecurity = 'Security is required';
        }

        if (!selectedStatus) {
            errors.stampCardStatus = 'Status is required';
        }

        const isFutureDate = selectedDate.getTime() > new Date().getTime();
        if (!isFutureDate) {
           errors.expirationDate = 'Expiration date should be in the future';
        }

        if (selectedSecurity === 'TIMEGATE' &&
            (!securityTimeGate.timeGateNumber ||
                securityTimeGate.timeGateNumber < 0))
        {
            errors.timeGateNumber = 'Time between stamping can not be negative';
        }

        setFormErrors(errors);  // Update the state once here

        return errors;
    };

    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        const errors = validateFormData();
        setFormErrors(errors);

        if (Object.keys(errors).length > 0) {
            handleShake();
            return;
        }

        const token = localStorage.getItem('authToken');
        const fileInput = document.getElementById('contained-button-file') as HTMLInputElement;
        const file = fileInput?.files?.[0];

        if (!file) return;

        try {
            const resizedImageUrl = await resizeAndCropImage(file);

            const payload: NewTemplate = {
                ...newTemplate,
                stampCardCategory: selectedCategory,
                stampCardSecurity: selectedSecurity,
                stampCardStatus: selectedStatus,
                image: resizedImageUrl,
                fileName: file.name,
                expirationDate: new Date(selectedDate.toISOString()),

                securityTimeGate : securityTimeGate
            };

            const response = await axios.post('/api/templates/new-template', payload, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            });

            if (response.status === 201) { // Created
                navigate('/templates/owned');
            } else {
                handleShake();
            }
        } catch (error) {
            console.log(error);
            handleShake();
        }
    };

return (
<LocalizationProvider dateAdapter={AdapterDateFns}>
<Controller title={'Create a Template'} showBackButton={true}/>
    <Container className={isShaking ? 'shake' : ''} component="main" maxWidth="xs">
    <Toolbar/>

    <Box sx={{marginTop: 3, display: 'flex', flexDirection: 'column', alignItems: 'center',}}>
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
                       autoFocus
                       value={newTemplate.name}
                       onChange ={e  =>
                           setNewTemplate(newTemplate =>({...newTemplate, name : e.target.value}))}
                       error={!!formErrors.name}
                       helperText={formErrors.name}/>

            <TextField margin="normal" required fullWidth sx={{ marginBottom: 2 }}
                       name="promise"
                       label="promise"
                       id="promise"
                       value={newTemplate.promise}
                       onChange ={e  =>
                           setNewTemplate(newTemplate =>({...newTemplate, promise : e.target.value}))}
                       error={!!formErrors.promise}
                       helperText={formErrors.promise}/>

            <TextField margin="normal" required fullWidth sx={{ marginBottom: 2 }}
                       name="description"
                       label="description"
                       id="description"
                       value={newTemplate.description}
                       onChange ={e  =>
                           setNewTemplate(newTemplate =>({...newTemplate, description : e.target.value}))}
                       error={!!formErrors.description}
                       helperText={formErrors.description}/>

            <TextField margin="normal" required fullWidth sx={{ marginBottom: 2 }}
                label="Stamps per Card"
                variant="outlined"
                type="number"
                value={newTemplate.defaultCount}
                       onChange={e =>
                           setNewTemplate(newTemplate => ({
                               ...newTemplate,
                               defaultCount: parseInt(e.target.value, 10)
                           }))}
                error={!!formErrors.defaultCount}
                helperText={formErrors.defaultCount}/>

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
                    TextField: (props) => <TextField
                        {...props}
                        error={!!formErrors.expirationDate}
                        helperText={formErrors.expirationDate}
                        fullWidth sx={{mb : 2}}/>
                }}
            />

            <FormControl fullWidth sx={{ marginBottom: 2 }} error={!!formErrors.stampCardCategory}>
                <InputLabel id="category-wheel-lable-id">Category</InputLabel>
                <Select
                    labelId="category-wheel-lable-id"
                    id="category-wheel-id"
                    value={selectedCategory}
                    label="Category"
                    onChange={(event: SelectChangeEvent) => setSelectedCategory(event.target.value)}>

                    {Category.map((category, index) => (
                        <MenuItem key={category} value={category}>{category}</MenuItem>
                    ))}
                </Select>
                <FormHelperText>{formErrors.stampCardCategory}</FormHelperText>
            </FormControl>

            <FormControl fullWidth sx={{ marginBottom: 2 }} error={!!formErrors.stampCardStatus}>
                <InputLabel id="status-wheel-lable-id">Status</InputLabel>
                <Select
                    labelId="status-wheel-lable-id"
                    id="status-wheel-id"
                    value={selectedStatus}
                    label="Status"
                    onChange={(event: SelectChangeEvent) => setSelectedStatus(event.target.value)}>

                    {Status.map((status, index) => (
                        <MenuItem key={status} value={status}>{status}</MenuItem>
                    ))}
                </Select>
                <FormHelperText>{formErrors.stampCardStatus}</FormHelperText>
            </FormControl>

            <FormControl fullWidth sx={{ marginBottom: 2 }} error={!!formErrors.stampCardSecurity}>
                <InputLabel id="security-wheel-lable-id">Security</InputLabel>
                <Select
                    labelId="security-wheel-lable-id"
                    id="security-wheel-id"
                    value={selectedSecurity}
                    label="Security"
                    onChange={(event: SelectChangeEvent) => setSelectedSecurity(event.target.value)}>

                    {Security.map((security, index) => (
                        <MenuItem key={security} value={security}>{security}</MenuItem>
                    ))}
                </Select>
                <FormHelperText>{formErrors.stampCardStatus}</FormHelperText>

                {selectedSecurity === 'TIMEGATE' &&
                    <>
                    <Typography variant={"h5"} align="right">
                        Time Gate
                    </Typography>
                    <Divider color={"primary"} sx={{ marginBottom: 2 }}/>

                    <TextField margin="normal"
                               required
                               fullWidth
                               label="Hours"
                               variant="outlined"
                               type="number"
                               value={securityTimeGate.timeGateNumber}
                               onChange={(e : ChangeEvent<HTMLInputElement>) => setSecurityTimeGate({
                                   timeGateNumber : parseInt(e.target.value, 10)})}
                               sx={{ marginBottom: 2 }}
                                error={!!formErrors.timeGateNumber}
                                helperText={formErrors.timeGateNumber}/>
                    </>
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
                            height: 340,
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