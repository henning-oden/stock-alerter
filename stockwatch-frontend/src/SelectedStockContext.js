import {createContext, useState} from 'react';

export const SelectedStockContext = createContext();

export const SelectedStockProvider = ({children}) => {
    const [code, setCode] = useState('');
    return (
    <SelectedStockContext.Provider value={{code, setCode}}>
        {children}
    </SelectedStockContext.Provider>
    );
}