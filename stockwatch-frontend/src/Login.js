import React from "react";
import { useContext } from "react";
import {
  Button,
  Grid,
  Link,
  makeStyles,
  TextField,
  Typography,
} from "@material-ui/core";
import { ComponentContext } from "./ComponentProvider";
import { MainContext } from "./MainContext";

const SetMainContentComponent = (setCurrentComponent) => {
  setCurrentComponent("main");
};

const useStyles = makeStyles({
  registerButton: {
    float: "right",
  },
});

const Login = (dispatch) => {
  const username = document.getElementById("username").value;
  const password = document.getElementById("password").value;
  fetch("http://localhost:8080/users/signin", {
    method: "POST",
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json",
    },
    // Password encryption and comparison should probably be refactored and put in the front end to not pass plain text passwords over the network more than absolutely necessary.
    body: JSON.stringify({
      username: username,
      password: password,
    }),
  })
    .then((res) => res.json())
    .then((data) => {
      console.log(data);
      const jwtToken = data.token;
      const mainProps = {
        isLoggedIn: true,
        token: jwtToken,
      };
      dispatch(mainProps);
    });
};

const LoginForm = () => {
  const { currentComponent, setCurrentComponent } = useContext(
    ComponentContext
  );
  const { mainState, dispatch } = useContext(MainContext);
  const classes = useStyles();
  return (
    <div>
      <Typography variant="h3">Login</Typography>
      <form className={classes.form}>
        <Grid container direction="column" alignContent="center">
          <Grid item>
            <TextField id="username" label="Username" variant="outlined" />
          </Grid>
          <Grid item>
            <TextField
              id="password"
              label="Password"
              type="password"
              variant="outlined"
            />
          </Grid>
          <Grid item>
            <Button
              className={classes.registerButton}
              variant="contained"
              color="primary"
              edge="end"
              onClick={() => {
                Login(dispatch);
                /*setCurrentComponent("watches")*/;
              }}
            >
              Login
            </Button>
          </Grid>
        </Grid>
      </form>
      <Button onClick={() => SetMainContentComponent(setCurrentComponent)}>
        Home
      </Button>
    </div>
  );
};

export default LoginForm;
