package com.example.user.myapplication.ui.events;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.user.myapplication.R;
import com.example.user.myapplication.model.Const;
import com.example.user.myapplication.model.entity.Event;

import java.util.List;

import static com.example.user.myapplication.model.StringUtil.nonNull;
import static com.example.user.myapplication.model.remote.RestService.DOMAIN;

class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private View view;
    private TextView nameTv;
    private ImageView imageIv;
    private TextView descriptionTv;
    private TextView dateTv;
    private List<Event> items;
    private int[] pictures;
    private Listener listener;

    ViewHolder(View itemView, List<Event> items, int[] pictures, Listener listener) {
        super(itemView);

        this.items = items;
        this.pictures = pictures;
        this.listener = listener;

        view = itemView.findViewById(R.id.card);
        view.setOnClickListener(this);
        nameTv = itemView.findViewById(R.id.event_name);
        imageIv = itemView.findViewById(R.id.event_image);
        descriptionTv = itemView.findViewById(R.id.event_description);
        dateTv = itemView.findViewById(R.id.event_date);
    }

    void bindData(int position) {
        nameTv.setText(nonNull(items.get(position).getName()));

        String picPath = nonNull(items.get(position).getPicture());
        if (picPath.length() > Const.EMPTY_STRING.length()) {
            Glide.with(itemView.getContext())
                    .load(DOMAIN + picPath)
                    .apply(RequestOptions.placeholderOf(R.drawable.image_24).centerCrop())
                    .into(imageIv);
        } else {
            imageIv.setBackgroundResource(pictures[position]);
        }

        descriptionTv.setText(nonNull(items.get(position).getDescription()));

        dateTv.setText(nonNull(items.get(position).getDate()));
    }

    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();
        if (position != RecyclerView.NO_POSITION) {
            listener.itemClick(items.get(position).getId());
        }
    }

    public interface Listener {
        void itemClick(String item);
    }
}
