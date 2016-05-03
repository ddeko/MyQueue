package com.myqueue.myqueue.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.myqueue.myqueue.Models.ExploreSort;
import com.myqueue.myqueue.R;


/**
 * Created by dedeeko on 2016/04/17.
 */
public class CategoryAdapter extends BaseAdapter<ExploreSort> {
    public CategoryAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.list_category_item, parent, false);

            holder = new ViewHolder();
            holder.text = (TextView)convertView.findViewById(R.id.explore_filter_list_text);
            holder.checkMark = (ImageView)convertView.findViewById(R.id.explore_filter_list_check_mark);

            convertView.setTag(holder);
        }
        else
            holder = (ViewHolder) convertView.getTag();

        final ExploreSort filter = getItem(position);

        holder.text.setText(filter.getFilterName());

        if(filter.isChecked()) {
            holder.checkMark.setVisibility(View.VISIBLE);
            holder.checkMark.setImageResource(R.drawable.sort_checkmark);
        }
        else
            holder.checkMark.setVisibility(View.INVISIBLE);

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private static class ViewHolder {
        TextView text;
        ImageView checkMark;
    }
}
