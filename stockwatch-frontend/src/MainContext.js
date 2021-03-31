import React from 'react';
import { createContext, useReducer } from 'react';

export const MainContext = createContext();

const initialState = {
    isLoggedIn: false,
    token: "Not set"
};

const reducer = (state, action) => {
    return {
        isLoggedIn: action.isLoggedIn,
        token: action.token
    };
}

export const MainProvider = ({ children }) => {
    const [state, dispatch] = useReducer(reducer, initialState);
    const providerValue = React.useMemo(() => {
        return { state, dispatch };
    }, [state, dispatch]);
    return (
        <MainContext.Provider value={providerValue}>
            { children }
        </MainContext.Provider>
    );
}