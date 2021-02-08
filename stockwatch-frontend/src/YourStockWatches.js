import React from 'react';
import { Button, Divider, Grid, makeStyles, Typography }  from "@material-ui/core";
import StockWatchForm from './StockWatchForm';

const getStockWatches = (userId) => {
    // todo: Actually fetch from detabase rather than hard-code
    const watches = [
        {
            id: 1,
            code: "GOOGL",
            minPrice: 2060,
            maxPrice: 2065,
        },
        {
            id: 2,
            code: "AAPL",
            minPrice: 134,
            maxPrice: 135,
        }
    ];
    return watches;
}

const useStyles = makeStyles({
    stockCode: {
        fontSize: "16px",
        fontWeight: "bold"
    },
    priceLimits: {
        fontSize: "14px",
        fontWeight: "bold",
        color: "#aaaaaa",
    }
})

const YourStockWatches = () => {
    const classes = useStyles();
    return (
        <div>
            <Grid container direction="column">
                {getStockWatches(0).map((stockWatch, index) => {
                    <Grid item container direction="row">
                        <Grid item xs={4} sm={8}>
                            <Typography className={classes.stockCode}>
                                {stockWatch.code}
                            </Typography>
                            <br />
                            <Typography className={classes.priceLimits}>
                                Max price: {stockWatch.maxPrice} /t Min price: {stockWatch.minPrice}
                            </Typography>
                        </Grid>
                        <Grid item xs={4} sm={2}>
                            <Button variant="contained" color="primary">Edit</Button>
                        </Grid>
                        <Grid item xs={4} sm={2}>
                            <Button variant="contained" color="secondary">Delete</Button>
                        </Grid>
                    </Grid>
                })}
            </Grid>
        </div>
    )
}

export default StockWatchForm;