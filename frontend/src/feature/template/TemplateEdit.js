import React from 'react';
import {useLocation} from "react-router-dom";

function TemplateEdit()
{
    const location = useLocation();
    const templateModel = location.state?.templateModel;

    return (
        <div>
            <h1>Edit Template</h1>
            <p>Template name: {templateModel.name}</p>
        </div>
    );
}

export default TemplateEdit;
