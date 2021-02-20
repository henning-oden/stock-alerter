import {createContext, useState} from 'react';

export const StockWatchContext = createContext();

export const StockWatchProvider = ({children}) => {
    const [code, setCode] = useState('');
    return (
    <StockWatchContext.Provider value={{code, setCode}}>
        {children}
    </StockWatchContext.Provider>
    );
}