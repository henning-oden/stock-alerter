import React, {createContext, useReducer, useState} from 'react';

export const StockWatchContext = createContext();

const reducer = (state, action) => {
    return {
        editing: action.editing,
        code: action.code,
        id: action.id
    };
}

const initialState = {
    editing: false,
    code: '',
    id: 0
}

export const StockWatchProvider = ({children}) => {
    const [state, dispatch] = useReducer(reducer, initialState);
    const providerValue = React.useMemo(() => {
        return { state, dispatch };
    }, [state, dispatch]);
    return (
    <StockWatchContext.Provider value={providerValue}>
        {children}
    </StockWatchContext.Provider>
    );
}