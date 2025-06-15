package sf.otp.dao.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import sf.otp.api.dto.OtpConfigDto;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OtpConfig {
    Integer length;
    Integer duration;
    LocalDateTime updatedAt;

    public OtpConfig(OtpConfigDto dto) {
        this.length = dto.length();
        this.duration = dto.duration();
        this.updatedAt = dto.updatedAt();
    }
}
