import React from "react";
import {
  AppBar,
  Button,
  IconButton,
  makeStyles,
  Toolbar,
  Typography,
} from "@material-ui/core";
import MenuIcon from "@material-ui/icons/Menu";

const useStyles = makeStyles({
  body: {
    margin: 0,
  },
  title: {
    flexGrow: 1,
  },
});

const Header = () => {
  const classes = useStyles();
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
        <Button color="inherit" edge="end">
          Login
        </Button>
      </Toolbar>
    </AppBar>
  );
}

export default Header;
