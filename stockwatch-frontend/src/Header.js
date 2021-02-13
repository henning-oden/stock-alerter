import React from "react";
import { useContext } from 'react';
import {
  AppBar,
  Button,
  IconButton,
  makeStyles,
  Toolbar,
  Typography,
} from "@material-ui/core";
import MenuIcon from "@material-ui/icons/Menu";
import { ComponentContext } from "./ComponentProvider";
import { MainContext } from "./MainContext";

const SetLoginComponent = (setCurrentComponent) => {
  setCurrentComponent("login");
}

const SetRegisterComponent = (setCurrentComponent) => {
  setCurrentComponent("register");
}

const SignOut = (setIsSignedIn) => {
  setIsSignedIn(false);
}

const useStyles = makeStyles({
  body: {
    margin: 0,
  },
  title: {
    flexGrow: 1,
  },
});

const Header = () => {
  const { currentComponent, setCurrentComponent } = useContext(ComponentContext);
  const { state, dispatch } = useContext(MainContext);
  console.log(state);
  const isLoggedIn = state.isLoggedIn;
  const classes = useStyles();
  const LoginButton = isLoggedIn ? null : (
    <Button color="inherit" edge="end" onClick={() => {SetLoginComponent(setCurrentComponent);}}>
  Login
</Button>);
const registerButton = isLoggedIn ? null : (
  <Button color="inherit" edge="end" onClick={() => {SetRegisterComponent(setCurrentComponent);}}>
  Register
</Button>);
const signOutButton = isLoggedIn ? (
<Button color="inherit" edge="end" onClick={() => {dispatch({ isLoggedIn: false, token: "Not set"})}}>
Sign out
</Button>
) : null;
  return (
    <AppBar position="static" className={classes.menuBar} color="primary">
      <Toolbar>
        <IconButton
          edge="start"
          className={classes.menuButton}
          color="inherit"
          aria-label="menu"
        >
          <MenuIcon />
        </IconButton>
        <Typography variant="h6" className={classes.title}>
          Stock Alerter
        </Typography>
        {LoginButton}
        {registerButton}
        {signOutButton}
      </Toolbar>
    </AppBar>
  );
}

export default Header;
