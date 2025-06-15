package sf.otp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sf.otp.api.dto.OperationDto;
import sf.otp.api.dto.OtpTokenDto;
import sf.otp.api.dto.OtpValidationDto;
import sf.otp.api.senders.Sender;
import sf.otp.dao.domain.OtpConfig;
import sf.otp.dao.domain.OtpToken;
import sf.otp.dao.domain.Status;
import sf.otp.dao.domain.User;
import sf.otp.dao.repository.OtpConfigRepository;
import sf.otp.dao.repository.OtpTokenRepository;
import sf.otp.service.AuthenticationService;
import sf.otp.service.OtpTokenService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OtpTokenServiceImpl implements OtpTokenService {

    private final OtpTokenRepository tokenRepository;
    private final OtpConfigRepository configRepository;
    private final AuthenticationService authenticationService;
    private final List<Sender> notificationSenders;

    @Override
    @Transactional
    public OtpTokenDto generate(OperationDto operationDto) {
        User user = authenticationService.getUserFromToken();

        OtpConfig config = configRepository.getConfiguration();
        String code = generateOtpCode(config.getLength());

        OtpToken savedToken = tokenRepository.save(
                OtpToken.builder()
                        .userId(user.getId())
                        .operationId(operationDto.id())
                        .code(code)
                        .expiresAt(LocalDateTime.now().plusSeconds(config.getDuration()))
                        .build()
        );

        log.info("Generated OTP code: {}, user: {}", code, user);
        sendNotifications(code, user);
        return new OtpTokenDto(savedToken);
    }

    @Transactional
    public void validate(OtpValidationDto requestDto) {
        User user = authenticationService.getUserFromToken();

        OtpToken otpToken = getOtpCodeWithUser(
                user.getId(),
                requestDto.operationId(),
                requestDto.code()
        );

        if (!otpToken.getStatus().equals(Status.ACTIVE)) {
            log.info("OTP code {} is not active.", otpToken);
            throw new RuntimeException("OTP code" + otpToken + " is not active.");
        }

        if (isExpiredOtpCode(otpToken)) {
            log.info("OTP code {} has expired.", otpToken);
            throw new RuntimeException("OTP code " + otpToken + " has expired.");
        }

        tokenRepository.changeStatus(otpToken.getId(), Status.USED);
        log.info("OTP code {} has been used.", otpToken);
    }

    private String generateOtpCode(int length) {
        return RandomStringUtils.random(
                length, 0, 0,
                true, true,
                null, // RandomStringUtils.ALPHANUMERICAL_CHARS
                new Random());
    }

    private OtpToken getOtpCodeWithUser(UUID userId, UUID operationId, String code) {
        log.info("Otp code not found: code={}, userId={} operationId={}", code, userId, operationId);
        return tokenRepository.findByUserIdAndOperationId(userId, operationId, code);
    }

    private boolean isExpiredOtpCode(OtpToken otpCode) {
        return otpCode.getExpiresAt().isBefore(LocalDateTime.now());
    }

    private void sendNotifications(String code, User user) {
        notificationSenders.forEach(notificationSender -> notificationSender.send("Ваш код подтверждения: " + code, user));
    }
}
