import React, { useContext, useEffect } from "react";
import { Button, Grid, makeStyles, TextField, Typography } from "@material-ui/core";
import baseUrl from "./util/BaseUrl";
import { ComponentContext } from "./ComponentProvider";
import { StockWatchContext } from "./StockWatchContext";
import { MainContext } from "./MainContext";
import StockWatchForm from "./StockWatchForm";
import HandleError from './util/HandleError';
import ErrorDisplay from "./ErrorDisplay";

const useStyles = makeStyles({
  createButton: {
    float: "right",
  },
  stockWatchForm: {
    alignContent: "center",
  },
});


const CreateStockWatch = () => {
  const classes = useStyles();
  const {currentComponent, setCurrentComponent} = useContext(ComponentContext);
  const {state: stockState, dispatch: stockDispatch} = useContext(StockWatchContext);
  const {state, dispatch} = useContext(MainContext);
  
  const SubmitForm = () => {
    const stockCode = document.getElementById("stockCode").value;
    const maxPrice = Number(document.getElementById("maxPrice").value);
    const minPrice = Number(document.getElementById("minPrice").value);
    const alertThreshold = Number(document.getElementById("alertThreshold").value);
    if (minPrice > maxPrice) {
      HandleError("The max price needs to be higher than the min price.");
    }
    else {
        let endpointUrl = stockState.editing? 'stocks/watch?id=' + stockState.id :  'stocks/watch'
      fetch(baseUrl + endpointUrl, {
        method: stockState.editing ? "PUT" : "POST",
        headers: {
          'Authorization': 'Bearer ' + state.token,
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            stockCode: stockCode,
            minPrice: minPrice,
            maxPrice: maxPrice,
            alertThreshold: alertThreshold
        })
      })
      .then((res) => res.json())
      .then((data) => {
        console.log(data);
        stockDispatch({
            editing: false,
            code: ''
        });
        setCurrentComponent('watches');
      })
      .catch((err) => HandleError('Failed to create stock watch: ' + err));
    }
  }

  useEffect(() => {
      if(stockState.editing) {
          fetch(baseUrl + 'stocks/watch?id=' + stockState.id, {
            method: "GET",
            headers: {
                'Authorization': 'Bearer ' + state.token,
                'Accept': 'application/json'
            }
          }).then((res) => res.json())
          .then((data) => {
            document.getElementById("stockCode").value = data.stockCode;
            document.getElementById("maxPrice").value = data.maxPrice;
            document.getElementById("minPrice").value = data.minPrice;
            document.getElementById("alertThreshold").value = data.alertThreshold;
          })
          .catch((error) => {
              HandleError('Error fetching stock watch: ' + error);
          })
      }
  })

  return (
    <div>
      <ErrorDisplay/>
      {StockWatchForm((() => SubmitForm()))}
    </div>
  );
};

export default CreateStockWatch;