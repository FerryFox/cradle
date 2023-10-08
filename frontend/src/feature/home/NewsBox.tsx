import Box from "@mui/material/Box";
import IntroductionCard from "./IntroductionCard";
import React from "react";
import {News} from "./model/models";

type NewsBoxProps = {
    news: News[];
}

export default function NewsBox( {news} : NewsBoxProps)
{
    return(
    <Box>
        {news.map((news, index) => (
            <IntroductionCard key={news.id} order={index + 1} news={news}></IntroductionCard>
        ))}
    </Box>
    )
}