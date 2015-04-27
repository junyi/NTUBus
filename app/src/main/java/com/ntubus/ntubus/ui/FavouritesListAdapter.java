package com.ntubus.ntubus.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ntubus.ntubus.R;
import com.ntubus.ntubus.entity.BusStop;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FavouritesListAdapter extends BaseAdapter {

    private List<BusStop> busStopList = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext;

    public FavouritesListAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        busStopList.add(new BusStop());
        busStopList.add(new BusStop());
        busStopList.add(new BusStop());
        busStopList.add(new BusStop());

    }

    @Override
    public int getCount() {
        return busStopList.size();
    }

    @Override
    public Object getItem(int position) {
        return busStopList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder mViewHolder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.home_list_item_favourite_list_item, parent, false);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        // Uncomment to enable marquee animation
//        mViewHolder.title.setSelected(true);


        return convertView;
    }


    static class ViewHolder {
        @InjectView(android.R.id.text1)
        TextView title;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
