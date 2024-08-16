/* eslint-disable no-undef */
const { createProxyMiddleware } = require("http-proxy-middleware");

module.exports = (app) => {
    app.use(
        "/stories",
        createProxyMiddleware({
            target: "http://localhost:8081",
            changeOrigin: true,
        })
    );
    app.use(
        "/site",
        createProxyMiddleware({
            target: "http://localhost:8081",
            changeOrigin: true,
        })
    );
};
