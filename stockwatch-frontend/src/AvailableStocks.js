import React from "react";
import { Button, Grid, makeStyles, Typography } from "@material-ui/core";

function getStocks() {
  // Actually retrieve from database later
  const stocks = [
    {
      code: "AAPL",
      commonname: "Apple",
      lastPrice: 134.14,
    },
    {
      code: "GOOGL",
      commonname: "Alphabet Inc Class A",
      lastPrice: 2064.34,
    },
    {
      code: "MSFT",
      commonname: "Microsoft",
      lastPrice: 242.66,
    },
  ];
  return stocks;
}

const useStyles = makeStyles({
  createWatch: {
    clear: "none",
    float: "right",
  },
});

function AvailableStocks() {
  const classes = useStyles();
  return (
    <div>
      <Typography variant="h1">Available Stocks</Typography>
      <Typography variant="body1">
        These stocks are available for watching.
      </Typography>
      <Grid container direction="column">
        {getStocks().map((stock, index) => (
          <Grid item key={index}>
            <Typography variant="h4">{stock.code}</Typography>
            <Typography variant="h5">{stock.commonname}</Typography>
            <Button className={classes.createWatch} color="primary">
              Create Watch
            </Button>
          </Grid>
        ))}
      </Grid>
    </div>
  );
}

export default AvailableStocks;
