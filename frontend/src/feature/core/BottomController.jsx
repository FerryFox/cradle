import {useEffect, useState} from "react";
import {Box, IconButton, useTheme} from "@mui/material";
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import MessageIcon from '@mui/icons-material/Message';
import CardGiftcardIcon from '@mui/icons-material/CardGiftcard';
import AccountBoxIcon from '@mui/icons-material/AccountBox';
import {useNavigate} from "react-router-dom";


function BottomController() {
    const [lastScrollTop, setLastScrollTop] = useState(0);
    const [showController, setShowController] = useState(false);
    const navigate = useNavigate();
    const theme = useTheme();

    useEffect(() => {
        const handleScroll = () => {
            const currentScrollTop =  document.documentElement.scrollTop;
            if (currentScrollTop < lastScrollTop) {
                // Scrolling Up
                setShowController(true);
            } else {
                // Scrolling Down
                setShowController(false);
            }
            setLastScrollTop(currentScrollTop <= 0 ? 0 : currentScrollTop);
        };

        window.addEventListener('scroll', handleScroll);
        return () => window.removeEventListener('scroll', handleScroll);
    }, [lastScrollTop]);

return (
<div>
{showController && (
    <Box sx={{
        position: 'fixed',
        bottom: 0,
        width: '100%',
        height : "5vh",
        backgroundColor: theme.palette.primary.main,
        display: 'flex',
        justifyContent: 'space-around',
        padding: '10px'}}>

        <IconButton onClick={() => navigate(-1)}>
            <ArrowBackIcon/>
        </IconButton>

        <IconButton>
            <CardGiftcardIcon/>
        </IconButton>

        <IconButton>
            <MessageIcon/>
        </IconButton>

        <IconButton>
            <AccountBoxIcon/>
        </IconButton>
    </Box>
)}
</div>
);
}

export default BottomController;
