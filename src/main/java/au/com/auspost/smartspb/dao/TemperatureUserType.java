package au.com.auspost.smartspb.dao;

import au.com.auspost.smartspb.domain.Temperature;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.BigDecimalType;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

public class TemperatureUserType implements UserType {
    public static final TemperatureUserType INSTANCE = new TemperatureUserType();

    @Override
    public int[] sqlTypes() {
        return new int[] {BigDecimalType.INSTANCE.sqlType()};
    }

    @Override
    public Class returnedClass() {
        return Temperature.class;
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
        BigDecimal columnValue = resultSet.getBigDecimal(columnName);
        return columnValue == null ? null :
                new Temperature(columnValue);
    }

    @Override
    public void nullSafeSet(PreparedStatement ps, Object value, int index, SessionImplementor sessionImplementor) throws HibernateException, SQLException {
        if ( value == null ) {
            ps.setNull( index, Types.NUMERIC );
        } else {
            BigDecimal bd = ((Temperature) value).getValue();
            ps.setBigDecimal( index, bd);
        }
    }

    @Override
    public Object deepCopy(Object o) throws HibernateException {
        return o == null ? null :
                new Temperature(((Temperature) o).getValue());
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object o) throws HibernateException {
        return (Temperature) deepCopy(o);
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
