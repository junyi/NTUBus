package com.ntubus.ntubus.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ntubus.ntubus.R;
import com.ntubus.ntubus.entity.BusStop;

import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class BusStopListAdapter extends AbstractListAdapter<BusStop, BusStopListAdapter.ViewHolder> implements ItemClickListener {
    private Context mContext;

    public BusStopListAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.bus_stop_list_item, parent, false);
        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BusStop busStop = mData.get(position);
        holder.description.setText(busStop.getString("description"));
        holder.roadName.setText(busStop.getString("road_name"));
    }

    @Override
    public void setItems(List<BusStop> list) {
        Collections.sort(list);
        super.setItems(list);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(mContext, "Position: " + Integer.toString(position), Toast.LENGTH_SHORT).show();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @InjectView(android.R.id.text1)
        TextView description;

        @InjectView(android.R.id.text2)
        TextView roadName;

        @InjectView(R.id.progress)
        BusProgressView progressView;

        ItemClickListener mItemClickListener;

        public ViewHolder(View view, ItemClickListener itemClickListener) {
            super(view);

            ButterKnife.inject(this, view);
            mItemClickListener = itemClickListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mItemClickListener.onItemClick(v, getAdapterPosition());
            if (progressView.isAnimating()) {
                progressView.stopAnimation();
            } else {
                progressView.startAnimation();
            }
        }
    }
}
