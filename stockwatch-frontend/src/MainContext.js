import { createContext, useState } from 'react';

export const MainContext = createContext();

export const MainProvider = ({ children }) => {
    const [name, setName] = useState("dummy");
    return (
        <MainContext.Provider value={{ name, setName }}>
            { children }
        </MainContext.Provider>
    );
}