import axios from "axios";
import {Dispatch, SetStateAction} from "react";
import {TemplateModel} from "../model/models";

export const loadTemplateModels = async (
    setTemplates: Dispatch<SetStateAction<TemplateModel[]>>,
    setError: Dispatch<SetStateAction<string>>,
    setLoading: Dispatch<SetStateAction<boolean>>

) => {
    try {
        const response = await axios.get('/api/templates/all/public');
        setTemplates(response.data);
        setLoading(false)
    }

    catch (error) {
        setError("An error occurred while fetching data.");
        setLoading(false);
    }
}

