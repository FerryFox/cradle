import Grid from "@mui/material/Grid";

function StempsField( {stamps} )
{
  return (
      <div>
          <Grid container spacing={2}>
              {stamps.map((stamp, index) => (
                  <Grid item key={index}>
                     Test
                  </Grid>
              ))}
          </Grid>
      </div>
  );
}