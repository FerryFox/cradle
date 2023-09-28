import {Box, IconButton} from "@mui/material";
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import {useNavigate} from "react-router-dom";

function TopController( {position = 'flex'})
{
    const navigate = useNavigate();


    return (
        <div className="bottom-controller">
            <Box sx={{
                border: '1px solid red',
                    height: '5vh',
                    width: '100%',
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'flex-start',
                    justifyContent: 'center',
                    position: position,
                    top: 0,
                    zIndex: 1000
            }}>
                <IconButton color={"primary"}
                    onClick={() => navigate(-1)}>
                    <ArrowBackIcon/>
                </IconButton>
            </Box>
        </div>
    );
}
export default TopController;