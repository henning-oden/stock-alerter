import React from "react";
import { Grid } from "@material-ui/core";
import Header from "./Header";
import MainContent from "./MainContent";
import RegisterForm from "./Register";
import AvailableStocks from "./AvailableStocks";
import LoginForm from "./Login";
import YourStockWatches from "./YourStockWatches";
import StockWatchForm from "./StockWatchForm";

function App() {
  return (
    <Grid container direction="column">
      <Grid item>
        <Header />
      </Grid>
      <Grid item container>
        <Grid item xs={0} sm={2} />
        <Grid item xs={12} sm={8}>
          <StockWatchForm />
        </Grid>
        <Grid item xs={0} sm={2} />
      </Grid>
    </Grid>
  );
}

export default App;
