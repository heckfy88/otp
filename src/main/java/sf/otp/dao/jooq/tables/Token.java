/*
 * This file is generated by jOOQ.
 */
package sf.otp.dao.jooq.tables;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import org.jooq.Check;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function8;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row8;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import sf.otp.dao.jooq.Keys;
import sf.otp.dao.jooq.Otp;
import sf.otp.dao.jooq.tables.records.TokenRecord;


/**
 * Таблица для хранения токенов
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Token extends TableImpl<TokenRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>otp.token</code>
     */
    public static final Token TOKEN = new Token();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TokenRecord> getRecordType() {
        return TokenRecord.class;
    }

    /**
     * The column <code>otp.token.id</code>. Уникальный идентификатор токена
     */
    public final TableField<TokenRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false).defaultValue(DSL.field(DSL.raw("uuid_generate_v4()"), SQLDataType.UUID)), this, "Уникальный идентификатор токена");

    /**
     * The column <code>otp.token.user_id</code>. Идентификатор пользователя
     */
    public final TableField<TokenRecord, UUID> USER_ID = createField(DSL.name("user_id"), SQLDataType.UUID.nullable(false), this, "Идентификатор пользователя");

    /**
     * The column <code>otp.token.operation_id</code>. Идентификатор операции
     */
    public final TableField<TokenRecord, UUID> OPERATION_ID = createField(DSL.name("operation_id"), SQLDataType.UUID.nullable(false), this, "Идентификатор операции");

    /**
     * The column <code>otp.token.code</code>. Токен
     */
    public final TableField<TokenRecord, String> CODE = createField(DSL.name("code"), SQLDataType.VARCHAR.nullable(false), this, "Токен");

    /**
     * The column <code>otp.token.status</code>. Статус токена
     */
    public final TableField<TokenRecord, String> STATUS = createField(DSL.name("status"), SQLDataType.VARCHAR.nullable(false), this, "Статус токена");

    /**
     * The column <code>otp.token.created_at</code>. Время создания токена
     */
    public final TableField<TokenRecord, LocalDateTime> CREATED_AT = createField(DSL.name("created_at"), SQLDataType.LOCALDATETIME(6).nullable(false).defaultValue(DSL.field(DSL.raw("now()"), SQLDataType.LOCALDATETIME)), this, "Время создания токена");

    /**
     * The column <code>otp.token.updated_at</code>. Время обновления токена
     */
    public final TableField<TokenRecord, LocalDateTime> UPDATED_AT = createField(DSL.name("updated_at"), SQLDataType.LOCALDATETIME(6).nullable(false).defaultValue(DSL.field(DSL.raw("now()"), SQLDataType.LOCALDATETIME)), this, "Время обновления токена");

    /**
     * The column <code>otp.token.expires_at</code>. Время жизни токена
     */
    public final TableField<TokenRecord, LocalDateTime> EXPIRES_AT = createField(DSL.name("expires_at"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "Время жизни токена");

    private Token(Name alias, Table<TokenRecord> aliased) {
        this(alias, aliased, null);
    }

    private Token(Name alias, Table<TokenRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("Таблица для хранения токенов"), TableOptions.table());
    }

    /**
     * Create an aliased <code>otp.token</code> table reference
     */
    public Token(String alias) {
        this(DSL.name(alias), TOKEN);
    }

    /**
     * Create an aliased <code>otp.token</code> table reference
     */
    public Token(Name alias) {
        this(alias, TOKEN);
    }

    /**
     * Create a <code>otp.token</code> table reference
     */
    public Token() {
        this(DSL.name("token"), null);
    }

    public <O extends Record> Token(Table<O> child, ForeignKey<O, TokenRecord> key) {
        super(child, key, TOKEN);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Otp.OTP;
    }

    @Override
    public UniqueKey<TokenRecord> getPrimaryKey() {
        return Keys.PK_OTP_TOKEN;
    }

    @Override
    public List<ForeignKey<TokenRecord, ?>> getReferences() {
        return Arrays.asList(Keys.TOKEN__FK_OTP_TOKEN_USER);
    }

    private transient User _user;

    /**
     * Get the implicit join path to the <code>otp.user</code> table.
     */
    public User user() {
        if (_user == null)
            _user = new User(this, Keys.TOKEN__FK_OTP_TOKEN_USER);

        return _user;
    }

    @Override
    public List<Check<TokenRecord>> getChecks() {
        return Arrays.asList(
            Internal.createCheck(this, DSL.name("status_constraint"), "(((status)::text = ANY ((ARRAY['ACTIVE'::character varying, 'EXPIRED'::character varying, 'USED'::character varying])::text[])))", true)
        );
    }

    @Override
    public Token as(String alias) {
        return new Token(DSL.name(alias), this);
    }

    @Override
    public Token as(Name alias) {
        return new Token(alias, this);
    }

    @Override
    public Token as(Table<?> alias) {
        return new Token(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Token rename(String name) {
        return new Token(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Token rename(Name name) {
        return new Token(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Token rename(Table<?> name) {
        return new Token(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row8 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row8<UUID, UUID, UUID, String, String, LocalDateTime, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function8<? super UUID, ? super UUID, ? super UUID, ? super String, ? super String, ? super LocalDateTime, ? super LocalDateTime, ? super LocalDateTime, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function8<? super UUID, ? super UUID, ? super UUID, ? super String, ? super String, ? super LocalDateTime, ? super LocalDateTime, ? super LocalDateTime, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
