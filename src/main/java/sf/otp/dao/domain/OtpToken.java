package sf.otp.dao.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class OtpToken {
    UUID id;
    UUID userId;
    UUID operationId;
    String code;
    Status status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    LocalDateTime expiresAt;
}
