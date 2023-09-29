import AppBarComponent from "../core/AppBarComponent";
import {Toolbar} from "@mui/material";

function StampCards()
{
return(
    <div>
        <AppBarComponent showMenuButtonElseBack={false}/>
        <Toolbar/>

            <h1>StampCards</h1>
        </div>
    );
}
export default StampCards;
