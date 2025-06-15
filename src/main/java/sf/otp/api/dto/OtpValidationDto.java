package sf.otp.api.dto;

import java.util.UUID;

public record OtpValidationDto(
        String code,
        UUID operationId
) {
}
