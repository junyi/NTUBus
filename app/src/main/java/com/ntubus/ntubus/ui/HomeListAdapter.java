package com.ntubus.ntubus.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.linearlistview.LinearListView;
import com.ntubus.ntubus.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class HomeListAdapter extends AbstractListAdapter<HomeListAdapter.Data, RecyclerView.ViewHolder> implements ItemClickListener {
    enum Type {INFO, FAVOURITE}

    private Context mContext;

    public HomeListAdapter(Context context) {
        mContext = context;
        mData.add(new Data(Type.INFO));
        mData.add(new Data(Type.FAVOURITE));
        mData.add(new Data(Type.INFO));
        mData.add(new Data(Type.FAVOURITE));

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        if (viewType == Type.INFO.ordinal()) {
            View view = inflater.inflate(R.layout.home_list_item_info, parent, false);
            return new InfoViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.home_list_item_favourite, parent, false);
            return new FavouritesViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        Data data = mData.get(position);
        return data.type.ordinal();
    }

    class Data {
        Type type;

        public Data(Type type) {
            this.type = type;
        }
    }

    class InfoViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.button)
        Button okButton;

        public InfoViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);

            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mData.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                }
            });

        }
    }

    class FavouritesViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.list)
        LinearListView list;

        @InjectView(R.id.more)
        AppCompatButton moreButton;

        BaseAdapter adapter;

        public FavouritesViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);

            adapter = new FavouritesListAdapter(mContext);
            list.setAdapter(adapter);

            ColorStateList csl = new ColorStateList(new int[][]{new int[0]}, new int[]{mContext.getResources().getColor(R.color.md_yellow_700)});
            moreButton.setSupportBackgroundTintList(csl);
        }
    }
}
