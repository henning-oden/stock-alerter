import React from 'react';
import { Button, Grid, makeStyles, TextField, Typography } from "@material-ui/core";

const useStyles = makeStyles({
    registerButton: {
        float: "right",
    }
});

function LoginForm() {
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
              <Button className={classes.registerButton} variant="contained" color="primary" edge="end">
                  Login
              </Button>
          </Grid>
        </Grid>
      </form>
      </div>
    );
}

export default LoginForm;