import { jwtDecode } from "jwt-decode";

class TokenService {
    getLocalRefreshToken() {
      const user = JSON.parse(localStorage.getItem("refresh_token"));
      return user?.refreshToken;
    }
  
    getLocalAccessToken() {
      const user = JSON.parse(localStorage.getItem("token"));
      return user?.accessToken;
    }

    getRoles() {
      const token = localStorage.getItem('token');
      if (!token) return new Set();

      const decodedToken = jwtDecode(token);
      let roles;
      try{
        roles = decodedToken.resource_access.maker.roles || [];
      }
      catch (e) {
        roles = [];
      }
      return new Set(roles);
  }
  
    updateLocalAccessToken(token) {
        let user = JSON.parse(localStorage.getItem("token"));
        if (user) {
          user.accessToken = token;
        } else {
          user = { accessToken: token };
        }
        localStorage.setItem("token", JSON.stringify(user));
      }

    updateRefreshToken(token){
        let user = JSON.parse(localStorage.getItem("refresh_token"));
        if (user) {
          user.refreshToken = token;
        } else {
          user = { refreshToken: token };
        }
        localStorage.setItem("refresh_token", JSON.stringify(user));
      }

  }
  
  export default new TokenService();