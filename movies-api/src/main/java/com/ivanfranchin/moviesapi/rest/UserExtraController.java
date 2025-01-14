package com.ivanfranchin.moviesapi.rest;

import com.ivanfranchin.moviesapi.model.UserExtra;
import com.ivanfranchin.moviesapi.rest.dto.UserExtraRequest;
import com.ivanfranchin.moviesapi.service.UserExtraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.ivanfranchin.moviesapi.config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/userextras")
public class UserExtraController {

    private final UserExtraService userExtraService;

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/me")
    public UserExtra getUserExtra(JwtAuthenticationToken token) {
        return userExtraService.validateAndGetUserExtra(token.getName());
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @PostMapping("/me")
    public UserExtra saveUserExtra(@Valid @RequestBody UserExtraRequest updateUserExtraRequest,
                                   JwtAuthenticationToken token) {
        Optional<UserExtra> userExtraOptional = userExtraService.getUserExtra(token.getName());
        UserExtra userExtra = userExtraOptional.orElseGet(() -> new UserExtra(token.getName()));
        userExtra.setAvatar(updateUserExtraRequest.getAvatar());
        return userExtraService.saveUserExtra(userExtra);
    }
}
