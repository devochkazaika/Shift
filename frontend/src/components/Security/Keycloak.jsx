import Keycloak from "keycloak-js";

const keycloak = new Keycloak({
 url: "http://localhost:8081",
 realm: "content-maker",
 clientId: "makerFront",
});

export default keycloak;