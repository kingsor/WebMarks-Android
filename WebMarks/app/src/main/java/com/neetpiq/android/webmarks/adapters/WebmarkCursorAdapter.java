package com.neetpiq.android.webmarks.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.neetpiq.android.webmarks.DatabaseHelper.WebmarkCursor;
import com.neetpiq.android.webmarks.R;
import com.neetpiq.android.webmarks.models.Webmark;
import com.neetpiq.android.webmarks.utils.UrlUtils;

/**
 * Created by edoardo on 05/09/2015.
 */
public class WebmarkCursorAdapter extends CursorAdapter {

    private WebmarkCursor mWebmarkCursor;

    public WebmarkCursorAdapter(Context context, WebmarkCursor cursor) {
        super(context, cursor, 0);
        mWebmarkCursor = cursor;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_row, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // get the webmark for the current row
        Webmark webmark = mWebmarkCursor.getWebmark();

        TextView tvTitle = (TextView) view.findViewById(R.id.webmark_title);
        TextView tvDomain = (TextView) view.findViewById(R.id.webmark_domain);
        TextView tvCreatedDate = (TextView) view.findViewById(R.id.webmark_date_created);

        tvTitle.setText(webmark.getTitle());
        tvDomain.setText(UrlUtils.getDomainFromUrl(webmark.getUrl()));
        tvCreatedDate.setText(webmark.getInsertDate().toString());
    }
}
