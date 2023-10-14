import React, {useState} from "react";
import {StampCardModel} from "./model/models";
import Controller from "../core/Controller";
import Typography from "@mui/material/Typography";
import {Button, Divider} from "@mui/material";
import {useNavigate} from "react-router-dom";

interface StampCardViewProps {
    stampCard: StampCardModel;
}
export default function StampCardView( { stampCard }: StampCardViewProps)
{
    const navigateTo = useNavigate();

return (
<>
<Controller title={"Stamp Card"}  showBackButton/>

    <Typography variant={"h2"} align={"left"}>
        {stampCard.templateModel.name}
    </Typography>

    <Divider color={"primary"}></Divider>

    <Button onClick={ () => { navigateTo(`/stampcard/details/${stampCard.id}` )}}>
        See Details ...
    </Button>
</>
    )
}