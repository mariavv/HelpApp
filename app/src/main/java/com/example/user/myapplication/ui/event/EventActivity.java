package com.example.user.myapplication.ui.event;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.user.myapplication.R;
import com.example.user.myapplication.model.entity.Event;
import com.example.user.myapplication.model.repository.EventsRepo;

import static com.example.user.myapplication.model.StringUtil.nonNull;

public class EventActivity extends AppCompatActivity {

    private static final String ARG_CATEGORY_ID = "category_id";
    private static final String ARG_EVENT_ID = "event_id";

    public static Intent start(Context context, String categoryId, String eventId) {
        Intent intent = new Intent(context, EventActivity.class);

        Bundle arguments = new Bundle();
        arguments.putString(ARG_CATEGORY_ID, categoryId);
        arguments.putString(ARG_EVENT_ID, eventId);
        intent.putExtras(arguments);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        configureViews();
    }

    private void configureViews() {
        TextView nameTv = findViewById(R.id.name);
        TextView dateTv = findViewById(R.id.date);
        TextView fullDescriptionTv = findViewById(R.id.full_description);

        String categoryId = getIntent().getStringExtra(ARG_CATEGORY_ID);
        String eventId = getIntent().getStringExtra(ARG_EVENT_ID);
        Event event = new EventsRepo(categoryId, this, getAssets()).getEvent(eventId);

        if (event != null) {
            nameTv.setText(nonNull(event.getName()));
            dateTv.setText(nonNull(event.getDate()));
            fullDescriptionTv.setText(nonNull(event.getFullDescription()));
        }
    }
}
