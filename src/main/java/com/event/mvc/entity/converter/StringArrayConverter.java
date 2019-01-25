package com.event.mvc.entity.converter;

import javax.persistence.AttributeConverter;

public class StringArrayConverter implements AttributeConverter<String[], Object[]> {
    @Override
    public Object[] convertToDatabaseColumn(String[] attribute) {
        return attribute;
    }

    @Override
    public String[] convertToEntityAttribute(Object[] dbData) {
        String[] result = new String[dbData.length];
        for(int i = 0; i < result.length; i++)
            result[i] = dbData[i].toString();

        return result;
    }
}
