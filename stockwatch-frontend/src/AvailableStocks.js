import React, { useContext, useEffect, useState } from "react";
import { Button, Grid, makeStyles, Typography } from "@material-ui/core";
import { ComponentContext } from "./ComponentProvider";
import baseUrl from "./util/BaseUrl";
import { StockWatchContext } from "./StockWatchContext";


const useStyles = makeStyles({
  createWatch: {
    clear: "none",
    float: "right",
  },
});


const AvailableStocks = () => {
  const {currentComponent, setCurrentComponent} = useContext(ComponentContext);
  const {code, dispatch} = useContext(StockWatchContext);
  const [stocks, setStocks] = useState([]);
  const classes = useStyles();
  
  const GoToStockWatchCreation = (code) => {
    dispatch({editing: false, code: code, id: 0});
    setCurrentComponent('createWatch');
  }

  useEffect(() => {  
    const fetchStocks = async () => {
      fetch(baseUrl + 'stocks/stock', {
        method: 'GET',
        headers: {'Accept': 'application/json',
        'Content-Type': 'application/json'}
      }).then((res) => res.json())
      .then((data) => {
        setStocks(data);
      })
    };
    fetchStocks();
  }, []);

  return (
    <div>
      <Typography variant="h1">Available Stocks</Typography>
      <Typography variant="body1">
        These stocks are available for watching.
      </Typography>
      <Grid container direction="column">
        {stocks.map((stock, index) => {
          return (
          <Grid item key={index}>
            <Typography variant="h4">{stock.code}</Typography>
            <Typography variant="h5">{stock.commonName}</Typography>
            <Button className={classes.createWatch} color="primary" onClick={() => GoToStockWatchCreation(stock.code)}>
              Create Watch
            </Button>
          </Grid>
          );
})}
      </Grid>
    </div>
  );
}

export default AvailableStocks;
