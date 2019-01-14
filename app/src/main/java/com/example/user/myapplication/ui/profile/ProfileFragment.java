package com.example.user.myapplication.ui.profile;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.user.myapplication.R;
import com.example.user.myapplication.model.DateHelper;
import com.example.user.myapplication.model.entity.Profile;
import com.example.user.myapplication.ui.editprofile.EditProfileActivity;
import com.example.user.myapplication.ui.profile.profilegialog.ProfileDialogFragment;

import java.text.MessageFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ProfileFragment extends MvpAppCompatFragment implements ProfileView {

    private View view;

    @BindView(R.id.photoIv)
    ImageView photoIv;

    @BindView(R.id.nameTv)
    TextView nameTv;

    @BindView(R.id.dateBirthTv)
    TextView dateBirthTv;

    @BindView(R.id.profAreaTv)
    TextView profAreaTv;

    @BindView(R.id.notificationsSwitch)
    Switch notificationsSwitch;

    @BindView(R.id.recycler)
    RecyclerView recycler;

    private Unbinder unbinder;

    private FriendsFeedAdapter adapter;

    @InjectPresenter
    ProfilePresenter presenter;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        setHasOptionsMenu(true);

        unbinder = ButterKnife.bind(this, view);

        configureViews();

        Activity activity = getActivity();
        if (activity != null) {
            activity.setTitle("Профиль");
            presenter.onCreateView(getContext(), activity.getAssets());
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.profile, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                editProfile();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showProfileInfo(Profile p) {
        Glide.with(view.getContext())
                .load(p.getPhoto())
                .apply(RequestOptions.placeholderOf(R.drawable.image_24).centerInside())
                .into(photoIv);
        nameTv.setText(MessageFormat.format("{0} {1}", p.getLastName(), p.getName()));
        dateBirthTv.setText(DateHelper.getFormatDate(p.getBirthDate()));
        profAreaTv.setText(p.getCompetenceArea());
        notificationsSwitch.setChecked(p.isGetNotifications());
        adapter.updateItems(p.getFriends());
    }

    private void configureViews() {
        ImageView photoIv = view.findViewById(R.id.photoIv);
        photoIv.setOnClickListener(v -> {
            DialogFragment dialog = new ProfileDialogFragment();
            if (getFragmentManager() != null) {
                dialog.show(getFragmentManager(), "dlg");
            }
        });

        configureRecyclerView();
    }

    private void configureRecyclerView() {
        adapter = new FriendsFeedAdapter();
        recycler.setAdapter(adapter);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        } else {
            recycler.setLayoutManager(new GridLayoutManager(getContext(), 1));
        }
    }

    private void editProfile() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.startActivity(EditProfileActivity.start(getContext()));
        }
    }
}
