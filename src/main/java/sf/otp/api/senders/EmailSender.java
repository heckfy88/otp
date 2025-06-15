package sf.otp.api.senders;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import sf.otp.dao.domain.User;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailSender implements Sender {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.from}")
    private String from;

    @Override
    @Async
    public void send(String message, User user) {
        String to = user.getEmail();
        if (to == null){
            log.info("User dont have an email. Send dont notify");
            return;
        }

        var mail = new SimpleMailMessage();
        mail.setTo(to);
        mail.setFrom(from);
        mail.setSubject("OTP Service");
        mail.setText(message);

        mailSender.send(mail);
        log.info("Send email to {} successfully", to);
    }
}
