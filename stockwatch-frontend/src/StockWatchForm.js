import React from "react";
import { Button, Grid, makeStyles, TextField, Typography } from "@material-ui/core";

const useStyles = makeStyles({
  createButton: {
    float: "right",
  },
  stockWatchForm: {
    alignContent: "center",
  },
});

const StockWatchForm = (stockCode, stockWatchId) => {
  const watchIdNumber = stockWatchId ? Number.parseInt(stockWatchId) : 0;
  const classes = useStyles();
  return (
    <div>
        <Typography variant="h3" align="center">
            Create Stock Watch
        </Typography>
      <form className={classes.stockWatchForm}>
        <input type="hidden" id="stockCode" value={stockCode} />
        <input type="hidden" id="stockWatchId" value={watchIdNumber} />
        <Grid container direction="column" alignContent="center">
          <Grid item>
            <TextField variant="outlined" id="maxPrice" label="Max Price" />
          </Grid>
          <Grid item>
            <TextField variant="outlined" id="minPrice" label="Min Price" />
          </Grid>
          <Grid item>
            <Button variant="contained" classname={classes.createButton}>
              Create Watch
            </Button>
          </Grid>
        </Grid>
      </form>
    </div>
  );
}

export default StockWatchForm;
