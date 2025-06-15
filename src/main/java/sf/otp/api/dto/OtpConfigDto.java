package sf.otp.api.dto;

import sf.otp.dao.domain.OtpConfig;

import java.time.LocalDateTime;


public record OtpConfigDto(
        Integer length,
        Integer duration,
        LocalDateTime updatedAt
) {
    public OtpConfigDto(OtpConfig domain) {
        this(
                domain.getLength(),
                domain.getDuration(),
                domain.getUpdatedAt()
        );
    }
}
