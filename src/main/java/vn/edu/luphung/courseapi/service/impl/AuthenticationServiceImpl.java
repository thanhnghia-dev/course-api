package vn.edu.luphung.courseapi.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.luphung.courseapi.dto.UserDTO;
import vn.edu.luphung.courseapi.dto.auth.AuthenticationResponse;
import vn.edu.luphung.courseapi.model.Token;
import vn.edu.luphung.courseapi.model.User;
import vn.edu.luphung.courseapi.repository.TokenRepository;
import vn.edu.luphung.courseapi.repository.UserRepository;
import vn.edu.luphung.courseapi.service.AuthenticationService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;

    private static final int NO_LOGIN_USER_ID = -1;

    @Override
    public AuthenticationResponse register(UserDTO userDTO) {
        User user = new User();
        user.setUserId(generateRandomUserId());
        user.setFullName(userDTO.getFullName());
        user.setUsername(userDTO.getUsername());
        user.setDob(userDTO.getDob());
        user.setGender(userDTO.getGender());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole());
        user.setStatus((byte) 1);
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        return new AuthenticationResponse("User registration was successful");
    }

    @Override
    public AuthenticationResponse authenticate(User request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        revokeAllTokenByUser(user);
        saveUserToken(accessToken, refreshToken, user);

        return new AuthenticationResponse(accessToken, refreshToken, "User login was successful");
    }

    private void revokeAllTokenByUser(User user) {
        List<Token> validTokens = tokenRepository.findAllAccessTokensByUser(user.getId());
        if(validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t-> {
            t.setLoggedOut(true);
        });

        tokenRepository.saveAll(validTokens);
    }

    private void saveUserToken(String accessToken, String refreshToken, User user) {
        Token token = new Token();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }

    private UserDetails userDetails() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .filter(UserDetails.class::isInstance)
                .map(UserDetails.class::cast)
                .orElse(null);
    }

    @Override
    public int getCurrentUserId() {
        return Optional.ofNullable(userDetails())
                .filter(userDetails -> userDetails instanceof User)
                .map(userDetails -> ((User) userDetails).getId())
                .orElse(NO_LOGIN_USER_ID);
    }

    @Override
    public ResponseEntity refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) {
        // extract the token from authorization header
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);

        // extract username from token
        String username = jwtService.extractUsername(token);

        // check if the user exist in database
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new RuntimeException("No user found"));

        // check if the token is valid
        if(jwtService.isValidRefreshToken(token, user)) {
            // generate access token
            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            revokeAllTokenByUser(user);
            saveUserToken(accessToken, refreshToken, user);

            return new ResponseEntity(new AuthenticationResponse(
                    accessToken, refreshToken, "New token generated"), HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.UNAUTHORIZED);

    }

    private String generateRandomUserId() {
        String year = String.valueOf(LocalDate.now().getYear()).substring(2);

        Random ran = new Random();
        int generatedNum = ran.nextInt(1000000);
        String randomNum = String.format("%06d", generatedNum);

        return year + randomNum;
    }

    public boolean isUsernameExist(String username) {
        return userRepository.existsByUsername(username);
    }

}
