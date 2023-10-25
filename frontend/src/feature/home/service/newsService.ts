import axios from 'axios';
import {News} from "../model/models";
import React from "react";

export const loadNews = async (
    setNews: React.Dispatch<React.SetStateAction<News[]>>,
    setError: React.Dispatch<React.SetStateAction<string | null>>,
    setLoading: React.Dispatch<React.SetStateAction<boolean>>
) =>
{
    try
    {
        const response = await axios.get('/api/news/home');
        setNews(response.data);
        setLoading(false)
    }
    catch (error) {
        if (error instanceof Error) {
            setError(error.message);
        } else {
            setError('Failed to fetch news.');
        }
    }
};
