import {Divider, IconButton, List, ListItem, ListItemButton, ListItemIcon, ListItemText, styled} from "@mui/material";
import ChevronLeftIcon from "@mui/icons-material/ChevronLeft";
import DashboardCustomizeIcon from "@mui/icons-material/DashboardCustomize";
import InboxIcon from "@mui/icons-material/Inbox";
import MailIcon from "@mui/icons-material/Mail";
import Drawer from "@mui/material/Drawer";
import * as React from "react";

const DrawerHeader = styled('div')(({ theme }) => ({
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'flex-end',
    padding: theme.spacing(0, 1),
    // necessary for content to be below app bar
    ...theme.mixins.toolbar,
}));

function DrawerComponent({ open, toggleDrawer, navigate })
{
    const drawerWidth = 240;

    return (
        <Drawer sx={{width: drawerWidth, flexShrink: 0, '& .MuiDrawer-paper': {width: drawerWidth, boxSizing: 'border-box',
            },}} variant="persistent" anchor="left" open={open}>
            <DrawerHeader>
                <IconButton onClick={toggleDrawer}><ChevronLeftIcon />
                </IconButton>
            </DrawerHeader>
            <Divider />

            <List>
                <ListItem key={'Templates'} disablePadding onClick={() => navigate('/templates')}>
                    <ListItemButton>
                        <ListItemIcon>
                            <DashboardCustomizeIcon />
                        </ListItemIcon>
                        <ListItemText primary={'Templates'} secondary={'search for stamp cards'} />
                    </ListItemButton>
                </ListItem>
            </List>
            <Divider />


            <List>
                <ListItem key={'Templates'} disablePadding onClick={() => navigate('/templates/owned')}>
                    <ListItemButton>
                        <ListItemIcon>
                            <DashboardCustomizeIcon />
                        </ListItemIcon>
                        <ListItemText primary={'My Templates'} secondary={'Visit your Templates'} />
                    </ListItemButton>
                </ListItem>
            </List>
            <Divider />


            <List>
                {['Inbox', 'Starred', 'Send email', 'Drafts'].map((text, index) => (
                    <ListItem key={text} disablePadding>
                        <ListItemButton>
                            <ListItemIcon>
                                {index % 2 === 0 ? <InboxIcon /> : <MailIcon />}
                            </ListItemIcon>
                            <ListItemText primary={text} />
                        </ListItemButton>
                    </ListItem>
                ))}
            </List>
            <Divider />
            <List>
                {['All mail', 'Trash', 'Spam'].map((text, index) => (
                    <ListItem key={text} disablePadding>
                        <ListItemButton>
                            <ListItemIcon>
                                {index % 2 === 0 ? <InboxIcon /> : <MailIcon />}
                            </ListItemIcon>
                            <ListItemText primary={text} />
                        </ListItemButton>
                    </ListItem>
                ))}
            </List>
        </Drawer>
    );
}
export default DrawerComponent;