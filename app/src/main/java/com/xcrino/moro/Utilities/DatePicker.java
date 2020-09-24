package com.xcrino.moro.Utilities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePicker extends DialogFragment {

    private Context context;
    private TextView textView;
    private Calendar calendar;
    private int mYear, mMonth, mDay;

    public DatePicker(Context context, final TextView textView) {
        this.context = context;
        this.textView = textView;
        calendar = Calendar.getInstance();

        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        updateDateInView(textView, year, month, dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void updateDateInView(TextView textView, int year, int month, int dayOfMonth) {
        String day = Integer.toString(dayOfMonth);
        String months = Integer.toString(month);
        if (day.length() == 1 && months.length() != 1) {
            textView.setText("0"+dayOfMonth + "-" + (month + 1) + "-" + year);
        }
        else if (months.length() == 1 && day.length() != 1) {
            textView.setText(dayOfMonth + "-0" + (month + 1) + "-" + year);
        }
        else if (day.length() == 1 && months.length() == 1) {
            textView.setText("0" + dayOfMonth + "-0" + (month + 1) + "-" + year);
        }
        else {
            textView.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
        }
    }
}
