package com.gusev.elisium.sberbanktestapplication.presenter;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.gusev.elisium.sberbanktestapplication.data.looper.ImageDownloader;
import com.gusev.elisium.sberbanktestapplication.data.model.BaseXmlModel;
import com.gusev.elisium.sberbanktestapplication.data.model.Channel;
import com.gusev.elisium.sberbanktestapplication.view.menu.HabContract;
import com.gusev.elisium.sberbanktestapplication.view.adapter.ListHolder;

import java.util.List;

/**
 * Created by elisiumGusev
 *
 * @Date 21/07/2017
 * @Author Andrei Gusev
 */

public class HabPresenter implements HabContract.Presenter, LoaderManager.LoaderCallbacks<List<BaseXmlModel>> {
    private static final int LOADER_ID = 1;
    private HabContract.View mView;
    private Loader<List<BaseXmlModel>> mListLoader;
    private LoaderManager loaderManager;
    private List<BaseXmlModel> mAllNewses, mCurrentNews;
    private int pageNum;
    private Channel mChannel;
    private ImageDownloader<ListHolder> mDownloader;
    private boolean flag;

    public HabPresenter(HabContract.View view, @NonNull Loader<List<BaseXmlModel>> loader,
                        @NonNull LoaderManager loaderManager, @NonNull ImageDownloader<ListHolder> downloader) {
        mView = view;
        mListLoader = loader;
        this.loaderManager = loaderManager;
        mDownloader = downloader;
        flag = true;
    }


    @Override
    public void start() {
        mListLoader = loaderManager.initLoader(LOADER_ID, null, this);
        mDownloader.start();
        if (flag) {
            mDownloader.getLooper();
            flag = false;
        }
    }

    @Override
    public void updateItems() {
        mListLoader = loaderManager.initLoader(LOADER_ID, null, this);
    }

    @Override
    public void stop() {
        mView = null;
        mDownloader.clearQueue();
        mDownloader.getLooper().quit();
        mDownloader.quit();
    }

    @Override
    public void getMoreItems() {
        if (pageNum + 10 > mAllNewses.size())
            pageNum = mAllNewses.size();
        else pageNum += 10;
        mCurrentNews.addAll(mAllNewses.subList(pageNum, pageNum));
        for (int i = 0; i < mCurrentNews.size(); i++) {
            mView.updateUIItem(i);
        }
        pageNum += 10;
    }


    @Override
    public Loader<List<BaseXmlModel>> onCreateLoader(int id, Bundle bundle) {
        mView.showProgress(true);
        return mListLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<BaseXmlModel>> loader, List<BaseXmlModel> news) {
        mAllNewses = news;
        pageNum = 10;
        try {
            mChannel = (Channel) news.remove(0);
            mView.updateDrawer(mChannel);
        } catch (ClassCastException e) {
            mChannel = null;
        }

        if (mAllNewses.size() > 10) {
            mCurrentNews = mAllNewses.subList(0, pageNum);
            mView.updateUI(mCurrentNews);
            pageNum += 10;
        } else {
            mView.updateUI(news);
        }
        mView.showProgress(false);

    }

    @Override
    public void onLoaderReset(Loader<List<BaseXmlModel>> loader) {

    }
}
