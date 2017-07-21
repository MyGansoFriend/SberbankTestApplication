package com.gusev.elisium.sberbanktestapplication.view.application;

import com.gusev.elisium.sberbanktestapplication.tools.SrcStr;

/**
 * Created by elisiumGusev
 *
 * @Date 21/07/2017
 * @Author Andrei Gusev
 */

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SrcStr.initializeContext(getApplicationContext());
    }
}
