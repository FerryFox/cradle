import React, {useEffect, useState} from 'react';
import {useNavigate} from "react-router-dom";
import axios from "axios";
import {NewBasicInformation, NewTemplateComposer, NewTemplateImage, NewTemplateSecurity} from "../model/models";
import BasicInformationForm from "./BasicInformationForm";
import Controller from "../../core/Controller";
import Container from "@mui/material/Container";
import {Divider, Step, StepLabel, Stepper, Toolbar} from "@mui/material";
import Typography from "@mui/material/Typography";
import ImageFrom from "./ImageForm";
import TemplateSecurityForm from "./TemplateSecurityForm";
import TemplatePreview from "./TemplatePreview";
import {resizeBase64AndCropImage} from "../../../assets/picture/resizeAndCropImage";


export default function TemplateFormSequence()
{
    const token = localStorage.getItem("authToken");
    axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
    const navigate = useNavigate();

    const [activeStep, setActiveStep] = useState(0); // Step state

    const currentDate = new Date();
    currentDate.setFullYear(currentDate.getFullYear() + 1);

    const [newTemplate, setNewTemplate] = useState<NewTemplateComposer>({
        newBasicInformation: {
            name: '',
            promise: '',
            description: '',
            defaultCount: 0,
            stampCardCategory: '',
        },

        newTemplateImage: {
            image: '',
        },

        newTemplateSecurity: {
            expirationDate: currentDate,
            securityTimeGateDurationInHour : 0,
            stampCardSecurity : "TRUSTUSER",
            stampCardStatus : "PUBLIC",
        },
    });
    useEffect(() => {
        console.log(newTemplate);
    }, [newTemplate]);


    const handleBasicInformationChange = (updatedBasicInformation: NewBasicInformation)  =>{
        setNewTemplate(currentTemplate => ({
            ...currentTemplate,
            newBasicInformation: updatedBasicInformation
        }));
        handleNext();
    }

    const handleImageChange = (newImage : string) =>
    {
        const changedImage: NewTemplateImage = {
            image: newImage
        }

        setNewTemplate(currentTemplate => ({
            ...currentTemplate,
            newTemplateImage: changedImage }))
    }

    const handleNewTemplateSecurity = (newTemplateSecurity : NewTemplateSecurity) => {

        setNewTemplate(currentTemplate => ({
            ...currentTemplate,
            newTemplateSecurity: newTemplateSecurity
        }));
    }

    const stepBack = () => {
        setActiveStep((prevActiveStep) => prevActiveStep - 1);
    }

    const handleNext = () => {
        setActiveStep((prevActiveStep) => prevActiveStep + 1);
    };

    const handleSubmit = async () => {
        const token = localStorage.getItem('authToken');

        try {
            const resizedImageUrl = await resizeBase64AndCropImage(newTemplate.newTemplateImage.image, 600,300 );
            console.log(resizedImageUrl)

            const newTemplateImage: NewTemplateImage = {
                image: resizedImageUrl,
            };

            console.log(newTemplateImage.image)

            setNewTemplate(currentTemplate => ({
                ...currentTemplate,
                newTemplateImage: newTemplateImage
            }));

            const response = await axios.post('/api/templates/new-template', newTemplate, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            });

            if (response.status === 201) { // Created
                navigate('/templates/owned');
            }
        } catch (error) {
            console.error(error);
        }
    };

    const steps = [
        'Basic Information',
        'Add an Image',
        'Set Security',
        'Look Preview',
    ];

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

            {activeStep === 0 && (<BasicInformationForm oldBasicInformation={newTemplate.newBasicInformation}
                                                        onBasicInformationChange={handleBasicInformationChange} />)}

            {activeStep === 1 && (<ImageFrom onImageChange={handleImageChange}
                                             stepBack={stepBack}
                                             handleNext={handleNext}
                                             oldImage={newTemplate.newTemplateImage.image}/>)}

            {activeStep === 2 && (<TemplateSecurityForm stepBack={stepBack}
                                                        handleNext={handleNext}
                                                        onSecuritySubmit={handleNewTemplateSecurity}
                                                        newTemplateSecurity={newTemplate.newTemplateSecurity}/>)}

            {activeStep === 3 && (<TemplatePreview stepBack={stepBack}
                                                   handleSubmit={handleSubmit}
                                                   newTemplateModel={newTemplate}/>)}
    </Container>
</>
);
}