import { useKeycloak } from '@react-keycloak/web';
import { jwtDecode } from 'jwt-decode';
// import keycloak from "./Keycloak";


export const AdminRoute = ({ children }) => {
  const { keycloak } = useKeycloak();

  let jwt;2
  try {
    jwt = jwtDecode(keycloak.token + "");
  } catch (error) {
    console.error('Error decoding token:', error);
    return null;
  }

  if (jwt?.resource_access?.maker?.roles?.includes('ADMIN')) {
    return children;
  }

  return null;
};
  

