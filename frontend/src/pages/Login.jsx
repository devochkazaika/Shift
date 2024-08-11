import React from 'react';
import { useKeycloak } from "@react-keycloak/web";
import { toast } from 'react-toastify';

const Login = () => {
  const { keycloak, initialized } = useKeycloak();

  if (!initialized) {
    toast.update("Загрузка");
    return ;
  }

  return keycloak.login();
};

export default Login;
