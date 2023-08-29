import React from 'react';
import { Link } from 'react-router-dom';
import Box from '@mui/material/Box';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import InboxIcon from '@mui/icons-material/Inbox';

function HomePage() {
return (
<div className="home-container">
    <h1>Welcome to StamPete</h1>

    <Box sx={{ width: '100%', maxWidth: 360, bgcolor: 'background.paper' }}>
        <List>
            <ListItem>
                <ListItemButton>
                    <ListItemIcon><InboxIcon /></ListItemIcon>
                    <Link to="/signin">Sign In</Link>
                </ListItemButton>
            </ListItem>
            <ListItem>
                <ListItemButton>
                    <ListItemIcon><InboxIcon /></ListItemIcon>
                    <Link to="/signup">Sign Up</Link>
                </ListItemButton>
            </ListItem>
        </List>
    </Box>
</div>
    );
}

export default HomePage;