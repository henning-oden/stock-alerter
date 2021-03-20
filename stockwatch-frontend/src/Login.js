import React from 'react';
import { useContext } from 'react';
import { Button, Grid, Link, makeStyles, TextField, Typography } from "@material-ui/core";
import { ComponentContext } from "./ComponentProvider";
import { MainContext } from './MainContext';
import baseUrl from './util/BaseUrl';
import ErrorDisplay from './ErrorDisplay';
import HandleError from './util/HandleError';

const SetMainContentComponent = (setCurrentComponent) => {
  setCurrentComponent('main');
}

const useStyles = makeStyles({
    registerButton: {
        float: "right",
    }
});

const Login = (dispatch, setCurrentComponent) => {
  const username = document.getElementById("username").value;
  const password = document.getElementById("password").value;
  fetch(baseUrl + 'users/signin', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json',
    },
    // Password encryption and comparison should probably be refactored and put in the front end to not pass plain text passwords over the network more than absolutely necessary.
    body: JSON.stringify({
      username: username,
      password: password
    })
  }).then(
    (res) => {
    if (!res.ok) {
      throw Error(res.status);
    }
    return res.json();
    })
    .catch((err) => {
      HandleError(err);
      return;
    })
    .then((data) => { 
      console.log(data);
      if (data) {
      const jwtToken = data.token;
      const mainProps = {
        isLoggedIn: true,
        token: jwtToken
      };
      dispatch(mainProps);
      return true;
    }
    return false;
    })
    .then((success) => {
      if (success) {
      setCurrentComponent('main');
      }
    });
}

const LoginForm = () => {
    const { currentComponent, setCurrentComponent } = useContext(ComponentContext);
    const { mainState, dispatch } = useContext(MainContext);
    const classes = useStyles();
    return (
        <div>
            <Typography variant="h3">
                Login
            </Typography>
            <ErrorDisplay/>
    <form className={classes.form} >
        <Grid container direction="column" alignContent="center">
          <Grid item>
            <TextField id="username" label="Username" variant="outlined" />
          </Grid>
          <Grid item>
            <TextField id="password" label="Password" type="password" variant="outlined" />
          </Grid>
          <Grid item>
              <Button className={classes.registerButton} variant="contained" color="primary" edge="end" onClick={() => {Login(dispatch, setCurrentComponent);}}>
                  Login
              </Button>
          </Grid>
        </Grid>
      </form>
      <Button onClick={() => SetMainContentComponent(setCurrentComponent)}>Home</Button>
      </div>
    );
}

export default LoginForm;