import React, { createContext, useContext } from 'react';
import { useKeycloak } from "@react-keycloak/web";


const NavigationContext = createContext();


const PrivateRoute = ({ children }) => {
  const { keycloak, initialized } = useKeycloak();

  if (!initialized) {
    return <div>Loading...</div>;
  }

  if (keycloak.authenticated) {
    return children;
  } else {
    keycloak.login();
    return null;
  }
};
 
 export default PrivateRoute;


export const useNavigation = () => useContext(NavigationContext);