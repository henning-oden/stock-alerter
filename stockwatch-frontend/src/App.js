import React from "react";
import { useContext, useState } from "react";
import { Grid } from "@material-ui/core";
import Header from "./Header";
import MainContent from "./MainContent";
import RegisterForm from "./Register";
import AvailableStocks from "./AvailableStocks";
import LoginForm from "./Login";
import YourStockWatches from "./YourStockWatches";
import { ComponentContext } from "./ComponentProvider";
import { StockWatchProvider } from "./StockWatchContext";
import CreateStockWatch from "./CreateStockWatch";

const GetActiveComponent = (activeComponent) => {
  return DetermineActiveComponent(activeComponent);
};

const DetermineActiveComponent = (activeComponent) => {
  switch (activeComponent) {
    case "main":
      return <MainContent />;
    case "login":
      return <LoginForm />;
    case "availableStocks":
      return (
      <StockWatchProvider>
        <AvailableStocks />
      </StockWatchProvider>
        );
    case "watches":
      return (
        <StockWatchProvider>
      <YourStockWatches />
      </StockWatchProvider>);
    case "createWatch":
      return (
        <StockWatchProvider>
      <CreateStockWatch />
      </StockWatchProvider>
      );
    case "register":
      return <RegisterForm />;
    default:
      return null;
  }
};

const App = () => {
  const { currentComponent, setCurrentComponent } = useContext(
    ComponentContext
  );
  let currentlyActiveComponent = GetActiveComponent(currentComponent);
  return (
    <Grid container direction="column">
      <Grid item>
        <Header />
      </Grid>
      <Grid item container>
        <Grid item xs={0} sm={2} />
        <Grid item xs={12} sm={8}>
          {currentlyActiveComponent}
        </Grid>
        <Grid item xs={0} sm={2} />
      </Grid>
    </Grid>
  );
};

export default App;
