import React from "react";
import { makeStyles } from "@material-ui/styles";
import TextField from "@material-ui/core/TextField";
import { Button, Grid, Typography } from "@material-ui/core";

const useStyles = makeStyles({
  formContainer: {
    margin: "auto",
  },
  registerButton: {
      float: "right",
  }
});

const RegisterForm = () => {
  const classes = useStyles();
  return (
    <div className="formContainer">
      <Typography variant="h3" align="center">Register user</Typography>
      <form className={classes.form} >
        <Grid container direction="column" alignContent="center">
          <Grid item>
            <TextField id="username" label="Username" variant="outlined" />
          </Grid>
          <Grid item>
            <TextField id="email" label="Email" variant="outlined" />
          </Grid>
          <Grid item>
            <TextField id="password" label="Password" variant="outlined" />
          </Grid>
          <Grid item>
            <TextField
              id="confirmPassword"
              label="Confirm Password"
              variant="outlined"
            />
          </Grid>
          <Grid item>
              <Button className={classes.registerButton} variant="contained" color="primary" edge="end">
                  Register
              </Button>
          </Grid>
        </Grid>
      </form>
    </div>
  );
}

export default RegisterForm;
