package com.myqueue.myqueue.APIs;

import android.content.Context;
import android.os.AsyncTask;

import com.myqueue.myqueue.Models.APIAddDummyRequest;
import com.myqueue.myqueue.Models.APIBaseResponse;
import com.myqueue.myqueue.Models.APIDeleteQueueRequest;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by leowirasanto on 5/9/2016.
 */
public abstract class TaskDeleteQueue extends AsyncTask<APIDeleteQueueRequest, Void, Boolean> {
    private Context context;
    private RestAdapter restAdapter;
    private APIBaseResponse response;
    private TaskBaseLoading mProgressTBD;
    private String errorMessage;

    public TaskDeleteQueue(Context context) {
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
    protected Boolean doInBackground(APIDeleteQueueRequest... APIDeleteQueueRequest) {
        API api = restAdapter.create(API.class);

        com.myqueue.myqueue.Models.APIDeleteQueueRequest param = APIDeleteQueueRequest[0];
        try {
            response = api.deleteQueue(param.getUserid());
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