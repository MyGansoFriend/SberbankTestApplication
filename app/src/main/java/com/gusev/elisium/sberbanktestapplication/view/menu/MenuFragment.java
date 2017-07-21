package com.gusev.elisium.sberbanktestapplication.view.menu;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gusev.elisium.sberbanktestapplication.R;
import com.gusev.elisium.sberbanktestapplication.data.loader.LoaderAsyncTask;
import com.gusev.elisium.sberbanktestapplication.data.looper.ImageDownloader;
import com.gusev.elisium.sberbanktestapplication.data.model.BaseXmlModel;
import com.gusev.elisium.sberbanktestapplication.data.model.Channel;
import com.gusev.elisium.sberbanktestapplication.data.model.Hab;
import com.gusev.elisium.sberbanktestapplication.presenter.HabPresenter;
import com.gusev.elisium.sberbanktestapplication.view.adapter.IListItemCallback;
import com.gusev.elisium.sberbanktestapplication.view.adapter.ListAdapter;
import com.gusev.elisium.sberbanktestapplication.view.adapter.ListHolder;
import com.gusev.elisium.sberbanktestapplication.view.adapter.OnLoadMoreListener;

import java.util.List;


/**
 * Created by elisiumGusev
 *
 * @Date 21/07/2017
 * @Autor Andrei Gusev
 */
public class MenuFragment extends Fragment implements IListItemCallback<Hab>, OnLoadMoreListener, HabContract.View {
    private RecyclerView mRecyclerView;
    private ListAdapter mAdapter;
    private ProgressBar mProgressBar;
    private Loader<List<BaseXmlModel>> loader;
    private HabContract.Presenter mPresenter;
    private WebView myWebView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ImageDownloader<ListHolder> mDownloader;
    private OnFragmentInteractionListener callback;

    public interface OnFragmentInteractionListener {
        void updateDrawer(Channel c);
    }

    public static MenuFragment newInstance() {
        Bundle args = new Bundle();
        MenuFragment fragment = new MenuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof MenuActivity) {
            callback = (OnFragmentInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loader = new LoaderAsyncTask(getActivity());


    }

    @Override
    public void onStart() {
        super.onStart();
        Handler responseHandler = new Handler();
        mDownloader = new ImageDownloader<>(responseHandler);
        mPresenter = new HabPresenter(this, loader, getLoaderManager(), mDownloader);
        mPresenter.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.stop();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_menu, container, false);
        mProgressBar = (ProgressBar) v.findViewById(R.id.rvProgressBar);
        myWebView = (WebView) v.findViewById(R.id.webview);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.rvList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mSwipeRefreshLayout = v.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.updateItems();

            }
        });
        return v;
    }

    @Override
    public void updateUI(List<BaseXmlModel> list) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (mAdapter == null) {
            mRecyclerView.setAdapter(new ListAdapter(list, this, mRecyclerView, mDownloader));
        } else mAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateUIItem(int position) {
        if (mAdapter != null) {
            mAdapter.notifyItemInserted(position);
        }
    }

    @Override
    public void updateDrawer(Channel channel) {
        callback.updateDrawer(channel);
    }

    @Override
    public void onListItemSelected(Hab data) {
        myWebView.setVisibility(View.VISIBLE);
        myWebView.loadUrl(data.getLink());
    }

    @Override
    public void showProgress(boolean flag) {
        if (flag) mProgressBar.setVisibility(View.VISIBLE);
        else mProgressBar.setVisibility(View.GONE);
    }


    @Override
    public void onLoadMore() {
        mPresenter.getMoreItems();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

}
