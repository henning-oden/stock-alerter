import { createContext, useState } from 'react';

export const ComponentContext = createContext();

export const ComponentProvider = ({children}) => {
    const [currentComponent, setCurrentComponent] = useState('main');
    return (
        <ComponentContext.Provider value={{ currentComponent, setCurrentComponent }}>
            { children }
        </ComponentContext.Provider>
    );
}