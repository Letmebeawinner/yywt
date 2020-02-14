package com.houqin.common;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author YaoZhen
 * @date 06-02, 16:35, 2018.
 */
public class DateEditor extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(text);
        } catch (ParseException e) {
            format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                date = format.parse(text);
            } catch (ParseException e1) {
                format = new SimpleDateFormat("yyyy-MM");
                try {
                    date = format.parse(text);
                } catch (ParseException e2) {
                    e2.printStackTrace();
                }
            }
        }
        setValue(date);
    }
}
