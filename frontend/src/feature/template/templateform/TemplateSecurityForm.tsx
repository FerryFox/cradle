import React, {ChangeEvent, useEffect, useState} from "react";
import Grid from "@mui/material/Grid";
import {
    Button,
    Divider,
    FormControl,
    FormHelperText,
    InputLabel,
    MenuItem,
    Select,
    SelectChangeEvent,
    Stack,
    Switch
} from "@mui/material";
import Box from "@mui/material/Box";
import {AdapterDateFns} from "@mui/x-date-pickers/AdapterDateFns";
import {DatePicker, LocalizationProvider} from "@mui/x-date-pickers";
import TextField from "@mui/material/TextField";
import FormControlLabel from "@mui/material/FormControlLabel";
import {NewTemplateSecurity} from "../model/models";
import axios from "axios";

type Props = {
    stepBack: () => void;
    handleNext : () => void;
    onSecuritySubmit : (newTemplateSecurity : NewTemplateSecurity) => void;
    newTemplateSecurity : NewTemplateSecurity;
};

type FormErrors = {
    expirationDate? : string;
    timeGateError? : string;
    security? : string;
}

export default function TemplateSecurityForm({stepBack, handleNext, onSecuritySubmit, newTemplateSecurity} : Props) {

    const [isShaking, setIsShaking] = useState<boolean>(false);
    const [formErrors, setFormErrors] = useState<FormErrors>({});
    const [checkedTimeGate, setCheckedTimeGate] = useState(false);
    const [timeGate , setTimeGate] =
        useState<number>(newTemplateSecurity.securityTimeGateDurationInHour);

    const handleTimeGateChange = (event : ChangeEvent<HTMLInputElement> ) => {
        setCheckedTimeGate(event.target.checked);
    };

    const [selectedDate, setSelectedDate] = useState<Date>(newTemplateSecurity.expirationDate);

    const [security, setSecurity] = useState([]);
    const [selectedSecurity, setSelectedSecurity] = useState(newTemplateSecurity.stampCardSecurity);
    useEffect(() => {
        axios('/api/templates/security')
            .then((response) => setSecurity(response.data));
    }, []);

    const [status, setStatus] = useState([]);
    const [selectedStatus, setSelectedStatus] = useState(newTemplateSecurity.stampCardStatus);
    useEffect(() => {
        axios('/api/templates/status')
            .then((response) => setStatus(response.data));
    }, []);

    const handleShake = () => {
        setIsShaking(true);
        setTimeout(() => {
            setIsShaking(false);
        }, 820);  // match the duration of the shake animation
    };

    const onSubmit = () => {
        const errors = validateFormData();

        if (Object.keys(errors).length > 0) {
            handleShake();
            return;
        }

        const newTemplateSecurity : NewTemplateSecurity = {
            expirationDate: selectedDate,
            securityTimeGateDurationInHour: timeGate,
            stampCardSecurity : selectedSecurity,
            stampCardStatus : selectedStatus,
        }
        onSecuritySubmit(newTemplateSecurity);
        handleNext();
    }

    const validateFormData = () => {
        let errors :FormErrors = {};

        if (selectedSecurity === '')
        {
            errors.security = 'select a proper security option';
        }

        const isFutureDate = selectedDate.getTime() > new Date().getTime();
        if (!isFutureDate) {
            errors.expirationDate = 'Expiration date should be in the future';
        }

        if (checkedTimeGate && timeGate <= 0) {
            errors.timeGateError = 'Time gate should be positive';
        }

        setFormErrors(errors);
        return errors;
    };

    return (
        <LocalizationProvider dateAdapter={AdapterDateFns}>
        <Box className={isShaking ? 'shake' : ''} component="form" onSubmit={handleNext} noValidate sx={{mt: 2}}>

        <Stack spacing={2}>
            <FormControl fullWidth error={!!formErrors.security}>
                <InputLabel id="security-wheel-lable-id">
                    Choose a base for Your Security Option
                </InputLabel>
                <Select
                    labelId="security-wheel-lable-id"
                    id="security-wheel-id"
                    value={selectedSecurity}
                    label="Choose a base for Your Security Option"
                    onChange={(event: SelectChangeEvent) => setSelectedSecurity(event.target.value)}>

                    {security.map((security) => (
                        <MenuItem key={security} value={security}>{security}</MenuItem>
                    ))}
                </Select>
                <FormHelperText>{formErrors.security}</FormHelperText>
            </FormControl>

            <FormControl fullWidth error={!!formErrors.security}>
                <InputLabel id="security-wheel-lable-id">
                    Set a public status of your Stamp Card
                </InputLabel>
                <Select
                    labelId="security-wheel-lable-id"
                    id="security-wheel-id"
                    value={selectedStatus}
                    label="Set a public status of your Stamp Card"
                    onChange={(event: SelectChangeEvent) => setSelectedStatus(event.target.value)}>

                    {status.map((status) => (
                        <MenuItem key={status} value={status}>{status}</MenuItem>
                    ))}
                </Select>
                <FormHelperText>{formErrors.security}</FormHelperText>
            </FormControl>
        </Stack>

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
                        fullWidth sx={{my : 2}}/>
                }}
            />

            <FormControlLabel
                control={
                    <Switch
                        checked={checkedTimeGate}
                        onChange={handleTimeGateChange}
                        inputProps={{ 'aria-label': 'controlled' }}
                    />
                }
                label="Set a period of time before the card can be stamped again?"
            />

            {checkedTimeGate && (
            <>
            <TextField
                margin="normal"
                id="outlined-number"
                label="Time Gate [h]"
                type="number"
                InputLabelProps={{
                shrink: true,}}
                    value={timeGate}
                    onChange={(e) => setTimeGate(parseInt(e.target.value))}
                    fullWidth sx={{mb : 2}}/>
            </>)
            }

            <Divider color={"primary"} sx={{ my : 2 }}/>
            <Grid container>
                <Grid item xs={6} >
                    <Button variant={"contained"} color={"primary"} onClick={stepBack}>
                        Back
                    </Button>
                </Grid>
                <Grid item xs={6}>
                    <Button variant={"contained"} onClick={onSubmit}>
                        Next
                    </Button>
                </Grid>
            </Grid>
        </Box>
        </LocalizationProvider>
    );
}