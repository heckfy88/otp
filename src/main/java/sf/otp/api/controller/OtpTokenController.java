package sf.otp.api.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sf.otp.api.dto.OperationDto;
import sf.otp.api.dto.OtpTokenDto;
import sf.otp.api.dto.OtpValidationDto;
import sf.otp.service.OtpTokenService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/otp/token")
public class OtpTokenController {

    private final OtpTokenService tokenService;

    @PostMapping
    public OtpTokenDto generateToken(@RequestBody OperationDto operationDto) {
        return tokenService.generate(operationDto);
    }

    @PostMapping("/validate")
    public ResponseEntity<Void> validateToken(@RequestBody OtpValidationDto dto) {
        tokenService.validate(dto);
        return ResponseEntity.ok().build();
    }
}
