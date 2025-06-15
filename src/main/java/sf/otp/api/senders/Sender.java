package sf.otp.api.senders;

import sf.otp.dao.domain.User;

public interface Sender {
    void send(String message, User user);
}