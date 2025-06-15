package sf.otp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sf.otp.api.dto.OtpConfigDto;
import sf.otp.dao.domain.OtpConfig;
import sf.otp.dao.repository.OtpConfigRepository;
import sf.otp.service.OtpConfigService;

@Slf4j
@Service
@RequiredArgsConstructor
public class OtpConfigServiceImpl implements OtpConfigService {

    private final OtpConfigRepository otpConfigRepository;

    @Override
    public OtpConfigDto getConfiguration() {

        OtpConfig config = otpConfigRepository.getConfiguration();

        log.info("Configuration: {}", config);
        return new OtpConfigDto(config);
    }

    @Override
    public OtpConfigDto updateConfiguration(OtpConfigDto dto) {
        OtpConfig config = otpConfigRepository.updateConfiguration(new OtpConfig(dto));

        log.info("Updated configuration: {}", config);
        return new OtpConfigDto(config);
    }
}
