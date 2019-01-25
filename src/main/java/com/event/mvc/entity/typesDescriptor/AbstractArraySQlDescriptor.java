package com.event.mvc.entity.typesDescriptor;

import com.vladmihalcea.hibernate.type.array.internal.ArrayUtil;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.MutableMutabilityPlan;
import org.hibernate.usertype.DynamicParameterizedType;

import java.sql.Array;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;

public abstract class AbstractArraySQlDescriptor<T> extends AbstractTypeDescriptor<T>
        implements DynamicParameterizedType {
    private Class<T> arrayObjectClass;

    @Override
    public void setParameterValues(Properties parameters) {
        arrayObjectClass = ((ParameterType) parameters.get(PARAMETER_TYPE)).getReturnedClass();
    }

    public AbstractArraySQlDescriptor(Class<T> arrayObjectClass) {
        super(arrayObjectClass, new MutableMutabilityPlan<T>() {
            @Override
            protected T deepCopyNotNull(T value) {
                return ArrayUtil.deepCopy(value);
            }
        });

        this.arrayObjectClass = arrayObjectClass;
    }

    @Override
    public String toString(T value) {
        return Arrays.deepToString((Object[]) value);
    }

    @Override
    public T fromString(String string) {
        return ArrayUtil.fromString(string, arrayObjectClass);
    }

    @Override
    public <X> X unwrap(T value, Class<X> type, WrapperOptions options) {
        return (X) ArrayUtil.wrapArray(value);
    }

    @Override
    public <X> T wrap(X value, WrapperOptions options) {
        if (value instanceof Array) {
            Array array = (Array) value;
            try {
                return ArrayUtil.unwrapArray((Object[]) array.getArray(), arrayObjectClass);
            } catch (SQLException e) {
                throw new IllegalArgumentException(e);
            }
        }

        return (T) value;
    }

    protected abstract String getSqlArrayType();
}
