import React, { useContext, createContext } from "react";
import { useNavigate } from "react-router-dom";
import { fetchToken } from './TokenProcess';
import TokenService from "./TokenService";

const AuthContext = createContext();

const AuthProvider = ({ children }) => {

  const navigate = useNavigate();
  const loginAction = async (data) => {
    try {
      const response = await fetchToken(data.login, data.password);
      const res = await response.json();
      if (res.access_token) {
        TokenService.updateLocalAccessToken(res.access_token);
        TokenService.updateRefreshToken(res.refresh_token);
        navigate("/");
        return;
      }
      throw new Error(res.message);
    } catch (err) {
      console.error(err);
    }
  };


  return (
    <AuthContext.Provider value={{loginAction }}>
      {children}
    </AuthContext.Provider>
  );

};

export default AuthProvider;
export const useAuth = () => {
  return useContext(AuthContext);
};