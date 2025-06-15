package sf.otp.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sf.otp.api.dto.OtpConfigDto;
import sf.otp.service.OtpConfigService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/otp/config")
public class OtpConfigController {

    private final OtpConfigService configService;

    @PostMapping
    public OtpConfigDto updateConfiguration(@RequestBody OtpConfigDto dto) {
        return configService.updateConfiguration(dto);
    }

    @GetMapping
    public OtpConfigDto getConfiguration() {
        return configService.getConfiguration();
    }
}
