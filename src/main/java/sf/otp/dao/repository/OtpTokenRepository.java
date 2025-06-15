package sf.otp.dao.repository;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.springframework.stereotype.Repository;
import sf.otp.dao.domain.OtpToken;
import sf.otp.dao.domain.Status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static sf.otp.dao.jooq.tables.Token.TOKEN;

@Repository
public class OtpTokenRepository {

    private final DSLContext dslContext;

    public OtpTokenRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public static List<Field<?>> TOKEN_FIELDS = List.of(
            TOKEN.ID,
            TOKEN.USER_ID,
            TOKEN.OPERATION_ID,
            TOKEN.CODE,
            TOKEN.STATUS,
            TOKEN.CREATED_AT,
            TOKEN.UPDATED_AT,
            TOKEN.EXPIRES_AT
    );

    public OtpToken save(OtpToken otpToken) {
        return dslContext.insertInto(TOKEN,
                        TOKEN.USER_ID,
                        TOKEN.OPERATION_ID,
                        TOKEN.CODE,
                        TOKEN.STATUS,
                        TOKEN.CREATED_AT,
                        TOKEN.UPDATED_AT,
                        TOKEN.EXPIRES_AT
                        )
                .values(
                        otpToken.getUserId(),
                        otpToken.getOperationId(),
                        otpToken.getCode(),
                        Status.ACTIVE.name(),
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        otpToken.getExpiresAt()
                )
                .returningResult()
                .fetchOneInto(OtpToken.class);
    }

    public OtpToken findByUserIdAndOperationId(UUID userId, UUID operationId, String code) {
        return dslContext
                .select(TOKEN_FIELDS)
                .from(TOKEN)
                .where(TOKEN.USER_ID.eq(userId))
                .and(TOKEN.OPERATION_ID.eq(operationId))
                .and(TOKEN.CODE.eq(code))
                .fetchOneInto(OtpToken.class);
    }

    public OtpToken changeStatus(UUID id, Status status) {
        return dslContext
                .update(TOKEN)
                .set(TOKEN.STATUS, status.name())
                .set(TOKEN.UPDATED_AT, LocalDateTime.now())
                .where(TOKEN.ID.eq(id))
                .returning()
                .fetchOneInto(OtpToken.class);

    }

    // Удаление из таблицы происходит чисткой всех записей, где expires_at < now(), с помощью процедуры
    public void deleteToken(UUID userId) {
        dslContext
                .update(TOKEN)
                .set(TOKEN.STATUS, Status.EXPIRED.name())
                .set(TOKEN.EXPIRES_AT, LocalDateTime.now())
                .where(TOKEN.USER_ID.eq(userId))
                .execute();
    }

    public void deleteExpiredTokens() {
        dslContext.deleteFrom(TOKEN)
                .where(TOKEN.STATUS.eq(Status.USED.name()))
                .or(TOKEN.STATUS.eq(Status.EXPIRED.name()))
                .execute();

    }

}