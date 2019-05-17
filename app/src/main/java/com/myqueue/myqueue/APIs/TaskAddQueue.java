package com.myqueue.myqueue.APIs;

import android.content.Context;
import android.os.AsyncTask;

import com.myqueue.myqueue.Models.APIAddQueueRequest;
import com.myqueue.myqueue.Models.APIAddShopRequest;
import com.myqueue.myqueue.Models.APIBaseResponse;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by leowirasanto on 5/8/2016.
 */
public abstract class TaskAddQueue extends AsyncTask<APIAddQueueRequest, Void, Boolean> {
    private Context context;
    private RestAdapter restAdapter;
    private APIBaseResponse response;
    private TaskBaseLoading mProgressTBD;
    private String errorMessage;

    public TaskAddQueue(Context context) {
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

    protected Boolean doInBackground(APIAddQueueRequest... APIAddQueueRequest) {
        API api = restAdapter.create(API.class);

        APIAddQueueRequest param = APIAddQueueRequest[0];

        try {
            response = api.addQueue(param.getUserid(),param.getShopid());
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

    public abstract void onResult(APIBaseResponse response, String statusMessage, boolean isSuccess);
}
