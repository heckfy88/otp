package sf.otp.dao.repository;

import lombok.val;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.springframework.stereotype.Repository;
import sf.otp.dao.domain.OtpConfig;

import java.time.LocalDateTime;
import java.util.List;

import static sf.otp.dao.jooq.tables.Config.CONFIG;

@Repository
public class OtpConfigRepository {

    private final DSLContext dslContext;

    public OtpConfigRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public static List<Field<?>> CONFIG_FIELDS = List.of(
            CONFIG.LENGTH,
            CONFIG.DURATION,
            CONFIG.UPDATED_AT
    );

    public OtpConfig getConfiguration() {
        return dslContext.select(CONFIG_FIELDS)
                .from(CONFIG)
                .where(CONFIG.ID.eq(1))
                .fetchOneInto(OtpConfig.class);
    }

    public OtpConfig updateConfiguration(OtpConfig otpConfig) {
        return dslContext.update(CONFIG)
                .set(CONFIG.DURATION, otpConfig.getDuration())
                .set(CONFIG.LENGTH, otpConfig.getLength())
                .set(CONFIG.UPDATED_AT, LocalDateTime.now())
                .where(CONFIG.ID.eq(1))
                .returningResult(CONFIG_FIELDS)
                .fetchOneInto(OtpConfig.class);
    }

}
