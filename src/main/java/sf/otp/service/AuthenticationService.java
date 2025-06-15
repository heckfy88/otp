package sf.otp.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import sf.otp.api.dto.LoginDto;
import sf.otp.api.dto.JwtDto;
import sf.otp.dao.domain.User;

public interface AuthenticationService {
    JwtDto login(LoginDto loginDto);

    default Jwt getToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken) {
            return (Jwt) authentication.getPrincipal();
        }
        return null;
    }

    User getUserFromToken();
}
