import React from 'react';
import { useAuth } from './AuthProvider';
import {Navigate } from 'react-router-dom';


const PrivateRoute = ({ children }) => {
    const user = useAuth();
    if (!user.token) {
      return <Navigate to="/login" />;
    }
    return children;
  };
  
  export default PrivateRoute;