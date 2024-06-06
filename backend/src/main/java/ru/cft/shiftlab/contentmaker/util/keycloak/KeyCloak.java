//package ru.cft.shiftlab.contentmaker.util.keycloak;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Component;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.UriComponentsBuilder;
//
//@Component
//@RequiredArgsConstructor
//public class KeyCloak {
//    @Value("${keycloak.auth-server-url}")
//    private String keyCloakUrl;
//    @Value("${keycloak.resource}")
//    private String clientId;
//    @Value("${keycloak.realm}")
//    private String realm;
////    private final RestTemplate restTemplate;
//
//    public AccessTokenResponse authenticate(AuthRequestDto request) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
//        parameters.add("username",request.getUsername());
//        parameters.add("password",request.getPassword());
//        parameters.add("grant_type", "password");
//        parameters.add("client_id", clientId);
//
//        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(parameters, headers);
//
//        return restTemplate.exchange(getAuthUrl(),
//                HttpMethod.POST,
//                entity,
//                AccessTokenResponse.class).getBody();
//    }
//
//    public AccessTokenResponse refreshToken(String refreshToken) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
//        parameters.add("grant_type", "refresh_token");
//        parameters.add("client_id", clientId);
//        parameters.add("refresh_token", refreshToken);
//
//        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(parameters, headers);
//
//        return restTemplate.exchange(getAuthUrl(),
//                HttpMethod.POST,
//                entity,
//                AccessTokenResponse.class).getBody();
//    }
//
//    private String getAuthUrl() {
//        return UriComponentsBuilder.fromHttpUrl(keyCloakUrl)
//                .pathSegment("realms")
//                .pathSegment(realm)
//                .pathSegment("protocol")
//                .pathSegment("openid-connect")
//                .pathSegment("token")
//                .toUriString();
//    }
//}
