package com.gusev.elisium.sberbanktestapplication.view.adapter;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gusev.elisium.sberbanktestapplication.R;
import com.gusev.elisium.sberbanktestapplication.data.looper.ImageDownloader;
import com.gusev.elisium.sberbanktestapplication.data.model.BaseXmlModel;
import com.gusev.elisium.sberbanktestapplication.data.model.Hab;

import java.util.List;

/**
 * Created by elisiumGusev
 *
 * @Date 21/07/2017
 * @Autor Andrei Gusev
 */
public class ListAdapter extends RecyclerView.Adapter<ListHolder> {
    private List<BaseXmlModel> list;
    private RecyclerView recyclerView;
    private IListItemCallback<Hab> mCallback;
    private OnLoadMoreListener mOnLoadMoreListener;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private ImageDownloader<ListHolder> mDownloader;


    public ListAdapter(List<BaseXmlModel> list, IListItemCallback<Hab> callback, RecyclerView recyclerView
            , ImageDownloader<ListHolder> downloader) {
        this.list = list;
        this.mCallback = callback;
        mOnLoadMoreListener = (OnLoadMoreListener) callback;
        this.recyclerView = recyclerView;
        mDownloader = downloader;

        mDownloader.setDownloaderListener(new ImageDownloader.ImageDownloaderListener<ListHolder>() {
            @Override
            public void onDownload(ListHolder target, Bitmap bitmap) {
                if (((Fragment) mCallback).isAdded()) {
                    Drawable drawable = new BitmapDrawable(((Fragment) mCallback).getResources(), bitmap);
                    target.bindDrawable(drawable);
                }
            }
        });

        onScroll();
    }


    private void onScroll() {
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager
                        .findLastVisibleItemPosition();
                if (!loading
                        && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                    loading = true;
                }
            }
        });
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item, parent, false);

        return new ListHolder(v, mCallback);
    }

    @Override
    public void onBindViewHolder(final ListHolder holder, int position) {
        Hab item = (Hab) list.get(position);
        holder.bind(item);
        if (item.getEnclosure() != null)
            mDownloader.queueImage(holder, item.getEnclosure());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

}
