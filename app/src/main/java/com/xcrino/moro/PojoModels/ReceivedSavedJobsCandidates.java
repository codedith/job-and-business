package com.xcrino.moro.PojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReceivedSavedJobsCandidates {
    @SerializedName("result")
    @Expose
    private Boolean result;
    @SerializedName("data")
    @Expose
    private List<ReceivedSavedJobsCandidatesList> receivedSavedJobsCandidatesLists = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public List<ReceivedSavedJobsCandidatesList> getReceivedSavedJobsCandidatesLists() {
        return receivedSavedJobsCandidatesLists;
    }

    public void setData(List<ReceivedSavedJobsCandidatesList> receivedSavedJobsCandidatesLists) {
        this.receivedSavedJobsCandidatesLists = receivedSavedJobsCandidatesLists;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
