package com.wide.agus.service.impl;

import com.wide.agus.dto.CredentialDTO;
import com.wide.agus.dto.LoginRequest;
import com.wide.agus.dto.RegisterRequest;
import com.wide.agus.dto.UserDTO;
import com.wide.agus.entity.User;
import com.wide.agus.repository.UserRepository;
import com.wide.agus.service.UserService;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Value("${keycloak.url}")
    private String keycloakUrl;
    @Value("${keycloak.client-id}")
    private String keycloakClientId;
    @Value("${keycloak.username}")
    private String keycloakUsername;
    @Value("${keycloak.password}")
    private String keycloakPassword;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void register(RegisterRequest request) {
        getAccessToken()
                .flatMap(accessToken -> createUser(accessToken, request)
                        .publishOn(Schedulers.boundedElastic())
                        .doOnSuccess(__ -> {
                            User user = new User();
                            user.setUsername(request.getUsername());
                            user.setAddress(request.getAddress());
                            user.setEmail(request.getEmail());
                            user.setFirstName(request.getFirstName());
                            user.setLastName(request.getLastName());
                            userRepository.saveAndFlush(user);
                        }))
                .block();

    }

    @Override
    public AccessTokenResponse login(LoginRequest loginRequest) {
        WebClient webClient = WebClient.create();
        return webClient.post()
                .uri(keycloakUrl + "/realms/master/protocol/openid-connect/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue("client_id=" + keycloakClientId +
                        "&username=" + loginRequest.getUsername() +
                        "&password=" + loginRequest.getPassword() +
                        "&grant_type=password")
                .retrieve()
                .bodyToMono(AccessTokenResponse.class).block();
    }

    private Mono<String> getAccessToken() {
        WebClient webClient = WebClient.create();
        return webClient.post()
                .uri(keycloakUrl + "/realms/master/protocol/openid-connect/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue("client_id=" + keycloakClientId +
                        "&username=" + keycloakUsername +
                        "&password=" + keycloakPassword +
                        "&grant_type=password")
                .retrieve()
                .bodyToMono(AccessTokenResponse.class)
                .map(AccessTokenResponse::getToken);
    }

    private Mono<Void> createUser(String accessToken, RegisterRequest registerRequest) {
        WebClient webClient = WebClient.create();
        UserDTO user = new UserDTO();
        user.setUsername(registerRequest.getUsername());
        user.setEnabled(true);
        user.setEmailVerified(true);
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());
        CredentialDTO credential = new CredentialDTO();
        credential.setType("password");
        credential.setValue(registerRequest.getPassword());
        credential.setTemporary(false);
        user.setCredentials(List.of(credential));

        return webClient.post()
                .uri(keycloakUrl + "/admin/realms/master/users")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .bodyValue(user)
                .retrieve()
                .toBodilessEntity()
                .then();
    }
}
