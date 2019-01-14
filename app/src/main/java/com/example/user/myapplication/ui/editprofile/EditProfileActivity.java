package com.example.user.myapplication.ui.editprofile;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.user.myapplication.R;
import com.example.user.myapplication.model.DateHelper;
import com.example.user.myapplication.model.entity.Profile;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditProfileActivity extends MvpAppCompatActivity implements EditProfileView, DatePickerDialog.OnDateSetListener {

    @BindView(R.id.photoIv)
    ImageView photoIv;

    @BindView(R.id.photoShadowIv)
    ImageView photoShadowIv;

    @BindView(R.id.lastNameEd)
    TextView lastNameEd;

    @BindView(R.id.nameEd)
    TextView nameEd;

    @BindView(R.id.birthDateTv)
    TextView birthDateTv;

    @BindView(R.id.profAreaEd)
    TextView profAreaEd;

    @BindView(R.id.passEd)
    EditText passEd;

    @BindView(R.id.passVisibilityIv)
    ImageView passVisibilityIv;

    DatePickerDialog datePickerDialog;

    @InjectPresenter
    EditProfilePresenter presenter;

    public static Intent start(Context context) {
        return new Intent(context, EditProfileActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        photoIv.setClipToOutline(true);
        photoShadowIv.setClipToOutline(true);

        // ?
        ActionBar abar = getSupportActionBar();
        abar.setCustomView(getLayoutInflater().inflate(R.layout.activity_edit_profile, null),
                new ActionBar.LayoutParams(
                        ActionBar.LayoutParams.WRAP_CONTENT,
                        ActionBar.LayoutParams.MATCH_PARENT,
                        Gravity.CENTER
                )
        );

        presenter.onCreate(this, getAssets());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                presenter.onSaveMenuItemSelected(lastNameEd.getText().toString(), nameEd.getText().toString(), birthDateTv.getText().toString(), profAreaEd.getText().toString());
                finish();
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.changePhotoTv)
    void onChangePhotoClick() {
        presenter.onChangePhotoClick();
    }

    @OnClick(R.id.calendarIv)
    void onBirthDateChangeClick() {
        presenter.onBirthDateChangeClick();
    }

    @OnClick(R.id.passVisibilityIv)
    void onPassVisibilityClick() {
        presenter.onPassVisibilityClick(passEd.getInputType());
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        presenter.onBirthDateChange(year, month, dayOfMonth);
    }

    @Override
    public void showSelectedBirthDate(Date date) {
        birthDateTv.setText(DateHelper.getFormatDate(date));
    }

    @Override
    public void showProfileInfo(Profile p) {
        lastNameEd.setText(p.getLastName());
        nameEd.setText(p.getName());
        showSelectedBirthDate(p.getBirthDate());
        profAreaEd.setText(p.getCompetenceArea());
    }

    @Override
    public void initCalendarDialog(int year, int month, int day) {
        datePickerDialog = new DatePickerDialog(this, this, year, month, day);
    }

    @Override
    public void changePassInputType(int imgRes, int inputType) {
        passVisibilityIv.setImageResource(imgRes);
        passEd.setInputType(inputType);
    }

    @Override
    public void showDatePickerDialog() {
        datePickerDialog.show();
    }

    @Override
    public void startActivityForResult(String actionType, int request) {
        Intent intent = new Intent(actionType);
        startActivityForResult(intent, request);
    }

    @Override
    public void startActivityForResult_(Intent intent, int request) {
        startActivityForResult(intent, request);
    }

    @Override
    public void setPhoto(Bitmap photoBitmap) {
        photoIv.setImageBitmap(photoBitmap);
    }

    @Override
    public void setPhoto(Uri outputFileUri) {
        photoIv.setImageURI(outputFileUri);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onActivityResult(requestCode, resultCode, data);
    }
}
