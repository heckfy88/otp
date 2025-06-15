package sf.otp.dao.jooq.repository;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.springframework.stereotype.Repository;
import sf.otp.dao.jooq.tables.User;

import java.util.List;

import static sf.otp.dao.jooq.Tables.USER;

@Repository
public class UserRepository {

    private final DSLContext dslContext;

    public UserRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

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
        dslContext.insertInto(USER,
                USER_FIELDS
                )
                .values(
                        user.getId(),
                        user.getUsername(),
                        user.getRole(),
                        user.getEmail(),
                        user.getPhoneNumber(),
                        user.getTgId(),
                        user.getPasswordHash(),
                        user.getCreatedAt(),
                        user.getIsActive()
                );

    }
}