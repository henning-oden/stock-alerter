import React from 'react';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import { Link, Typography } from '@material-ui/core';


function MainContent() {
    return (
        <div>
            <Typography variant="h1">
                    Welcome to Stock Alerter!
                </Typography>
                <Typography variant="h3">
                    What is this?
                </Typography>
                <Typography variant="body1">
                    Stock Alerter is a program that allows users to search for stocks and create watches for specific stocks they want to keep track of. The maximum and minimum prices for the stock will be checked against the current price, and whenever the price has been outside of the interval formed by the prices for a number of price fetches equal to the Alert Threshold for the watch, a notification will be sent to the user. Currently, this can happen at most once per day per user and via e-mail only.
                </Typography>
                <Typography variant="h3">
                    Why Stock Alerter?
                </Typography>
                <Typography variant="body1">
                <Typography variant="body1">
                    The overall purpose of this app is for the author to practice software development skills, learn new skills in software development, and to show what is already learned. As an added bonus, it can potentially help investors monitor stocks they are interested in.
                </Typography>
                </Typography>
                <Typography variant="h3">
                    How does this work?
                </Typography>
                <Typography variant="body1">
                    This program is written using Java for the backend and with a web application written in JavaScript. The database supporting the storage of the data is currently a MySQL 8 database. The back end uses Spring Boot with JPA, Lombok, JWT and the Alpaca Java API for getting stock price information.
                </Typography>
                <Typography variant="h3">
                    What does this cost?
                </Typography>
                <p>
                    At the moment this costs absolutely nothing. The whole project is a test and practice of programming skills. You can contact the author 
                    on <a href="mailto:henning.oden@outlook.com">henning.oden@outlook.com</a> if you wish to discuss anything about the project. Simply put "Stock Alerter: " at the beginning of the subject line.
                </p>
        </div>
    );
}

export default MainContent;