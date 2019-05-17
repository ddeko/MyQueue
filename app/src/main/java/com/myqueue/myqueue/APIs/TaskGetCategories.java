package com.myqueue.myqueue.APIs;

import android.content.Context;
import android.os.AsyncTask;

import com.myqueue.myqueue.Models.APICategoriesResponse;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by DedeEko on 4/22/2016.
 */
public abstract class TaskGetCategories extends AsyncTask<Void, Void, Boolean> {
    private RestAdapter restAdapter;
    private APICategoriesResponse response;
    private String errorMessage;
    private Context context;

    public TaskGetCategories(Context context) {
        this.restAdapter = RestClient.setupRestClient();
        this.response = null;
        this.errorMessage = "";
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        API api = restAdapter.create(API.class);

        try {
            response = api.Category();
            return true;
        }
        catch(RetrofitError error) {
            errorMessage = error.getLocalizedMessage();
        }
        catch(Exception error) {
            if(error.getCause() != null)
                errorMessage = error.getCause().getMessage();
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        boolean success = response.getStatus()==1?true:false;

        onResult(response, response.getMsg(), success);
    }

    public abstract void onResult(APICategoriesResponse response, String statusMessage, boolean isSuccess);
}