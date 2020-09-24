package com.xcrino.moro.SliderPageActivity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xcrino.moro.Activity.CreateProfileActivity;
import com.xcrino.moro.Activity.DashboardActivity;
import com.xcrino.moro.Activity.LogInActivity;
import com.xcrino.moro.Adapter.IntroScreenViewPagerAdapter;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.AppPreferencesShared;

import java.util.Locale;

public class SliderImageScreenActivity extends AppCompatActivity {

    private AppPreferencesShared mAppPreferences;
    private Context mContext;
    private TextView[] introBullets;
    private int[] introSliderLayouts;
    private ViewPager mSlider_viewpager;
    private LinearLayout mSlider_dots;
    private IntroScreenViewPagerAdapter mIntroScreenViewPagerAdapter;
    private Button mSkip_button, mNext_button;
    public final static String STARTINBACKGROUND = "startinbackground";

    public static void newInstance(Context context, boolean startInBackground) {
        Intent i = new Intent(context, SliderImageScreenActivity.class);  //MyActivity can be anything which you want to start on bootup...
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        i.putExtra(SliderImageScreenActivity.STARTINBACKGROUND, startInBackground);

        context.startActivity(i);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        mAppPreferences = new AppPreferencesShared(mContext);
        Locale myLocale = new Locale(mAppPreferences.getLocale());
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        setContentView(R.layout.activity_slider_image_screen);

        if (!mAppPreferences.getIsFirstTimeLaunch()) {
            applicationStartup();
            finish();
        }


        mSlider_viewpager = findViewById(R.id.slider_viewpager);
        mSlider_dots = findViewById(R.id.slider_dots);
        mSkip_button = findViewById(R.id.skip_button);
        mNext_button = findViewById(R.id.next_button);
        mSlider_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                makeIntroBullets(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        introSliderLayouts = new int[]{R.layout.slider_one, R.layout.slider_two, R.layout.slider_three, R.layout.slider_four, R.layout.slider_five, R.layout.slider_six};
        makeIntroBullets(0);

        mIntroScreenViewPagerAdapter = new IntroScreenViewPagerAdapter(mContext, introSliderLayouts);
        mSlider_viewpager.setAdapter(mIntroScreenViewPagerAdapter);

        mSkip_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applicationStartup();
            }
        });

        mNext_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = getItem(+1);
                if (current < introSliderLayouts.length) {
                    mSlider_viewpager.setCurrentItem(current);
                } else {
                    applicationStartup();
                }
            }
        });
    }

    private void makeIntroBullets(int position) {
        int arraySize = introSliderLayouts.length;
        introBullets = new TextView[arraySize];
        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);
        mSlider_dots.removeAllViews();
        for (int i = 0; i < arraySize; i++) {
            introBullets[i] = new TextView(this);
            introBullets[i].setText(HtmlCompat.fromHtml("   &#9679;   ", HtmlCompat.FROM_HTML_MODE_LEGACY));
            introBullets[i].setTextSize(17F);
            introBullets[i].setTextColor(colorsInactive[position]);
            mSlider_dots.addView(introBullets[i]);
        }

        if (introBullets.length > 0) {
            introBullets[position].setTextColor(colorsActive[position]);
        }

    }

    private int getItem(int i) {
        return mSlider_viewpager.getCurrentItem() + i;

    }

    private void applicationStartup() {
        if (mAppPreferences.getIsCreateProfile() == 2) {
            mAppPreferences.setIsFirstTimeLaunch(false);
            mAppPreferences.setIsCreateProfile(2);
            startActivity(new Intent(mContext, DashboardActivity.class));
            finish();
        } else if (mAppPreferences.getIsCreateProfile() == 1) {
            mAppPreferences.setIsFirstTimeLaunch(false);
            mAppPreferences.setIsCreateProfile(1);
            startActivity(new Intent(mContext, CreateProfileActivity.class));
            finish();
        } else {
            mAppPreferences.setIsFirstTimeLaunch(false);
            mAppPreferences.setIsCreateProfile(0);
            startActivity(new Intent(mContext, LogInActivity.class));
            finish();
        }

    }
}