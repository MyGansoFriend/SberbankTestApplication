package com.gusev.elisium.sberbanktestapplication.tools;

import android.content.Context;

import com.gusev.elisium.sberbanktestapplication.R;


/**
 * Created by elisiumGusev
 *
 * @Date 21/07/2017
 * @Autor Andrei Gusev
 */
public class SrcStr {
    private static SrcStr instance;
    public static String BASE_URL;
    public static String DATE;

    private static Context mContext;

    public static SrcStr get() {
        if (instance == null) {
            throw new NullPointerException("Forgot to initialize SrcStr");
        }
        return instance;
    }

    public Context getContext() {
        return mContext;
    }

    public static void initializeContext(Context context) {
        instance = new SrcStr(context);
        mContext=context;
    }

    private SrcStr(Context context) {
        BASE_URL = context.getResources().getString(R.string.base_url);
        DATE=context.getResources().getString(R.string.date);
    }
}