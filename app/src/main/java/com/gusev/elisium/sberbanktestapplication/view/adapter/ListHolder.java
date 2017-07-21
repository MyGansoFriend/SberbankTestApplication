package com.gusev.elisium.sberbanktestapplication.view.adapter;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gusev.elisium.sberbanktestapplication.R;
import com.gusev.elisium.sberbanktestapplication.data.model.Hab;
import com.gusev.elisium.sberbanktestapplication.tools.FormateDateTime;
import com.gusev.elisium.sberbanktestapplication.tools.SrcStr;

import java.util.Date;

/**
 * Created by elisiumGusev
 *
 * @Date 21/07/2017
 * @Autor Andrei Gusev
 */
public class ListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView mTitle, mDesc, mLink, mDate, mCategory, mAuthor;
    private ImageView mIcon;
    private Hab data;
    private IListItemCallback<Hab> mCallback;

    public ListHolder(View itemView, IListItemCallback<Hab> callback) {
        super(itemView);
        this.mCallback = callback;
        mTitle = (TextView) itemView.findViewById(R.id.hab_title);
        mDesc = (TextView) itemView.findViewById(R.id.hab_desc);
        mDate = (TextView) itemView.findViewById(R.id.hab_date);
        mCategory = (TextView) itemView.findViewById(R.id.hab_category);
        mLink = (TextView) itemView.findViewById(R.id.hab_link);
        mAuthor = (TextView) itemView.findViewById(R.id.hab_author);
        mIcon = (ImageView) itemView.findViewById(R.id.hab_icon);
        mLink.setMovementMethod(LinkMovementMethod.getInstance());
        mLink.setOnClickListener(this);
    }

    public void bind(Hab hab) {
        this.data = hab;
        if (data.getEnclosure() == null)
            mIcon.setVisibility(View.GONE);
        mTitle.setText(data.getTitle());
        mAuthor.setText("@" + data.getDc_creator());
        mDesc.setText(data.getDescription());
        mLink.setText(data.getLink());
        mDate.setText(SrcStr.DATE + " " + FormateDateTime.formatDate(new Date(data.getPubDate())));
        StringBuilder category = new StringBuilder();
        for (String s : hab.getCategories()) {
            category.append(s + "*, ");
        }
        mCategory.setText(category.toString());
    }

    public void bindDrawable(Drawable drawable) {
        mIcon.setImageDrawable(drawable);
        mIcon.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        mCallback.onListItemSelected(data);
    }


}
