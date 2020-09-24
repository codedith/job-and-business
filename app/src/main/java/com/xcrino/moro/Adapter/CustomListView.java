package com.xcrino.moro.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xcrino.moro.R;

public class CustomListView extends ArrayAdapter<String> {

    private String[] languagelist;
    private Activity context;

    public CustomListView(Activity context, String[] languagelist) {
        super(context, R.layout.language_list, languagelist);
        this.context = context;
        this.languagelist = languagelist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;

        ViewHolder viewHolder = null;
        if (r == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.language_list, null);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) r.getTag();
        }
        viewHolder.languagelist.setText(languagelist[position]);
        return r;
    }


    class ViewHolder {
        TextView languagelist;

        public ViewHolder(View view) {

            languagelist = view.findViewById(R.id.language_list);

        }
    }


}
