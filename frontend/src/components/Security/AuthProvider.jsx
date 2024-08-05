import React, { useContext, createContext } from "react";
import { useNavigate } from "react-router-dom";
import { fetchToken } from './TokenProcess';
import TokenService from "./TokenService";
import { toast } from "react-toastify";

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
        toast.success('Успешный вход!');
        navigate("/");
        location.reload();
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

export const logOut = () => {
  TokenService.updateLocalAccessToken("");
  TokenService.updateRefreshToken("");
}