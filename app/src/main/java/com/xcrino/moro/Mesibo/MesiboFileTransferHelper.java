package com.xcrino.moro.Mesibo;

import android.os.Bundle;

import com.google.gson.Gson;
import com.mesibo.api.Mesibo;

public class MesiboFileTransferHelper implements Mesibo.FileTransferHandler {

    private static Gson mGson = new Gson();
    private static Mesibo.HttpQueue mQueue = new Mesibo.HttpQueue(4, 0);
    public static class MesiboUrl {
        public String op;
        public String file;
        public String result;
        public String xxx;

        MesiboUrl() {
            result = null;
            op = null;
            xxx = null;
            file = null;
        }
    }

    MesiboFileTransferHelper() {
        	Mesibo.addListener(this);
    }
    
    public boolean Mesibo_onStartUpload(Mesibo.MessageParams params, final Mesibo.FileInfo file) {

        // we don't need to check origin the way we do in download
        if(Mesibo.getNetworkConnectivity() != Mesibo.CONNECTIVITY_WIFI && !file.userInteraction)
            return false;

        // limit simultaneous upload or download
        if(mUploadCounter >= 3 && !file.userInteraction)
            return false;

        final long mid = file.mid;

        Bundle b = new Bundle();
        b.putString("op", "upload");
        b.putString("token", MesiboSampleAPI.getToken());
        b.putLong("mid", mid);
        b.putInt("profile", 0);

        updateUploadCounter(1);
        Mesibo.Http http = new Mesibo.Http();

        http.url = MesiboSampleAPI.getUploadUrl();
        http.postBundle = b;
        http.uploadFile = file.getPath();
        http.uploadFileField = "photo";
        http.other = file;
        file.setFileTransferContext(http);

        http.listener = new Mesibo.HttpListener() {
            @Override
            public boolean Mesibo_onHttpProgress(Mesibo.Http config, int state, int percent) {
                Mesibo.FileInfo f = (Mesibo.FileInfo)config.other;

                if(100 == percent && Mesibo.Http.STATE_DOWNLOAD == state) {
                    String response = config.getDataString();
                    MesiboUrl mesibourl = null;
                    try {
                        mesibourl = mGson.fromJson(response, MesiboUrl.class);
                    } catch (Exception e) {}

                    if(null == mesibourl || null == mesibourl.file) {
                        Mesibo.updateFileTransferProgress(f, -1, Mesibo.FileInfo.STATUS_FAILED);
                        return false;
                    }

                    //TBD, f.setPath if video is re-compressed
                    f.setUrl(mesibourl.file);
                }

                int status = f.getStatus();
                if(100 == percent || status != Mesibo.FileInfo.STATUS_RETRYLATER) {
                    status = Mesibo.FileInfo.STATUS_INPROGRESS;
                    if(percent < 0)
                        status = Mesibo.FileInfo.STATUS_RETRYLATER;
                }

                if(percent < 100 || (100 == percent && Mesibo.Http.STATE_DOWNLOAD == state))
                    Mesibo.updateFileTransferProgress(f, percent, status);

                if((100 == percent && Mesibo.Http.STATE_DOWNLOAD == state) || status != Mesibo.FileInfo.STATUS_INPROGRESS)
                    updateUploadCounter(-1);

                return ((100 == percent && Mesibo.Http.STATE_DOWNLOAD == state) || status != Mesibo.FileInfo.STATUS_RETRYLATER);
            }
        };

        if(null != mQueue)
            mQueue.queue(http);
        else if(http.execute()) {

        }

        return true;

    }

    public boolean Mesibo_onStartDownload(final Mesibo.MessageParams params, final Mesibo.FileInfo file) {

        //TBD, check file type and size to decide automatic download
        if(!MesiboSampleAPI.getMediaAutoDownload() && Mesibo.getNetworkConnectivity() != Mesibo.CONNECTIVITY_WIFI && !file.userInteraction)
            return false;

        // only realtime messages to be downloaded in automatic mode.
        if(Mesibo.ORIGIN_REALTIME != params.origin && !file.userInteraction)
            return false;

        // limit simultaneous upload or download, 1st condition is redundant but for reference only
        if(Mesibo.getNetworkConnectivity() != Mesibo.CONNECTIVITY_WIFI && mDownloadCounter >= 3 && !file.userInteraction)
            return false;

        updateDownloadCounter(1);

        final long mid = file.mid;

        String url = file.getUrl();
        if(!url.toLowerCase().startsWith("http://") && !url.toLowerCase().startsWith("https://")) {
            url = MesiboSampleAPI.getDownloadUrl() + url;
        }

        Mesibo.Http http = new Mesibo.Http();

        http.url = url;
        http.downloadFile = file.getPath();
        http.resume = true;
        http.maxRetries = 10;
        http.other = file;
        file.setFileTransferContext(http);

        http.listener = new Mesibo.HttpListener() {
            @Override
            public boolean Mesibo_onHttpProgress(Mesibo.Http http, int state, int percent) {
                Mesibo.FileInfo f = (Mesibo.FileInfo)http.other;

                int status = Mesibo.FileInfo.STATUS_INPROGRESS;

                //TBD, we can simplify this now, don't need separate handling
                if(Mesibo.FileInfo.SOURCE_PROFILE == f.source) {
                    if(100 == percent) {
                        Mesibo.updateFileTransferProgress(f, percent, Mesibo.FileInfo.STATUS_INPROGRESS);
                    }
                } else {

                    status = f.getStatus();
                    if(100 == percent || status != Mesibo.FileInfo.STATUS_RETRYLATER) {
                        status = Mesibo.FileInfo.STATUS_INPROGRESS;
                        if(percent < 0)
                            status = Mesibo.FileInfo.STATUS_RETRYLATER;
                    }

                    Mesibo.updateFileTransferProgress(f, percent, status);

                }

                if(100 == percent || status != Mesibo.FileInfo.STATUS_INPROGRESS)
                    updateDownloadCounter(-1);

                return (100 == percent  || status != Mesibo.FileInfo.STATUS_RETRYLATER);
            }
        };

        if(null != mQueue)
            mQueue.queue(http);
        else if(http.execute()) {

        }

        return true;
    }

    @Override
    public boolean Mesibo_onStartFileTransfer(Mesibo.FileInfo file) {
        if(Mesibo.FileInfo.MODE_DOWNLOAD == file.mode)
            return Mesibo_onStartDownload(file.getParams(), file);

        return Mesibo_onStartUpload(file.getParams(), file);
    }

    @Override
    public boolean Mesibo_onStopFileTransfer(Mesibo.FileInfo file) {
        Mesibo.Http http = (Mesibo.Http) file.getFileTransferContext();
        if(null != http)
            http.cancel();

        return true;
    }

    private int mDownloadCounter = 0, mUploadCounter = 0;
    public synchronized int updateDownloadCounter(int increment) {
        mDownloadCounter += increment;
        return mDownloadCounter;
    }

    public synchronized int updateUploadCounter(int increment) {
        mUploadCounter += increment;
        return mUploadCounter;
    }
}


