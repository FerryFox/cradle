import React, {ChangeEvent, FormEvent, useEffect, useState} from "react";
import {useLocation, useNavigate} from "react-router-dom";
import TextField from "@mui/material/TextField";
import Container from "@mui/material/Container";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import {Divider, FormControl, InputLabel, MenuItem, Select, Toolbar} from "@mui/material";
import axios from "axios";
import Button from "@mui/material/Button";
import {DatePicker, LocalizationProvider} from "@mui/x-date-pickers";
import {AdapterDateFns} from "@mui/x-date-pickers/AdapterDateFns";
import {TemplateModel} from "./model/models";
import Controller from "../core/Controller";

type FormErrors = {
    name?: string;
    promise? : string;
    description? : string;

    stampCardCategory? : string;
    stampCardStatus? : string;

    expirationDate? : string
}

export default function TemplateEdit()
{
    const navigate = useNavigate();

    const location = useLocation();
    const originalTemplateModel = location.state?.templateModel as TemplateModel
    const [selectedDate, setSelectedDate] = useState<Date>(new Date(originalTemplateModel.expirationDate));
    const [editedTemplateModel, setEditedTemplateModel] = useState<TemplateModel>({ ...originalTemplateModel });
    const [formErrors, setFormErrors] = useState<FormErrors>({});

    const [imageSrc, setImageSrc] = useState(originalTemplateModel?.image || '');
    useEffect(() => {
        console.log("Original Image Base64:", originalTemplateModel?.image);
        if (originalTemplateModel?.image) {
            const base64Header = `data:image/jpeg;base64,`; // adjust the mime type if you know it's different
            setImageSrc(base64Header + originalTemplateModel.image);
        }
    }, [originalTemplateModel]);

    const [Category, setCategory] = useState<string[]>([]);
    useEffect(() => {
        axios('/api/templates/categories')
            .then((response) => setCategory(response.data))
    }, []);

    const [Status, setStatus] = useState<string[]>([]);
    useEffect(() => {
        axios('/api/templates/status')
            .then((response) => setStatus(response.data))
        ;
    }, []);

    const hasChanges = JSON.stringify(originalTemplateModel) !== JSON.stringify(editedTemplateModel);

    function handleTemplateChange<K extends keyof TemplateModel>(
        name: K,
        value: TemplateModel[K]
    ) {
        setEditedTemplateModel(prevModel => ({ ...prevModel, [name]: value }));
    }

    const validateFormData = () => {
        let errors :FormErrors = {};

        if (!editedTemplateModel.name || editedTemplateModel.name.length > 21) {
            errors.name = 'Name is required and should not be longer then 20 characters';
        }

        if (!editedTemplateModel.promise || editedTemplateModel.promise.length > 21) {
            errors.promise = 'Promise is required and should not be longer then 20 characters';
        }

        if (!editedTemplateModel.description || editedTemplateModel.description.length > 151) {
            errors.description = 'Description is required and should not be longer then 150 characters';
        }


        if (!editedTemplateModel) {
            errors.stampCardCategory = 'Category is required';
        }

        if (!editedTemplateModel) {
            errors.stampCardStatus = 'Status is required';
        }

        const isFutureDate = selectedDate.getTime() > new Date().getTime();
        if (!isFutureDate) {
            errors.expirationDate = 'Expiration date should be in the future';
        }

        setFormErrors(errors);  // Update the state once here

        return errors;
    };

    const handleSubmit = async (event : FormEvent<HTMLFormElement>)  => {
        event.preventDefault();

        const errors = validateFormData();
        setFormErrors(errors);

        if (Object.keys(errors).length > 0) {
            return;
        }

        const token = localStorage.getItem('authToken');
        await axios.put('/api/templates', editedTemplateModel,
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

    const handleImageChange = (e : ChangeEvent<HTMLInputElement>) =>
    {
        if (!e.target.files) return;
        const file = e.target.files[0];

        if (file) {
            const reader = new FileReader();

            reader.onload = function(event: ProgressEvent<FileReader>)
            {
                if(event.target) {
                    const result = event.target.result as string;  // Type assertion
                    setImageSrc(result);
                    setEditedTemplateModel((prevModel: TemplateModel) => ({ ...prevModel, image: result }));
                }
            };

            reader.readAsDataURL(file);
        }
    }

return (
<LocalizationProvider dateAdapter={AdapterDateFns}>
<Controller title={'Edit your Template'} showBackButton/>
    <Container>
        <Toolbar/>

        <Box sx={{marginTop: 4, display: 'flex', flexDirection: 'column', alignItems: 'center',}}>
            <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mb : 2}}>

                {hasChanges ? (
                <Box sx={{ display: 'flex', gap: 2, marginBottom: 2 }}>
                    <Button variant={"contained"} onClick={() => navigate('/templates/owned')}>
                        Discard Changes
                    </Button>

                    {hasChanges && (
                        <Button type="submit" variant={"contained"} color={"secondary"}>
                            Save
                        </Button>
                    )}
                </Box>
                ) : <Toolbar/>}

                <Typography variant={"h5"} align="right">
                    Edit  Information
                </Typography>

                <Divider color={"primary"} sx={{ marginBottom: 2 }}/>

                <TextField margin="normal" required fullWidth sx={{ marginBottom: 2 }}
                    value={editedTemplateModel.name}
                    label="Name"
                    onChange={(e) =>
                        handleTemplateChange('name', e.target.value)}
                   error={!!formErrors.name}
                   helperText={formErrors.name}/>

                <TextField margin="normal" required fullWidth sx={{ marginBottom: 2 }}
                           value={editedTemplateModel.promise}
                           label="Promise"
                           onChange={(e) =>
                               handleTemplateChange('promise', e.target.value)}
                           error={!!formErrors.promise}
                           helperText={formErrors.promise}/>

                <TextField margin="normal" required fullWidth sx={{ marginBottom: 2 }}
                       value={editedTemplateModel.description}
                       label="Description"
                       onChange={(e) =>
                           handleTemplateChange('description', e.target.value)}
                           error={!!formErrors.description}
                           helperText={formErrors.description}/>

                <Typography variant={"h5"} align="right">
                   Edit Configuration
                </Typography>
                <Divider color={"primary"} sx={{ marginBottom: 2 }}/>

                <DatePicker
                    label="Expiration Date"
                    openTo="year"
                    value={selectedDate}
                    onChange={(newDate) => {
                        setSelectedDate(newDate as Date);
                        setEditedTemplateModel(prevModel => ({
                            ...prevModel,
                            expirationDate: newDate as Date
                        }));
                    }}
                    components={{
                        TextField: (props) => <TextField
                            {...props}
                            error={!!formErrors.expirationDate}
                            helperText={formErrors.expirationDate}
                            fullWidth sx={{mb : 2}}/>
                    }}
                />

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
                        <MenuItem key={category} value={category}>{category}</MenuItem>
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
                            <MenuItem key={status} value={status}>{status}</MenuItem>
                        ))}
                    </Select>
                </FormControl>

                <Typography variant={"h5"} align="right">
                    Edit Picture
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
                                height: 340,
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
</LocalizationProvider>
    );
}
