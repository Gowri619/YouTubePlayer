package com.example.kotlinsample.di.fragmentmanager;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;

import java.util.LinkedList;

public class FragmentTagStack implements Parcelable {
    private static final String TAG = "FragmentStack";
    private boolean showLogs;
    private LinkedList<String> tags;
    private String activeTag;

    public FragmentTagStack() {
        tags = new LinkedList<>();
    }

    public FragmentTagStack(Parcel p) {
        this();
        if (tags!=null) {
            p.readStringList(tags);
            activeTag = p.readString();
        }
    }

    public void setShowLogs(boolean showLogs) {
        this.showLogs = showLogs;
    }

    public void push(String tag) {
        tags.add(tag);
        activeTag = tag;
        logStack();
    }

    public String pop() {
        String tag = peek();
        if (!TextUtils.isEmpty(tag)) {
            tags.removeLast();
        }

        activeTag = peek();
        logStack();
        return tag;
    }

    public String peek() {
        if (tags.size() > 0) {
            return tags.peekLast();
        } else {
            return null;
        }
    }

    private void logStack() {
        if (showLogs) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(String.format("Active tag: %s\nStack:\n", activeTag));
            for (String tag : tags) {
                stringBuilder.append(String.format("[ %s ]\n", tag));
            }
            Log.d(TAG, stringBuilder.toString());
        }
    }

    public void popUpAll() {
        tags.clear();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(tags);
        dest.writeString(activeTag);
    }

    //public CREATOR CREATOR;

    public String getActiveTag() {
        return activeTag;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public FragmentTagStack createFromParcel(Parcel in) {
            return new FragmentTagStack(in);
        }

        public FragmentTagStack[] newArray(int size) {
            return new FragmentTagStack[size];
        }
    };
}
