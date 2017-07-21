package com.gusev.elisium.sberbanktestapplication.view.base;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import com.gusev.elisium.sberbanktestapplication.R;


/**
 * Created by elisiumGusev
 *
 * @Date 21/07/2017
 * @Autor Andrei Gusev
 */
public abstract class AbstractFragmentActivity extends AppCompatActivity {
    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null)
            fm.beginTransaction()
                    .replace(R.id.fragment_container, createFragment())
                    .commit();
    }

    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.activity_menu;
    }
}
