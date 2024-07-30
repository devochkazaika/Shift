import React from 'react';

import {Navigate } from 'react-router-dom';
import TokenService from './TokenService';


const PrivateRoute = ({ children }) => {
    if (!TokenService.getLocalAccessToken()) {
      return <Navigate to="/login" />;
    }
    return children;
  };
  
  export default PrivateRoute;