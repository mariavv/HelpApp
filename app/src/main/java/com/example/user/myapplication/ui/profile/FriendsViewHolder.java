package com.example.user.myapplication.ui.profile;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.user.myapplication.R;
import com.example.user.myapplication.model.Const;
import com.example.user.myapplication.model.entity.Friend;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.user.myapplication.model.StringUtil.nonNull;

class FriendsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.photoIv)
    ImageView photoIv;

    @BindView(R.id.nameTv)
    TextView nameTv;

    private List<Friend> items;

    FriendsViewHolder(@NonNull View itemView, List<Friend> items) {
        super(itemView);

        this.items = items;

        ButterKnife.bind(this, itemView);
    }

    void bindData(int position) {
        Friend friend = items.get(position);
        nameTv.setText(friend.getName());

        String picPath = nonNull(friend.getPhoto());
        if (picPath.length() > Const.EMPTY_STRING.length()) {
            Glide.with(itemView.getContext())
                    .load(picPath)
                    .apply(RequestOptions.placeholderOf(R.drawable.image_24).centerCrop())
                    .into(photoIv);
        }
    }
}
