import React, { createContext, useContext } from 'react';

import {Navigate } from 'react-router-dom';
import TokenService from './TokenService';



const NavigationContext = createContext();

const PrivateRoute = ({ children }) => {
    if (!TokenService.getLocalAccessToken()) {
      return <Navigate to="/login" />;
    }
    return children;
  };
  
  export default PrivateRoute;


export const useNavigation = () => useContext(NavigationContext);