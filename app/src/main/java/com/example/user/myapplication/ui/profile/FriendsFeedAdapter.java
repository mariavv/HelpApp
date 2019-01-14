package com.example.user.myapplication.ui.profile;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.myapplication.R;
import com.example.user.myapplication.model.entity.Friend;

import java.util.ArrayList;
import java.util.List;

public class FriendsFeedAdapter extends RecyclerView.Adapter<FriendsViewHolder> {

    private List<Friend> items = new ArrayList<>();

    @NonNull
    @Override
    public FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.friend_item, viewGroup, false);

        return new FriendsViewHolder(v, items);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        if (items != null) {
            return items.size();
        }
        return 0;
    }

    void updateItems(List<Friend> entities) {
        if (entities != null) {
            items = entities;
            notifyDataSetChanged();
        }
    }
}
