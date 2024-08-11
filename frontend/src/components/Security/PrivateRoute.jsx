import React, { createContext, useContext } from 'react';
import { useKeycloak } from "@react-keycloak/web";


const NavigationContext = createContext();


const PrivateRoute = ({ children }) => {
  const { keycloak, initialized } = useKeycloak();

  if (!initialized) {
    return <div>Loading...</div>;
  }

  const isLoggedIn = keycloak.authenticated;

  return isLoggedIn ? children : keycloak.login();
 };
 
 export default PrivateRoute;


export const useNavigation = () => useContext(NavigationContext);