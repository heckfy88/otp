package sf.otp.dao.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import sf.otp.api.dto.UserDto;
import sf.otp.dao.domain.User;

import java.util.List;
import java.util.UUID;

import static sf.otp.dao.jooq.Tables.USER;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final DSLContext dslContext;
    private final PasswordEncoder passwordEncoder;
    private final OtpTokenRepository otpTokenRepository;

    public static List<Field<?>> USER_FIELDS = List.of(
            USER.ID,
            USER.USERNAME,
            USER.ROLE,
            USER.EMAIL,
            USER.PHONE_NUMBER,
            USER.TG_ID,
            USER.PASSWORD_HASH,
            USER.CREATED_AT,
            USER.IS_ACTIVE
    );

    public User create(User user) {
        return dslContext.insertInto(USER,
                        USER.USERNAME,
                        USER.ROLE,
                        USER.EMAIL,
                        USER.PHONE_NUMBER,
                        USER.TG_ID,
                        USER.PASSWORD_HASH,
                        USER.CREATED_AT
                )
                .values(
                        user.getUsername(),
                        user.getRole(),
                        user.getEmail(),
                        user.getPhoneNumber(),
                        user.getTgId(),
                        passwordEncoder.encode(user.getPasswordHash()),
                        user.getCreatedAt()
                )
                .returning()
                .fetchSingleInto(User.class);
    }

    public User findByUsername(String username) {
        return dslContext
                .select(
                        USER.ID,
                        USER.USERNAME,
                        USER.ROLE,
                        USER.EMAIL,
                        USER.PHONE_NUMBER,
                        USER.TG_ID,
                        USER.CREATED_AT,
                        USER.IS_ACTIVE
                )
                .from(USER)
                .where(USER.USERNAME.eq(username))
                .fetchOneInto(User.class);
    }

    public User findById(UUID id) {
        return dslContext
                .select(
                        USER.ID,
                        USER.USERNAME,
                        USER.ROLE,
                        USER.EMAIL,
                        USER.PHONE_NUMBER,
                        USER.TG_ID,
                        USER.CREATED_AT,
                        USER.IS_ACTIVE
                )
                .from(USER)
                .where(USER.ID.eq(id))
                .fetchOneInto(User.class);
    }

    public User findByEmail(String email) {
        return dslContext
                .select(USER_FIELDS)
                .from(USER)
                .where(USER.EMAIL.eq(email))
                .fetchOneInto(User.class);
    }

    public List<User> getAll() {
        return dslContext
                .select(
                        USER.ID,
                        USER.USERNAME,
                        USER.ROLE,
                        USER.EMAIL,
                        USER.PHONE_NUMBER,
                        USER.TG_ID,
                        USER.CREATED_AT,
                        USER.IS_ACTIVE
                )
                .from(USER)
                .where(USER.ROLE.eq("USER"))
                .fetchInto(User.class);
    }

    public void deleteById(UUID userId) {
        dslContext
                .update(USER)
                .set(USER.IS_ACTIVE, false)
                .where(USER.ID.eq(userId));

        otpTokenRepository.deleteToken(userId);
    }
}