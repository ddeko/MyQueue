package com.myqueue.myqueue.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myqueue.myqueue.Models.address.APILocationResult;
import com.myqueue.myqueue.R;


/**
 * Created by dedeeko on 8/7/15.
 */
public class SearchLocationAdapter extends BaseAdapter<APILocationResult>{

    public SearchLocationAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_store_element, parent, false);
            holder = new ViewHolder();
            holder.locationName = (TextView) convertView.findViewById(R.id.store_location);
            holder.storeName = (TextView) convertView.findViewById(R.id.store_name);
            holder.addItemText = (TextView) convertView.findViewById(R.id.store_add_store);
            convertView.setTag(holder);
        }  else {
            holder = (ViewHolder) convertView.getTag();
        }

        final APILocationResult items = getItem(position);

        if(items.getStreetName().isEmpty()){
            holder.storeName.setText(items.getFormattedAddress());
        }
        else{
            holder.storeName.setText(items.getStreetName());
        }

        holder.locationName.setText(items.getInfo());
        holder.addItemText.setText("");
        holder.storeName.setVisibility(View.VISIBLE);
        holder.locationName.setVisibility(View.VISIBLE);
        holder.addItemText.setVisibility(View.GONE);

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private static class ViewHolder {
        TextView locationName;
        TextView storeName;
        TextView addItemText;
    }
}