package com.gusev.elisium.sberbanktestapplication.view.menu;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.gusev.elisium.sberbanktestapplication.R;
import com.gusev.elisium.sberbanktestapplication.data.model.Channel;
import com.gusev.elisium.sberbanktestapplication.view.base.AbstractFragmentActivity;


/**
 * Created by elisiumGusev
 *
 * @Date 21/07/2017
 * @Autor Andrei Gusev
 */
public class MenuActivity extends AbstractFragmentActivity implements
        NavigationView.OnNavigationItemSelectedListener, MenuFragment.OnFragmentInteractionListener {

    protected DrawerLayout drawer;
    protected AppBarLayout mAppBarLayout;
    protected CollapsingToolbarLayout mToolbarLayout;
    protected Toolbar toolbar;
    protected NavigationView mNavigationView;
    protected TextView tvDrawerTitle;
    protected TextView tvDrawerDesc;


    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, MenuActivity.class);
        return intent;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        mToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);

        mToolbarLayout.setScrimsShown(true);
        changeAppBarHeight((int) getResources().getDimension(R.dimen.tool_bar_height));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);


        tvDrawerTitle = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.drawer_title);
        tvDrawerDesc = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.drawer_desc);


    }

    @Override
    protected Fragment createFragment() {
        return MenuFragment.newInstance();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            exitDialog();
        }
    }

    private void exitDialog() {
        if (getFragmentManager().getBackStackEntryCount() == 0)
            if (!isTaskRoot()) finish();
            else
                new AlertDialog.Builder(MenuActivity.this)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.no, null)
                        .setMessage(R.string.exitDialog)
                        .show();
        else {
            getFragmentManager().popBackStack();
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



    private void changeAppBarHeight(int height) {
        CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams();
        p.height = height;
        mAppBarLayout.setLayoutParams(p);
    }

    @Override
    public void updateDrawer(Channel c) {
        tvDrawerTitle.setText(c.getTitle());
        tvDrawerDesc.setText(c.getDescription());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }



}
