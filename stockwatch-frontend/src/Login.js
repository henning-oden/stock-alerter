import React from 'react';
import { useContext } from 'react';
import { Button, Grid, Link, makeStyles, TextField, Typography } from "@material-ui/core";
import { ComponentContext } from "./ComponentProvider";
import { MainContext } from './MainContext';

const SetMainContentComponent = (setCurrentComponent) => {
  setCurrentComponent('main');
}

const useStyles = makeStyles({
    registerButton: {
        float: "right",
    }
});

const Login = (setIsSignedIn, setToken) => {
  const username = document.getElementById("username").value;
  const password = document.getElementById("password").value;
  fetch('http://localhost:8080/users/signin', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      username: username,
      password: password
    })
  }).then(
    (res) => res.json()
    )
    .then((data) => { 
      console.log(data);
      setIsSignedIn(true);
      setToken(data.token);
    }/* save the token... somehow */);
}

const LoginForm = () => {
    const { currentComponent, setCurrentComponent } = useContext(ComponentContext);
    const { signedIn, token } = useContext(MainContext);
    const {isSignedIn, setIsSignedIn} = signedIn;
    const {getToken, setToken} = token;
    const classes = useStyles();
    return (
        <div>
            <Typography variant="h3">
                Login
            </Typography>
    <form className={classes.form} >
        <Grid container direction="column" alignContent="center">
          <Grid item>
            <TextField id="username" label="Username" variant="outlined" />
          </Grid>
          <Grid item>
            <TextField id="password" label="Password" type="password" variant="outlined" />
          </Grid>
          <Grid item>
              <Button className={classes.registerButton} variant="contained" color="primary" edge="end" onClick={() => Login(setIsSignedIn, setToken)}>
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