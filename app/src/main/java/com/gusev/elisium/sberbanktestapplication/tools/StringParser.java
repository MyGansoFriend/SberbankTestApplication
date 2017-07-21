package com.gusev.elisium.sberbanktestapplication.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by elisiumGusev
 *
 * @Date 21/07/2017
 * @Author Andrei Gusev
 */

public class StringParser {
    public static String removeHtmlTags(String str) {
        str = str.replaceAll("<(.*?)>", " ");
        str = str.replaceAll("<(.*?)\n", " ");
        str = str.replaceFirst("(.*?)>", " ");
        str = str.replaceAll("&nbsp;", " ");
        str = str.replaceAll("&amp;", " ");
        return str;
    }

    public static String getImgFromString(String str) {
        Pattern p = Pattern.compile("src=\"(.*?)\"");
        Matcher m = p.matcher(str);
        String url = null;
        while (m.find())
            url = m.group(1);
        return url;
    }
}
