package sf.otp.api.dto;

import sf.otp.dao.domain.OtpToken;

public record OtpTokenDto(
    String code
) {

    public OtpTokenDto(OtpToken token) {
        this(token.getCode());
    }
}
