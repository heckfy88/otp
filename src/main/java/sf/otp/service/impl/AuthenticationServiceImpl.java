package sf.otp.service.impl;

import com.nimbusds.jose.JWSAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import sf.otp.api.dto.LoginDto;
import sf.otp.api.dto.JwtDto;
import sf.otp.config.security.JwtProperties;
import sf.otp.dao.domain.User;
import sf.otp.dao.repository.UserRepository;
import sf.otp.service.AuthenticationService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(JwtProperties.class)
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private final JwtProperties jwtProperties;
    private final UserRepository userRepository;

    @Override
    public JwtDto login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.email(),
                        loginDto.password()
                )
        );

        return new JwtDto(generateToken(authentication));
    }

    @Override
    public User getUserFromToken() {
        Jwt jwt = getToken();
        if (jwt == null) {
            return null;
        }

        Map<String, Object> claims = jwt.getClaims();
        return userRepository.findById(UUID.fromString(claims.get("sub").toString()));
    }

    private String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        Instant expiry = now.plus(jwtProperties.getExpiration(), ChronoUnit.HOURS);

        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("ROLE_USER"); // Fallback if somehow missing

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(jwtProperties.getIssuer())
                .issuedAt(now)
                .expiresAt(expiry)
                .subject(authentication.getName())
                .claim("role", role.replace("ROLE_", ""))
                .build();

        JwsHeader headers = JwsHeader.with(JWSAlgorithm.HS256::getName)
                .keyId(jwtProperties.getKeyId())
                .build();

        log.info("Generating token for user: {}", authentication.getName());

        return this.jwtEncoder.encode(JwtEncoderParameters.from(headers, claims)).getTokenValue();
    }
}
