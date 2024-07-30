class TokenService {
    getLocalRefreshToken() {
      const user = JSON.parse(localStorage.getItem("token"));
      return user?.refreshToken;
    }
  
    getLocalAccessToken() {
      const user = JSON.parse(localStorage.getItem("token"));
      return user?.accessToken;
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
        let user = JSON.parse(localStorage.getItem("token"));
        if (user) {
          user.refreshToken = token;
        } else {
          user = { refreshToken: token };
        }
        localStorage.setItem("token", JSON.stringify(user));
      }

  }
  
  export default new TokenService();