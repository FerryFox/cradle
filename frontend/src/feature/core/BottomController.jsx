import {useEffect, useState} from "react";
import {Box, IconButton, useTheme} from "@mui/material";
import MessageIcon from '@mui/icons-material/Message';
import CardGiftcardIcon from '@mui/icons-material/CardGiftcard';
import {useNavigate} from "react-router-dom";
import ArrowCircleUpIcon from '@mui/icons-material/ArrowCircleUp';
import DashboardIcon from '@mui/icons-material/Dashboard';



function BottomController() {
    const [lastScrollTop, setLastScrollTop] = useState(0);
    const [showController, setShowController] = useState(false);
    const navigate = useNavigate();
    const theme = useTheme();

    const scrollToTop = () => {
        window.scrollTo({
            top: 0,
            behavior: 'smooth',
        });
    };

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
        margin: 0,
        padding: 0,
        position: 'fixed',
        bottom: 0,
        width: '100%',
        height : "5vh",
        backgroundColor: theme.palette.primary.main,
        display: 'flex',
        justifyContent: 'space-around',
     }}>

        <IconButton onClick={() => navigate("/dashboard")}>
            <DashboardIcon/>
        </IconButton>

        <IconButton>
            <CardGiftcardIcon/>
        </IconButton>

        <IconButton>
            <MessageIcon/>
        </IconButton>

        <IconButton onClick={() => scrollToTop()}>
            <ArrowCircleUpIcon/>
        </IconButton>
    </Box>
)}
</div>
);
}

export default BottomController;
