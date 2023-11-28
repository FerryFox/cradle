import React, {useEffect, useState} from "react";
import axios from "axios";
import Typography from "@mui/material/Typography";
import {Box, Button, Divider, FormControl, FormHelperText, InputLabel, MenuItem, Select, SelectChangeEvent} from "@mui/material";
import TextField from "@mui/material/TextField";
import Grid from "@mui/material/Grid";
import {useNavigate} from "react-router-dom";
import {NewBasicInformation} from "../model/models";

type FromErrorBasic = {
    name?: string;
    promise? : string;
    description? : string;
    defaultCount? : string;
    stampCardCategory? : string;
}

type Pops = {
    oldBasicInformation : NewBasicInformation;
    onBasicInformationChange: (basicInformation: NewBasicInformation) => void;
}

export default function BasicInformationForm( {oldBasicInformation, onBasicInformationChange} : Readonly<Pops>)
{
    const [isShaking, setIsShaking] = useState<boolean>(false);
    const navigateTo = useNavigate();

    const [formErrors, setFormErrors] = useState<FromErrorBasic>({});
    const [basicInformation, setBasicInformation] =
        useState<NewBasicInformation>(oldBasicInformation);

    const [Category, setCategory] = useState<string[]>([]);
    const [selectedCategory, setSelectedCategory] = useState(oldBasicInformation.stampCardCategory || '');
    useEffect(() => {
        axios('/api/templates/categories')
            .then((response) => setCategory(response.data))
    }, []);

    const validateFormDataBasics = () => {
        let errors: FromErrorBasic = {};

        if (!basicInformation.name || basicInformation.name.length > 21) {
            errors.name = 'Name is required and should not be longer then 20 characters';
        }

        if (!basicInformation.promise || basicInformation.promise.length > 21) {
            errors.promise = 'Promise is required and should not be longer then 20 characters';
        }

        if (!basicInformation.description || basicInformation.description.length > 81) {
            errors.description = 'Description is required and should not be longer then 80 characters';
        }

        if (!basicInformation.defaultCount || basicInformation.defaultCount < 1 || basicInformation.defaultCount > 101) {
            errors.defaultCount = 'Stamps should be at least 1 and not more then 100';
        }

        if (!selectedCategory) {
            errors.stampCardCategory = 'Please select a category';
        }

        setFormErrors(errors);  // Update the state once here

        return errors;
    }

        function handleSubmit(e : React.FormEvent<HTMLFormElement>)
        {
            e.preventDefault();
            const errors : FromErrorBasic = validateFormDataBasics();
            setFormErrors(errors);

            if (Object.keys(errors).length === 0) {
                const updatedBasicInformation = {
                    ...basicInformation,
                    stampCardCategory: selectedCategory
                };
                onBasicInformationChange(updatedBasicInformation);
            }
            else handleShake();
        }

    const handleShake = () => {
        setIsShaking(true);
        setTimeout(() => {
            setIsShaking(false);
        }, 820);  // match the duration of the shake animation
    };

return (
<>
    <Box className={isShaking ? 'shake' : ''} component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 2}}>
        <Typography variant={"h6"} align="center">
            Enter Basic Information
        </Typography>

        <TextField margin="normal" required fullWidth
                   id="name"
                   label="Choose a public name"
                   name="name"
                   autoComplete="name"
                   autoFocus
                   value={basicInformation.name}
                   onChange ={e  =>
                       setBasicInformation((basicInformation: NewBasicInformation) =>({...basicInformation, name : e.target.value}))}
                   error={!!formErrors.name}
                   helperText={formErrors.name}/>

        <TextField margin="normal" required fullWidth
                   name="promise"
                   label="What are you offering for completion?"
                   id="promise"
                   value={basicInformation.promise}
                   onChange ={e  =>
                       setBasicInformation((basicInformation : NewBasicInformation) =>({...basicInformation, promise : e.target.value}))}
                   error={!!formErrors.promise}
                   helperText={formErrors.promise}/>

        <TextField margin="normal" required fullWidth
                   name="description"
                   label="Write something about your card"
                   id="description"
                   value={basicInformation.description}
                   onChange ={e  =>
                       setBasicInformation((basicInformation : NewBasicInformation) =>({...basicInformation, description : e.target.value}))}
                   error={!!formErrors.description}
                   helperText={formErrors.description}/>

        <TextField margin="normal" required fullWidth sx={{ mb: 2 }}
                   label="How much stamps is your card holding?"
                   variant="outlined"
                   type="number"
                   value={basicInformation.defaultCount}
                   onChange={e =>
                       setBasicInformation( (basicInformation : NewBasicInformation) => ({
                           ...basicInformation,
                           defaultCount: parseInt(e.target.value, 10)
                       }))}
                   error={!!formErrors.defaultCount}
                   helperText={formErrors.defaultCount}/>

        <FormControl fullWidth error={!!formErrors.stampCardCategory}>
            <InputLabel id="category-wheel-lable-id">
                Choose a category for your stamp card
            </InputLabel>
            <Select
                labelId="category-wheel-lable-id"
                id="category-wheel-id"
                value={selectedCategory}
                label="Choose a category for your stamp card"
                onChange={(event: SelectChangeEvent) => setSelectedCategory(event.target.value)}>

                {Category.map((category, index) => (
                    <MenuItem key={category} value={category}>{category}</MenuItem>
                ))}
            </Select>
            <FormHelperText>{formErrors.stampCardCategory}</FormHelperText>
        </FormControl>

        <Divider color={"primary"} sx={{ my : 2 }}/>

        <Grid container>
            <Grid item xs={6} >
                <Button variant={"text"} color={"secondary"} onClick={() => navigateTo(-1)} >
                    Stop Creating
                </Button>
            </Grid>
            <Grid item xs={6}>
                <Button type={"submit"} variant={"contained"}>
                    Next
                </Button>
            </Grid>
        </Grid>
    </Box>
</>
);
}