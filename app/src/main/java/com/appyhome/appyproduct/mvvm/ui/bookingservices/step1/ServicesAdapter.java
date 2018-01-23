package com.appyhome.appyproduct.mvvm.ui.bookingservices.step1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.appyhome.appyproduct.mvvm.R;

import org.json.JSONObject;

import java.util.ArrayList;

public class ServicesAdapter extends BaseAdapter {
    Context mContext;
    private ArrayList<JSONObject> mListItem;

    public ServicesAdapter(Context mContext, ArrayList<JSONObject> listItem) {
        this.mContext = mContext;
        this.mListItem = listItem;
    }

    public int getCount() {
        return mListItem != null ? mListItem.size() : 0;
    }

    public Object getItem(int i) {
        return mListItem != null && mListItem.size() > 0 ? mListItem.get(i) : null;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View arg1, ViewGroup viewGroup) {
        JSONObject data = mListItem.get(position);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.view_item_service_offer, viewGroup, false);

        TextView tvTitle = (TextView) row.findViewById(R.id.tvTitle);
        TextView tvDescription = (TextView) row.findViewById(R.id.tvDescription);
        TextView tvPrice = (TextView) row.findViewById(R.id.tvPrice);
        try {
            tvTitle.setText(data.getString("name"));
            tvDescription.setText(data.getString("description"));
            tvPrice.setText(data.getString("price"));
        } catch (Exception e) {
        }
        return row;
    }
}