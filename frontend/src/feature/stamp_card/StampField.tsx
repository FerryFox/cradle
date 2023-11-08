import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import {Paper} from "@mui/material";
import {DEFAULT_ELEVATION} from "../../globalConfig";
import {StampFieldModel} from "./model/models";
import React from "react";
// @ts-ignore
import paperImage from './Paper.jpg';
// @ts-ignore
import redCross from './redcross.svg';

type StampFieldProps = {
    stampFields: StampFieldModel[];
    onStampAttempt: (stampField: StampFieldModel) => void;
}

export default function StampField( {stampFields ,  onStampAttempt} : StampFieldProps )
{
    return (
      <Paper elevation={DEFAULT_ELEVATION} sx={{px : 2, py : 2 ,
          backgroundImage: `url(${paperImage})`,
          backgroundSize: 'cover',
          backgroundRepeat: 'no-repeat',
          backgroundPosition: 'center',}}>
          <Grid container spacing={2}>
              {stampFields?.map ((stampField) =>
                  (
                  <Grid item xs={4} key={stampField.id}>
                      {
                          stampField.stamped ? (
                              <Box sx={{
                                  backgroundImage: `url(${redCross})`,
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
      </Paper>
  );
}

