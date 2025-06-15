package sf.otp.api.dto;

import sf.otp.dao.domain.User;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserDto(
        UUID id,
        String username,
        String role,
        String email,
        String phoneNumber,
        String tgId,
        String passwordHash,
        LocalDateTime createdAt,
        Boolean isActive
) {
    public UserDto(User user) {
        this(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getTgId(),
                null,
                user.getCreatedAt(),
                user.getIsActive()
        );
    }
}