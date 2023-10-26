import Typography from "@mui/material/Typography";
import Link from "@mui/material/Link";
import * as React from "react";
import Container from "@mui/material/Container";

export default function Copyright() {

return (
<Container sx={{ mt: 8, mb: 4 }}>
        <Typography variant="body2" color="text.secondary" align="center" >
            {'Copyright © '}
            <Link color="inherit" href="https://mui.com/">
                StamPete
            </Link>{' '}
            {new Date().getFullYear()}
            {'.'}
        </Typography>
    </Container>
    );
}