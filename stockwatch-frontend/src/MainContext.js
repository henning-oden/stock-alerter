import React from 'react';
import { createContext, useState } from 'react';

export const MainContext = createContext();

export const MainProvider = ({ children }) => {
    const [signedIn, setSignedIn] = useState(false);
    const [token, setToken] = useState('not set');
    const providerValue = React.useMemo(() => ({
        signedIn, setSignedIn,
        token, setToken,
    }), [signedIn, token]);
    return (
        <MainContext.Provider value={providerValue}>
            { children }
        </MainContext.Provider>
    );
}