package com.event.mvc.entity.typesDescriptor;

import org.h2.value.Value;
import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.ValueExtractor;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
import org.hibernate.type.descriptor.sql.BasicBinder;
import org.hibernate.type.descriptor.sql.BasicExtractor;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;

import java.sql.*;

public class ArraySQlTypeDescriptor implements SqlTypeDescriptor {
    public static final ArraySQlTypeDescriptor INSTANCE = new ArraySQlTypeDescriptor();

    @Override
    public int getSqlType() {
        return Value.ARRAY;
    }

    @Override
    public boolean canBeRemapped() {
        return true;
    }

    @Override
    public <X> ValueBinder<X> getBinder(JavaTypeDescriptor<X> javaTypeDescriptor) {
        return new BasicBinder<X>(javaTypeDescriptor, this) {
            @Override
            protected void doBind(PreparedStatement st, Object value, int index, WrapperOptions options) throws SQLException {
                AbstractArraySQlDescriptor<Object> abstractArraySQlDescriptor = (AbstractArraySQlDescriptor<Object>) javaTypeDescriptor;

                st.setArray(index, st.getConnection().createArrayOf(abstractArraySQlDescriptor.getSqlArrayType(),
                        abstractArraySQlDescriptor.unwrap(value, Object[].class, options)));

            }

            @Override
            protected void doBind(CallableStatement st, Object value, String name, WrapperOptions options) throws SQLException {
                throw new UnsupportedOperationException("Binding by name is not supported");

            }
        };
    }

    @Override
    public <X> ValueExtractor<X> getExtractor(JavaTypeDescriptor<X> javaTypeDescriptor) {
        return new BasicExtractor<X>(javaTypeDescriptor, this) {
            @Override
            protected X doExtract(ResultSet rs, String name, WrapperOptions options) throws SQLException {
                Array array = rs.getArray(name);
                return javaTypeDescriptor.wrap(array, options);
            }

            @Override
            protected X doExtract(CallableStatement statement, int index, WrapperOptions options) throws SQLException {
                return javaTypeDescriptor.wrap(statement.getArray(index), options);
            }

            @Override
            protected X doExtract(CallableStatement statement, String name, WrapperOptions options) throws SQLException {
                return javaTypeDescriptor.wrap(statement.getArray(name), options);
            }
        };
    }
}


