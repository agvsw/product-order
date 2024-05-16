package com.wide.agus.service;

import com.wide.agus.dto.LoginRequest;
import com.wide.agus.dto.RegisterRequest;
import org.keycloak.representations.AccessTokenResponse;

public interface UserService {
    void register(RegisterRequest request);

    AccessTokenResponse login(LoginRequest loginRequest);
}
