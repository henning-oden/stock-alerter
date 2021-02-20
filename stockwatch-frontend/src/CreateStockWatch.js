import React, { useContext } from "react";
import { Button, Grid, makeStyles, TextField, Typography } from "@material-ui/core";
import baseUrl from "./util/BaseUrl";
import { ComponentContext } from "./ComponentProvider";
import { SelectedStockContext } from "./SelectedStockContext";
import { MainContext } from "./MainContext";
import StockWatchForm from "./StockWatchForm";

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
  const {code, setCode} = useContext(SelectedStockContext);
  const {state, dispatch} = useContext(MainContext);
  
  const SubmitForm = () => {
    const stockCode = document.getElementById("stockCode").value;
    const maxPrice = Number(document.getElementById("maxPrice").value);
    const minPrice = Number(document.getElementById("minPrice").value);
    const alertThreshold = Number(document.getElementById("alertThreshold").value);
    if (minPrice > maxPrice) {
      alert("The max price needs to be higher than the min price.");
    }
    else {
      fetch(baseUrl + 'stocks/create-watch', {
        method: "POST",
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
        setCode('');
        setCurrentComponent('watches');
      })
      .catch((err) => alert('Failed to create stock watch: ' + err));
    }
  }

  return (
      StockWatchForm((() => SubmitForm()), false)
  );
};

export default CreateStockWatch;