package com.myqueue.myqueue.APIs;

import android.content.Context;
import android.os.AsyncTask;

import com.myqueue.myqueue.Models.APIFeedResponse;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by 高橋六羽 on 3/24/2016.
 */
public abstract class TaskFeed extends AsyncTask<Void, Void, Boolean> {
    private Context context;
    private RestAdapter restAdapter;
    private APIFeedResponse response;
    private TaskBaseLoading mProgressTBD;
    private String errorMessage;

    public TaskFeed(Context context) {
        this.restAdapter = RestClient.setupRestClient();
        this.response = null;
        this.errorMessage = "";
        this.context = context;
        mProgressTBD = new TaskBaseLoading(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressTBD.show();
    }

    @Override
    protected Boolean doInBackground(Void... Void) {
        API api = restAdapter.create(API.class);

        try {
            response = api.Feed();
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
        if (mProgressTBD.isShowing())
            mProgressTBD.dismiss();

        boolean success = response.getStatus()==1?true:false;

        onResult(response, response.getMsg(), success);
    }

    public abstract void onResult(APIFeedResponse response, String statusMessage, boolean isSuccess);
}
