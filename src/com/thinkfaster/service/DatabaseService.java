package com.thinkfaster.service;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by brekol on 19.09.15.
 */
public class DatabaseService {

    private final Activity activity;
    private SharedPreferences sharedPreferences;

    public DatabaseService(Activity activity) {
        this.activity = activity;
        sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
    }

    public void save(String key, List<String> value) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(key, new HashSet<>(value));
        editor.commit();
    }

    public List<String> getList(String key) {
        final Set<String> set = sharedPreferences.getStringSet(key, new HashSet<String>());
        return new ArrayList<>(set);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public void putBoolean(String key, boolean value) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
}
