package com.gusev.elisium.sberbanktestapplication.tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Created by elisiumGusev
 *
 * @Date 21/07/2017
 * @Autor Andrei Gusev
 */
public class FormateDateTime {
    public static String formatDate(Date date) {
        return new SimpleDateFormat("MM.dd HH:mm", Locale.US).format(date);
    }

    public static String formatTime(Date date) {
        return new SimpleDateFormat("HH:mm", Locale.US).format(date);
    }
}
