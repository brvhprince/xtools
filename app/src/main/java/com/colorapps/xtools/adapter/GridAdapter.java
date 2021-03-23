package com.colorapps.xtools.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.colorapps.xtools.DetailActivity;
import com.colorapps.xtools.R;
import com.colorapps.xtools.model.AllTools;
import com.colorapps.xtools.utils.Tools;

import java.util.ArrayList;
import java.util.List;

/**
 * xtools
 * Created by Pennycodes on 3/16/2021 at 8:30 AM.
 */
public class GridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<AllTools> items = new ArrayList<>();

    private OnLoadMoreListener onLoadMoreListener;

    private Context ctx;

    public GridAdapter(Context context, List<AllTools> items) {
        this.items = items;
        ctx = context;
    }
    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView name;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image);
            name = (TextView) v.findViewById(R.id.name);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_image_sectioned, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final AllTools s = items.get(position);
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            Tools.displayImageOriginal(ctx, view.image, s.image);
            view.name.setText(s.title);
            view.lyt_parent.setOnClickListener(view1 -> {
                Intent intent = new Intent(ctx, DetailActivity.class);
               intent.putExtra("title",s.title);
               intent.putExtra("image",s.image);
               intent.putExtra("description",s.description);
                ctx.startActivity(intent);

            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }


    public interface OnLoadMoreListener {
        void onLoadMore(int current_page);
    }
}
