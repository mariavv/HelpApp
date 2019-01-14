package com.example.user.myapplication.ui.categories;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.user.myapplication.R;
import com.example.user.myapplication.model.Const;
import com.example.user.myapplication.model.entity.Category;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.user.myapplication.model.StringUtil.nonNull;
import static com.example.user.myapplication.model.remote.RestService.DOMAIN;

class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.category)
    TextView nameTv;

    @BindView(R.id.category_image)
    ImageView imageIv;

    private List<Category> items;
    private int[] pictures;
    private Listener listener;

    ViewHolder(View itemView, List<Category> items, int[] pictures, Listener listener) {
        super(itemView);

        this.items = items;
        this.pictures = pictures;
        this.listener = listener;

        ButterKnife.bind(this, itemView);
    }

    void bindData(int position) {
        nameTv.setText(items.get(position).getName());

        String picPath = nonNull(items.get(position).getPicture());
        if (picPath.length() > Const.EMPTY_STRING.length()) {
            Glide.with(itemView.getContext())
                    .load(DOMAIN + picPath)
                    .apply(RequestOptions.placeholderOf(R.drawable.image_24).centerCrop())
                    .into(imageIv);
        } else {
            imageIv.setBackgroundResource(pictures[position]);
        }
    }

    @OnClick(R.id.category_card)
    void onCardClick() {
        int position = getAdapterPosition();
        if (position != RecyclerView.NO_POSITION) {
            listener.itemClick(items.get(position).getId());
        }
    }

    public interface Listener {
        void itemClick(String item);
    }
}
