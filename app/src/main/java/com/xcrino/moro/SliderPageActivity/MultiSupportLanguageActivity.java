package com.xcrino.moro.SliderPageActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xcrino.moro.Adapter.CustomListView;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.AppPreferencesShared;

import java.util.Locale;

public class MultiSupportLanguageActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView toolbar_title;
    private ImageView back_arrow;
    private Context context;

    //    private Locale myLocale;
    String currentLanguage = "en", currentLang;
    private AppPreferencesShared appPreferencesShared;


    ListView listView;
    String[] languagelist = new String[]{"English", "Dutch", "French", "German", "Italian", "Indonesian", "Catalan", "Spanish", "Ukrainian", "Russian"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_support_language);

        context = this;
        appPreferencesShared = new AppPreferencesShared(context);
        currentLanguage = appPreferencesShared.getLocale();

        listView = findViewById(R.id.listOfMultiLanguage);
        CustomListView customListView = new CustomListView(this, languagelist);
        listView.setAdapter(customListView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    setLocale("en");
                }
                else if(position==1){
                    setLocale("nl");
                }
                else if(position==2){
                    setLocale("fr");
                }
                else if(position==3){
                    setLocale("de");
                }
                else if(position==4){
                    setLocale("it");
                }
                else if(position==5){
                    setLocale("in");

                }
                else if(position==6){
                    setLocale("ca");

                }
                else if(position==7){
                    setLocale("es");

                }
                else if(position==8){
                    setLocale("uk");

                }
                else if(position==9){
                    setLocale("ru");

                }
                    Toast.makeText(context, languagelist[position] + "", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        getLayoutInitUi();

        context = this;
        toolbar_title.setText("Multi Language Support");
        back_arrow.setOnClickListener(this);

    }

    private void getLayoutInitUi() {
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        back_arrow = (ImageView) findViewById(R.id.back_arrow);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrow:
                onBackPressed();
                break;

        }
    }

    public void setLocale(String localeName) {
        if (!localeName.equals(currentLanguage)) {
            appPreferencesShared.setLocale(localeName);
            Locale myLocale = new Locale(localeName);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
            finish();

        }
        else {
            Toast.makeText(MultiSupportLanguageActivity.this, "Language already selected!", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        Locale myLocale = new Locale(appPreferencesShared.getLocale());
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

    }
}