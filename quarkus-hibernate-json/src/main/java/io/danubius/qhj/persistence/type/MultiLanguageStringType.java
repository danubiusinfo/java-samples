package io.danubius.qhj.persistence.type;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.danubius.qhj.dto.MultiLanguageString;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;
import org.postgresql.util.PGobject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class MultiLanguageStringType implements UserType {

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.JAVA_OBJECT};
    }

    @Override
    public Class<?> returnedClass() {
        return MultiLanguageString.class;
    }

    @Override
    public boolean equals(Object o, Object o1) throws HibernateException {
        if (o == null) {
            return o1 == null;
        }
        return o.equals(o1);
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return o.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names,
                              SharedSessionContractImplementor sharedSessionContractImplementor, Object o)
            throws HibernateException, SQLException {
        final String cellContent = resultSet.getString(names[0]);
        if (cellContent == null) {
            return null;
        }

        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(cellContent.getBytes(StandardCharsets.UTF_8), returnedClass());
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert string to json: " + e.getMessage(), e);
        }
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int i,
                            SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        if (value == null) {
            preparedStatement.setNull(i, Types.NULL);
            return;
        }
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final StringWriter writer = new StringWriter();
            mapper.writeValue(writer, value);
            writer.flush();
            PGobject pGobject = new PGobject();
            pGobject.setType("json");
            pGobject.setValue(writer.toString());
            preparedStatement.setObject(i, pGobject, Types.OTHER);
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert json to string: " + e.getMessage(), e);
        }
    }

    @Override
    public Object deepCopy(Object o) throws HibernateException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(o);
            oos.flush();
            oos.close();

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            return new ObjectInputStream(bais).readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new HibernateException(e);
        }
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object o) throws HibernateException {
        return (Serializable) this.deepCopy(o);
    }

    @Override
    public Object assemble(Serializable serializable, Object o) throws HibernateException {
        return this.deepCopy(serializable);
    }

    @Override
    public Object replace(Object o, Object o1, Object o2) throws HibernateException {
        return this.deepCopy(o);
    }
}
