package com.example.user.myapplication.ui.events;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.myapplication.R;
import com.example.user.myapplication.model.entity.Event;

import java.util.ArrayList;
import java.util.List;

class FeedAdapter extends RecyclerView.Adapter<ViewHolder>
        implements ViewHolder.Listener {

    private FeedAdapter.OnItemClickListener onItemClickListener;

    private List<Event> items = new ArrayList<>();
    private int[] pictures;

    FeedAdapter(FeedAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_item, parent, false);

        return new ViewHolder(v, items, pictures, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        if (items != null) {
            return items.size();
        }
        return 0;
    }

    @Override
    public void itemClick(String itemId) {
        onItemClickListener.onItemClick(itemId);
    }

    void updateItems(List<Event> entities, int[] pics) {
        if (entities != null) {
            items = entities;
            pictures = pics;
            notifyDataSetChanged();
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String itemId);
    }
}