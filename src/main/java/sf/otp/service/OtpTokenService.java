package sf.otp.service;

import sf.otp.api.dto.OperationDto;
import sf.otp.api.dto.OtpTokenDto;
import sf.otp.api.dto.OtpValidationDto;

public interface OtpTokenService {
    OtpTokenDto generate(OperationDto operationDto);
    void validate(OtpValidationDto dto);
}
