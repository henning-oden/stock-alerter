import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import { MainProvider } from "./MainContext";
import { ComponentProvider } from "./ComponentProvider";

ReactDOM.render(
  <React.StrictMode>
    <MainProvider>
      <ComponentProvider>
        <App />
      </ComponentProvider>
    </MainProvider>
  </React.StrictMode>,
  document.getElementById('root')
);
