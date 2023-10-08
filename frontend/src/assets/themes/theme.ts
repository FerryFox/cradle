import {createTheme} from "@mui/material";
export const darkTheme = createTheme(
    {
        breakpoints:
            {
                values:
                    {
                        xs: 0,
                        sm: 600,
                        md: 900,
                        lg: 1200,
                        xl: 1536,
                    },
            },
        palette:
            {
                mode: 'dark',
                primary: {main: '#85DCB0'},
                secondary: {main: '#E27D60'},
            }
    }
);

export const lightTheme = createTheme(
    {
        breakpoints:
            {
                values:
                    {
                        xs: 0,
                        sm: 600,
                        md: 900,
                        lg: 1200,
                        xl: 1536,
                    },
            },
        palette:
            {
                primary: {main: '#85DCB0'},
                secondary: {main: '#E27D60'},
                info: {main: '#E8A97C'},
                success: {main: '#41B3A3'},
                warning: {main: '#C38D9E'},
                error: {main: '#e040fb'},
            },
        typography: {
            fontFamily: '"Gabarito", sans-serif',
            h1: {
                fontSize: '2.25rem', // adjust as needed
            },
            h2: {
                fontSize: '2rem', // adjust as needed
            },
            h3: {
                fontSize: '1.75rem', // adjust as needed
            },
            h4: {
                fontSize: '1.5rem', // adjust as needed
            },
            h5: {
                fontSize: '1.25rem', // adjust as needed
            },
            h6: {
                fontSize: '1rem', // adjust as needed
            },
            body1: {
                fontSize: '1rem', // adjust as needed
            },
            body2: {
                fontSize: '0.875rem', // adjust as needed
            }
        },
    }
);