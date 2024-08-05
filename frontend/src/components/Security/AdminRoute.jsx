import React from 'react';
import TokenService from './TokenService';


export const AdminRoute = ({ children }) => {
    console.log(TokenService.getRoles());
    if (!TokenService.getRoles().has("ADMIN")) {
      return <></>
    }
    return children;
  };
  

