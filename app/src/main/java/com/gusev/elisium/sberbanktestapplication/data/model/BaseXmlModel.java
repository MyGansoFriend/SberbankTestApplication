package com.gusev.elisium.sberbanktestapplication.data.model;

import java.lang.reflect.Field;

/**
 * Created by elisiumGusev
 *
 * @Date 21/07/2017
 * @Author Andrei Gusev
 */

public class BaseXmlModel {
    public  Field findField(String fieldName, BaseXmlModel model) {
        Field field;
        String validFieldName = fieldName.replace(':', '_');
        try {
            field = model.getClass().getDeclaredField(validFieldName);
        } catch (NoSuchFieldException e) {
            return null;
        }
        field.setAccessible(true);
        return field;
    }
}
