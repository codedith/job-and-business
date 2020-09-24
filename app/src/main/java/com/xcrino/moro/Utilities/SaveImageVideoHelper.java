package com.xcrino.moro.Utilities;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.lang.ref.WeakReference;

public class SaveImageVideoHelper implements Target {

    private Context mContext;
    private WeakReference<AlertDialog> alertDialogWeakReference;
    private WeakReference<ContentResolver> contentResolverWeakReference;
    private String name, description;

    public SaveImageVideoHelper(Context mContext, AlertDialog alertDialog, ContentResolver contentResolver, String name, String description) {
        this.mContext = mContext;
        this.alertDialogWeakReference = new WeakReference<>(alertDialog);
        this.contentResolverWeakReference = new WeakReference<>(contentResolver);
        this.name = name;
        this.description = description;

    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        ContentResolver cNr = contentResolverWeakReference.get();
        AlertDialog dialog = alertDialogWeakReference.get();
        if (cNr != null) {
            MediaStore.Images.Media.insertImage(cNr, bitmap, name, description);
            //open gallary after download
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            mContext.startActivity(Intent.createChooser(intent, "Select Image"));

        }
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {

    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }
}
