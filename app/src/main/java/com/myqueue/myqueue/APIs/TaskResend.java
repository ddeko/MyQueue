package com.myqueue.myqueue.APIs;

import android.content.Context;
import android.os.AsyncTask;

import com.myqueue.myqueue.Models.APIBaseResponse;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by 高橋六羽 on 2016/03/22.
 */
public abstract class TaskResend extends AsyncTask<String, Void, Boolean> {
    private Context context;
    private RestAdapter restAdapter;
    private APIBaseResponse response;
    private TaskBaseLoading mProgressTBD;
    private String errorMessage;

    public TaskResend(Context context) {
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
    protected Boolean doInBackground(String... String) {
        API api = restAdapter.create(API.class);

        String param = String[0];

        try {
            response = api.Resend(param);
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

        boolean success = response.getStatus()==1;

        onResult(response, response.getMsg(), success);
    }

    public abstract void onResult(APIBaseResponse response, String statusMessage, boolean isSuccess);
}
