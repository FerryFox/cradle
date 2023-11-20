import React, {useState} from 'react';
import {useNavigate} from "react-router-dom";
import axios from "axios";
import {NewTemplate} from "../model/models";
import BasicInformationForm from "./BasicInformationForm";
import Controller from "../../core/Controller";
import Container from "@mui/material/Container";
import {Divider, Step, StepLabel, Stepper, Toolbar} from "@mui/material";
import Typography from "@mui/material/Typography";
import {BasicInformation} from "./models";
import ImageFrom from "./ImageForm";
import SecurityForm from "./SecurityForm";

export default function TemplateFormSequence()
{
    const token = localStorage.getItem("authToken");
    axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;

    const navigate = useNavigate();

    const [newTemplate, setNewTemplate] = useState<NewTemplate>({} as NewTemplate);
    const [newBasicInformation, setNewBasicInformation] = useState<BasicInformation>({
        name: '', // Initialize all fields
        promise: '',
        description: '',
        defaultCount: 0,
        stampCardCategory: '',
    });

    const [activeStep, setActiveStep] = useState(0); // Step state
    const handleNext = () => {
        setActiveStep((prevActiveStep) => prevActiveStep + 1);
    };

    const handleBasicInformationChange = (updatedBasicInformation: BasicInformation)  =>{
        setNewBasicInformation(updatedBasicInformation);
        handleNext();
    }

    const handleImageChange = () => {
        handleNext();
    }

    const handleSecurityChange = () => {
        handleNext();
    }

    const steps = [
        'Basic Information',
        'Style your card',
        'Set Security',
        'Look Preview',
    ];

    const stepBack = () => {
        setActiveStep((prevActiveStep) => prevActiveStep - 1);
    }

return (
<>
    <Controller title={"Create a new Template"}/>
    <Toolbar/>
    <Toolbar/>
        <Container>

            <Stepper activeStep={activeStep} alternativeLabel>
                {steps.map((label) => (
                    <Step key={label}>
                        <StepLabel>
                            <Typography variant={"body2"}>
                                {label}
                            </Typography>
                        </StepLabel>
                    </Step>
                ))}
            </Stepper>
            <Divider color={"primary"} sx={{ mt: 2 }}/>

            {activeStep === 0 && (<BasicInformationForm oldBasicInformation={newBasicInformation}
                                                        onBasicInformationChange={handleBasicInformationChange} />)}

            {activeStep === 1 && (<ImageFrom onImageChange={handleImageChange} stepBack={stepBack}/>)}

            {activeStep === 2 && (<SecurityForm />)}
            {activeStep === 3 && (<Typography>Step 4</Typography>)}

    </Container>
</>
);
}