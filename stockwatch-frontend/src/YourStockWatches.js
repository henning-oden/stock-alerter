import React, { useEffect, useContext, useState } from 'react';
import { Button, Divider, Grid, makeStyles, Typography }  from "@material-ui/core";
import { MainContext } from './MainContext';
import { ComponentContext } from './ComponentProvider';
import BaseUrl from './util/BaseUrl';
import { StockWatchContext } from './StockWatchContext';

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
    const { stockState, dispatch: stockDispatch } = useContext(StockWatchContext)
    const { state, dispatch } = useContext(MainContext);
    const { currentComponent, setCurrentComponent } = useContext(ComponentContext);
    const [ watches, setWatches ] = useState([]);
    const token = state.token;
    const classes = useStyles();

    useEffect(() => {
        const fetchWatches = async () => {
        fetch(BaseUrl + 'stocks/get-watches', {
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + token,
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(res => res.json())
        .then(data => {
            console.log("Setting data...");
            console.log(data);
            setWatches(data);});
    };

    fetchWatches();
    return setWatches([]);
    }, []);

    const deleteWatch = (id) => {
        const response = window.confirm("Are you sure you want to delete this watch?");
        if (response) {
            fetch(BaseUrl + 'stocks/delete-watch?id=' + id, {
                method: "DELETE",
                headers: {
                    'Authorization': 'Bearer ' + token,
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            }).then(res => res.json())
            .then(data => {
                if (data.result === 'Success') {
                    const remainingWatches = watches.filter(w => w.id !== id);
                    setWatches(remainingWatches);
                }
                else {
                    alert('Could not delete watch: ' + data.message);
                }
            })
            .catch(() => alert("Could not delete watch!"));
        }
    }

    const editWatch = (stockWatch) => {
        stockDispatch({
            editing: true,
            code: stockWatch.stockCode,
            id: stockWatch.id
        });
        setCurrentComponent('createWatch');
    }
    return (
        <div>
            <Typography variant="h3">Your Stock Watches</Typography>
            <Grid container direction="column">
                {watches.map(stockWatch => {
                    return (<Grid item container direction="row">
                        <Grid item xs={4} sm={8}>
                            <Typography className={classes.stockCode}>
                                {stockWatch.stockCode}
                            </Typography>
                            <br />
                            <Typography className={classes.priceLimits}>
                                Max price: {stockWatch.maxPrice} | Min price: {stockWatch.minPrice}
                            </Typography>
                        </Grid>
                        <Grid item xs={4} sm={2}>
                            <Button variant="contained" color="primary" onClick={() => editWatch(stockWatch)}>Edit</Button>
                        </Grid>
                        <Grid item xs={4} sm={2}>
                            <Button variant="contained" color="secondary" onClick={() => deleteWatch(stockWatch.id)}>Delete</Button>
                        </Grid>
                    </Grid>)
                })}
                <Button onClick={() => setCurrentComponent('main')}>Home</Button>
            </Grid>
        </div>
    )
}

export default YourStockWatches;