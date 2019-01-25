package com.event.mvc.entity.typesDescriptor;

public class StringArrayTypeDescriptor extends AbstractArraySQlDescriptor<String[]> {
    public static final StringArrayTypeDescriptor INSTANCE = new StringArrayTypeDescriptor();

    public StringArrayTypeDescriptor() {
        super(String[].class);
    }

    @Override
    protected String getSqlArrayType() {
        return "Array";
    }
}
