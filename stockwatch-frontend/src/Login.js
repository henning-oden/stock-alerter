import React from 'react';
import { useContext } from 'react';
import { Button, Grid, Link, makeStyles, TextField, Typography } from "@material-ui/core";
import { ComponentContext } from "./ComponentProvider";

const SetMainContentComponent = (setCurrentComponent) => {
  setCurrentComponent('main');
}

const useStyles = makeStyles({
    registerButton: {
        float: "right",
    }
});

const LoginForm = () => {
    const { currentComponent, setCurrentComponent } = useContext(ComponentContext);
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
            <TextField id="password" label="Password" variant="outlined" />
          </Grid>
          <Grid item>
              <Button className={classes.registerButton} variant="contained" color="primary" edge="end" onClick={() => alert('Not implemented')}>
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