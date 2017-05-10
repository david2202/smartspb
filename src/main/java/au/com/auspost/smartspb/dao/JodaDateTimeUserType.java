package au.com.auspost.smartspb.dao;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.DbTimestampType;
import org.hibernate.usertype.UserType;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.Objects;

public class JodaDateTimeUserType implements UserType {

    @Override
    public int[] sqlTypes() {
        return new int[] {DbTimestampType.INSTANCE.sqlType()};
    }

    @Override
    public Class returnedClass() {
        return DateTime.class;
    }

    @Override
    public boolean equals(Object o, Object o1) throws HibernateException {
        return Objects.equals(o, o1);
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return Objects.hashCode(o);
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor sessionImplementor, Object o) throws HibernateException, SQLException {
        String columnName = names[0];
        Date columnValue = resultSet.getTimestamp(columnName);
        return columnValue == null ? null :
                new DateTime(columnValue);
    }

    @Override
    public void nullSafeSet(PreparedStatement ps, Object value, int index, SessionImplementor sessionImplementor) throws HibernateException, SQLException {
        if ( value == null ) {
            ps.setNull( index, Types.TIMESTAMP);
        } else {
            DateTime dt = (DateTime) value;
            ps.setTimestamp( index, new Timestamp(dt.getMillis()));
        }
    }

    @Override
    public Object deepCopy(Object o) throws HibernateException {
        return o == null ? null :
                new DateTime(((DateTime) o).getMillis());
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object o) throws HibernateException {
        return (DateTime) deepCopy(o);
    }

    @Override
    public Object assemble(Serializable cached, Object o) throws HibernateException {
        return deepCopy(cached);
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return deepCopy(original);
    }
}
