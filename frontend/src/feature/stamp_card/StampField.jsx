import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";

function StempField( {count, stamps} )
{
  return (
      <div>
          <Grid container spacing={2}>
              {Array.from({ length: count }, (_, index) => (
                  <Grid item xs={4} key={index}>
                      <Box sx={{
                          backgroundImage: `url("https://images.nightcafe.studio/jobs/Ku1vjoHEHrx5OGqbtgxL/Ku1vjoHEHrx5OGqbtgxL--1--cyx7c.jpg?tr=w-1600,c-at_max")`,
                          backgroundPosition: 'center',
                          backgroundSize: 'cover',
                          backgroundRepeat: 'no-repeat',
                          height: "12vh",
                          display: 'flex',
                          alignItems: 'center',
                          justifyContent: 'center',
                      }}>
                          <p>{index + 1}</p>
                      </Box>
                  </Grid>
              ))}
          </Grid>
      </div>
  );
}

export default StempField;