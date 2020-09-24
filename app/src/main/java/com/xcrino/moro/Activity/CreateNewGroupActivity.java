package com.xcrino.moro.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.AppPreferencesShared;

import java.util.Locale;

public class CreateNewGroupActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView back_arrow;
    private TextView toolbar_title;
    private Button next_button;
//    private RecyclerView recycler_for_contact;
//    private FloatingActionButton add_contact;
//    private TextInputEditText addNewContact;

    private Context mContext;
    private AppPreferencesShared appPreferencesShared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appPreferencesShared= new AppPreferencesShared(this);
        Locale myLocale = new Locale(appPreferencesShared.getLocale());
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        if (appPreferencesShared.getmDayNightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        setContentView(R.layout.activity_create_new_group);

        getUiLayoutDesign();

        mContext = this;
        toolbar_title.setText("New Group");
        back_arrow.setOnClickListener(this);
        next_button.setOnClickListener(this);

//        recycler_for_contact.setLayoutManager(new LinearLayoutManager(mContext));
//        recycler_for_contact.setHasFixedSize(true);
//        recycler_for_contact.setAdapter(new CreateNewGroupAdapter(mContext));

    }

    private void getUiLayoutDesign() {
//        addNewContact = (TextInputEditText) findViewById(R.id.addNewContact);
//        add_contact = (FloatingActionButton) findViewById(R.id.add_contact);
//        recycler_for_contact = (RecyclerView) findViewById(R.id.recycler_for_contact);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        back_arrow = (ImageView) findViewById(R.id.back_arrow);
        next_button = (Button) findViewById(R.id.next_button);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrow:
                onBackPressed();
                break;

            case R.id.next_button:
                startActivity(new Intent(mContext, ChattingActivity.class));
                finish();
                break;
        }
    }
}
