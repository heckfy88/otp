package sf.otp.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sf.otp.dao.repository.OtpTokenRepository;

@Service
@RequiredArgsConstructor
public class TokenScheduler {

    private final OtpTokenRepository otpTokenRepository;

    // once a day
    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanExpiredAndUsedTokens() {
        otpTokenRepository.deleteExpiredTokens();
    }

}
