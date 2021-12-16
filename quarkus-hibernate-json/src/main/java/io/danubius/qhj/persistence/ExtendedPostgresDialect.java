package io.danubius.qhj.persistence;

import org.hibernate.dialect.PostgreSQL10Dialect;

import java.sql.Types;

public class ExtendedPostgresDialect extends PostgreSQL10Dialect {

    public ExtendedPostgresDialect() {
        super();
        this.registerColumnType(Types.JAVA_OBJECT, "json");
    }
}
