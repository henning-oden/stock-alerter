import React from "react";
import { makeStyles } from "@material-ui/styles";
import TextField from "@material-ui/core/TextField";
import { Button, Grid, Typography } from "@material-ui/core";
import baseUrl from "./util/BaseUrl";
import ErrorDisplay from "./ErrorDisplay";
import HandleError from "./util/HandleError";

const useStyles = makeStyles({
  formContainer: {
    margin: "auto",
  },
  registerButton: {
      float: "right",
  }
});

const formSubmit = () => {
  const username = document.getElementById("username").value;
  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value;
  const confirmPassword = document.getElementById("confirmPassword").value;
  if (confirmPassword !== password) {
    HandleError('The password confirmation did not match the original password');
  }
  else {
    const body = {
      username: username,
      password: password,
      email: email
    };
    fetch(baseUrl + 'users/signup', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(body)
    })
    .then((res) => res.json())
    .then((data) => {
      if(data.result === 'Failure') {
        HandleError('An error occurred while registering the user: ' + data.message);
      }
      else {
        alert(data.message);
      }
    })
    .catch(() => {
      HandleError('An error occurred while registering the user.');
    }) 
  }
}

const RegisterForm = () => {
  const classes = useStyles();
  return (
    <div className="formContainer">
      <Typography variant="h3" align="center">Register user</Typography>
      <ErrorDisplay/>
      <form className={classes.form} >
        <Grid container direction="column" alignContent="center">
          <Grid item>
            <TextField id="username" label="Username" variant="outlined" />
          </Grid>
          <Grid item>
            <TextField id="email" label="Email" variant="outlined" />
          </Grid>
          <Grid item>
            <TextField type="password" id="password" label="Password" variant="outlined" />
          </Grid>
          <Grid item>
            <TextField
              type="password"
              id="confirmPassword"
              label="Confirm Password"
              variant="outlined"
            />
          </Grid>
          <Grid item>
              <Button className={classes.registerButton} variant="contained" color="primary" edge="end" onClick={() => {formSubmit()}}>
                  Register
              </Button>
          </Grid>
        </Grid>
      </form>
    </div>
  );
}

export default RegisterForm;
