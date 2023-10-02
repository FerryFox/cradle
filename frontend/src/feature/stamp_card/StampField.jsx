import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";

function StempField( {stampFields,  onStampAttempt} )
{
  return (
      <div>
          <Grid container spacing={2}>
              {stampFields &&stampFields.map ((stampField) =>
                  (
                  <Grid item xs={4} key={stampField.id}>
                      {
                          stampField.isStamped ? (
                              <Box sx={{
                                  backgroundImage: `url(${stampField.stampedImageUrl})`,
                                  backgroundPosition: 'center',
                                  backgroundSize: 'cover',
                                  backgroundRepeat: 'no-repeat',
                                  height: "12vh",
                                  display: 'flex',
                                  alignItems: 'center',
                                  justifyContent: 'center'}}>

                                  <p>{stampField.index}</p>
                              </Box>
                        ) :
                        (
                          <Box onClick={() => onStampAttempt(stampField)} sx={{
                              backgroundImage: `url(${stampField.emptyImageUrl})`,
                              backgroundPosition: 'center',
                              backgroundSize: 'cover',
                              backgroundRepeat: 'no-repeat',
                              height: "12vh",
                              display: 'flex',
                              alignItems: 'center',
                              justifyContent: 'center'}}>

                              <p>{stampField.index}</p>
                          </Box>
                        )
                      }
                  </Grid>
              ))}
          </Grid>
      </div>
  );
}

export default StempField;