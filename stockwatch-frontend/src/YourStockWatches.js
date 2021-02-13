import React from 'react';
import { useContext } from 'react';
import { Button, Divider, Grid, makeStyles, Typography }  from "@material-ui/core";
import { MainContext } from './MainContext';

const getStockWatches = (token) => {
    fetch('http://localhost:8080/stocks/get-watches', {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token,
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(res => {res.json();})
    .then(data => {return data;});
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
    const { state, dispatch } = useContext(MainContext);
    const token = state.token;
    const classes = useStyles();
    return (
        <div>
            <Grid container direction="column">
                {getStockWatches(token).map((stockWatch, index) => {
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

export default YourStockWatches;