package com.myqueue.myqueue.APIs;

import android.content.Context;
import android.os.AsyncTask;

import com.myqueue.myqueue.Models.APIMaxQueueRequest;
import com.myqueue.myqueue.Models.APIMaxQueueResponse;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by Penopole on 4/26/2016.
 */
public abstract class TaskTotalQueue extends AsyncTask<APIMaxQueueRequest, Void, Boolean> {
    private Context context;
    private RestAdapter restAdapter;
    private APIMaxQueueResponse response;
    private TaskBaseLoading mProgressTBD;
    private String errorMessage;

    public TaskTotalQueue(Context context) {
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
    protected Boolean doInBackground(APIMaxQueueRequest... APIMaxQueueRequest) {
        API api = restAdapter.create(API.class);

        com.myqueue.myqueue.Models.APIMaxQueueRequest param = APIMaxQueueRequest[0];

        try {
            response = api.getTotalQueue(param.getShopid());
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

    public abstract void onResult(APIMaxQueueResponse response, String statusMessage, boolean isSuccess);
}
