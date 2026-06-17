package com.alberto.auth.services;

import com.alberto.auth.dto.LoginRequest;
import com.alberto.auth.dto.TokenResponse;

public interface AuthService {

    TokenResponse autenticar(LoginRequest request) throws Exception;
}
