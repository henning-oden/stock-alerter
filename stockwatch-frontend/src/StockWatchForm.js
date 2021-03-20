import React, { useContext } from "react";
import { Button, Grid, makeStyles, TextField, Typography } from "@material-ui/core";
import baseUrl from "./util/BaseUrl";
import { ComponentContext } from "./ComponentProvider";
import { StockWatchContext } from "./StockWatchContext";

const useStyles = makeStyles({
  createButton: {
    float: "right",
  },
  stockWatchForm: {
    alignContent: "center",
  },
});


const StockWatchForm = (Callback) => {
  const classes = useStyles();
  const { currentComponent, setCurrentComponent } = useContext(ComponentContext);
  const {state, dispatch} = useContext(StockWatchContext);
  const headingText = state.editing? 'Edit Stock Watch' : 'Create Stock Watch';
  const buttonText = state.editing? 'Edit Watch' : 'Create Watch';

  return (
    <div>
        <Typography variant="h3" align="center">
            {headingText}
        </Typography>
        <Typography variant="body1" align="center">
          Chosen stock: {state.code}
        </Typography>
      <form className={classes.stockWatchForm}>
        <input type="hidden" id="stockCode" value={state.code} />
        <Grid container direction="column" alignContent="center">
          <Grid item>
            <TextField variant="outlined" id="maxPrice" label="Max Price" InputLabelProps={{shrink: true}} type="number"/>
          </Grid>
          <Grid item>
            <TextField variant="outlined" id="minPrice" label="Min Price" InputLabelProps={{shrink: true}} type="number"/>
          </Grid>
          <Grid item>
            <TextField variant="outlined" id="alertThreshold" label="Alert Threshold" InputLabelProps={{shrink: true}} type="number"/>
          </Grid>
          <Grid item>
            <Button variant="contained" className={classes.createButton} onClick={() => Callback()}>
              {buttonText}
            </Button>
          </Grid>
        </Grid>
      </form>
      <Button align="center" onClick={() => setCurrentComponent('main')}>Home</Button>
    </div>
  );
}

export default StockWatchForm;
