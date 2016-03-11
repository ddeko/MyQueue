package com.myqueue.myqueue.Preferences;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.myqueue.myqueue.Activities.LoginActivity;
import com.myqueue.myqueue.Models.User;

import java.util.HashMap;

/**
 * Created by 高橋六羽 on 2016/03/11.
 */
public class SessionManager {
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "MyQueuePref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_USERID = "user_id";

    public static final String KEY_EMAIL = "email";

    public static final String KEY_PASSWORD = "password";

    public static final String KEY_TOKEN = "token";

    public static final String KEY_NAME = "name";

    public static final String KEY_PHONE = "phone";

    public static final String KEY_PROFILEPHOTO = "profilephoto";

    public static final String KEY_COVERPHOTO = "coverphoto";

    public static final String KEY_ISOWNER = "isowner";

    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     */
    public void createLoginSession(User user) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing  in pref
        editor.putString(KEY_USERID, user.getUser_id());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_PASSWORD, user.getPassword());
        editor.putString(KEY_TOKEN, user.getToken());
        editor.putString(KEY_NAME, user.getName());
        editor.putString(KEY_PHONE, user.getPhone());
        editor.putString(KEY_PROFILEPHOTO, user.getProfilephoto());
        editor.putString(KEY_COVERPHOTO, user.getCoverphoto());
        editor.putString(KEY_ISOWNER, user.getIsowner());

        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */
    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }
    }

    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();

        // user data
        user.put(KEY_USERID, pref.getString(KEY_USERID, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));
        user.put(KEY_TOKEN, pref.getString(KEY_TOKEN, null));
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_PHONE, pref.getString(KEY_PHONE, null));
        user.put(KEY_PROFILEPHOTO, pref.getString(KEY_PROFILEPHOTO, null));
        user.put(KEY_COVERPHOTO, pref.getString(KEY_COVERPHOTO, null));
        user.put(KEY_ISOWNER, pref.getString(KEY_ISOWNER, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}
