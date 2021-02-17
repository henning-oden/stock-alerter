import React, { useContext, useEffect, useState } from "react";
import { Button, Grid, makeStyles, Typography } from "@material-ui/core";
import { ComponentContext } from "./ComponentProvider";
import baseUrl from "./util/BaseUrl";


const useStyles = makeStyles({
  createWatch: {
    clear: "none",
    float: "right",
  },
});

const SetStockWatchFormComponent = (setCurrentComponent) => {
  setCurrentComponent('watchForm');
}

const AvailableStocks = () => {
  const {currentComponent, setCurrentComponent} = useContext(ComponentContext);
  const [stocks, setStocks] = useState([]);
  const classes = useStyles();

  useEffect(() => {  
    const fetchStocks = async () => {
      fetch(baseUrl + 'stocks/all', {
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
            <Button className={classes.createWatch} color="primary" onClick={() => SetStockWatchFormComponent(setCurrentComponent)}>
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
