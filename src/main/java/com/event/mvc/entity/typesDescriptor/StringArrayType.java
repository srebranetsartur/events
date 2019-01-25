package com.event.mvc.entity.typesDescriptor;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.usertype.DynamicParameterizedType;

import java.util.Properties;

public class StringArrayType extends AbstractSingleColumnStandardBasicType<String[]> implements DynamicParameterizedType {
    private StringArrayType() {
        super(ArraySQlTypeDescriptor.INSTANCE, StringArrayTypeDescriptor.INSTANCE);
    }

    @Override
    public void setParameterValues(Properties parameters) {
        ((StringArrayTypeDescriptor) getJavaTypeDescriptor()).setParameterValues(parameters);
    }

    @Override
    public String getName() {
        return "string-array";
    }

    @Override
    protected boolean registerUnderJavaType() {
        return true;
    }


}
