import React from 'react';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import MailOutlineIcon from '@mui/icons-material/MailOutline';
import WorkIcon from '@mui/icons-material/Work';

export default function About() {
    return (
        <Box className="about-container" bgcolor="primary.main" padding={3}>
            <Container>
                <Typography variant="h4" gutterBottom>
                    About Me
                </Typography>
                <Typography variant="h6" gutterBottom>
                    Dustin Rasch
                </Typography>
                <Typography variant="body1" gutterBottom>
                    Welcome to my page. My name is Dustin Rasch, and I am actively looking for new job opportunities.
                </Typography>
                <Typography variant="body1" gutterBottom>
                    <MailOutlineIcon color="action" fontSize="small" style={{ verticalAlign: 'middle', marginRight: '8px' }} />
                    Email: <a href="mailto:dustin.rasch@outlook.de">dustin.rasch@outlook.de</a>
                </Typography>
                <Typography variant="body1">
                    <WorkIcon color="action" fontSize="small" style={{ verticalAlign: 'middle', marginRight: '8px' }} />
                    I'm open to job offers and would be delighted to connect with potential employers.
                </Typography>
            </Container>
        </Box>
    );
}
