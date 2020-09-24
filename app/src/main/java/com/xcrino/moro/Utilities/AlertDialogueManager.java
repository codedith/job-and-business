package com.xcrino.moro.Utilities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.Window;
import android.widget.RatingBar;
import android.widget.TextView;

import com.xcrino.moro.BuildConfig;
import com.xcrino.moro.R;

public class AlertDialogueManager {
    private Context mContext;
    private String mMessage;
    private AlertDialog.Builder builder;
    private Dialog dialog;
    private RatingBar ratingBar;
    private TextView descriptionText;

    public void showViewAlertDialog(final Context context, String message) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.rate_us);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#cc66b2ff")));
        ratingBar = dialog.findViewById(R.id.ratingBar);
        descriptionText = dialog.findViewById(R.id.descriptionText);
        descriptionText.setText(message);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                String ratingValue = ratingBar.toString();
                if (ratingValue == "1.0" || ratingValue == "2.0" || ratingValue == "3.0") {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    String subject = "Feedback For MoGo App";
                    String body = "";
                    Uri data = Uri.parse("mailto:?subject="+subject+ "&body="+body);
                    intent.setData(data);
                    context.startActivity(intent);
                }else {
                    Uri data = Uri.parse("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                    Intent gotoMarket = new Intent(Intent.ACTION_VIEW, data);
                    context.startActivity(gotoMarket);

                }
            }
        });
        dialog.show();
    }
}
