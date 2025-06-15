package sf.otp.service;

import sf.otp.api.dto.OtpConfigDto;

public interface OtpConfigService {

    OtpConfigDto getConfiguration();
    OtpConfigDto updateConfiguration(OtpConfigDto dto);
}
