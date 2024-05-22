/* eslint-disable no-undef */
const { createProxyMiddleware } = require("http-proxy-middleware");

module.exports = (app) => {
    app.use(
        "/stories",
        createProxyMiddleware({
            target: "http://localhost:8080",
            changeOrigin: true,
        })
    );
};
