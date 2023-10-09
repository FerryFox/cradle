import React from "react";
import AppBarComponent from "./AppBarComponent";
import BottomController from "./BottomController";
import DrawerComponent from "./DrawerComponent";
import {useNavigate} from "react-router-dom";

type ControllerProps = {
    title: string;
    showBackButton?: boolean;
    showSecondLine?: boolean;
};

export default function Controller({ title, showBackButton = false , showSecondLine = false} :  ControllerProps )
{
    const navigateTo = useNavigate();
    const [open, setOpen] = React.useState(false);
    const toggleDrawer = () => {setOpen(!open);}

    return (
        <div>
            <AppBarComponent
                title={title}
                toggleDrawer={toggleDrawer}
                showBackButton={showBackButton}
                showSecondLine={showSecondLine}/>

            <DrawerComponent
                open={open}
                toggleDrawer={toggleDrawer}
                navigate={navigateTo} />

            <BottomController/>
        </div>
        );
}