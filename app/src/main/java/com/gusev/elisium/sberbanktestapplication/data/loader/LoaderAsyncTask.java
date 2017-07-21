package com.gusev.elisium.sberbanktestapplication.data.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.gusev.elisium.sberbanktestapplication.data.model.BaseXmlModel;
import com.gusev.elisium.sberbanktestapplication.data.model.Hab;
import com.gusev.elisium.sberbanktestapplication.data.remote.HttpClient;

import java.util.List;

/**
 * Created by elisiumGusev
 *
 * @Date 21/07/2017
 * @Autor Andrei Gusev
 */
public class LoaderAsyncTask extends AsyncTaskLoader<List<BaseXmlModel>> {
    public LoaderAsyncTask(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }


    @Override
    public List<BaseXmlModel> loadInBackground() {
        List<BaseXmlModel> news = HttpClient.getItems();
        return news;
    }

}
