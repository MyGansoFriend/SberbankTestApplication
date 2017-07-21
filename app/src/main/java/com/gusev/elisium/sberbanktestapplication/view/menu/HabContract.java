package com.gusev.elisium.sberbanktestapplication.view.menu;

import com.gusev.elisium.sberbanktestapplication.data.model.BaseXmlModel;
import com.gusev.elisium.sberbanktestapplication.data.model.Channel;
import com.gusev.elisium.sberbanktestapplication.data.model.Hab;
import com.gusev.elisium.sberbanktestapplication.presenter.base.IBasePresenter;
import com.gusev.elisium.sberbanktestapplication.view.base.IBaseView;

import java.util.List;

/**
 * Created by elisiumGusev
 *
 * @Date 21/07/2017
 * @Author Andrei Gusev
 */

public interface HabContract {
    interface View extends IBaseView {
        void showProgress(boolean flag);

        void updateUI(List<BaseXmlModel> data);

        void updateUIItem(int position);
        void updateDrawer(Channel channel);
    }

    interface Presenter extends IBasePresenter {
        void getMoreItems();
        void updateItems();
    }
}
