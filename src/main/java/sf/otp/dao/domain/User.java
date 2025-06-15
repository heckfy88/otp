package sf.otp.dao.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sf.otp.api.dto.UserDto;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private UUID id;
    private String username;
    private String role;
    private String email;
    private String phoneNumber;
    private String tgId;
    private String passwordHash;
    private LocalDateTime createdAt;
    private Boolean isActive;

    public User(UserDto dto) {
        this.id = dto.id();
        this.username = dto.username();
        this.role = dto.role();
        this.email = dto.email();
        this.phoneNumber = dto.phoneNumber();
        this.tgId = dto.tgId();
        this.passwordHash = dto.passwordHash();
        this.createdAt = LocalDateTime.now();
        this.isActive = dto.isActive();
    }
}