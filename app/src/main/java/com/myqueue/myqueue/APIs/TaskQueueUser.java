package com.myqueue.myqueue.APIs;

import android.content.Context;
import android.os.AsyncTask;

import com.myqueue.myqueue.Models.APIMaxQueueRequest;
import com.myqueue.myqueue.Models.APIMaxQueueResponse;
import com.myqueue.myqueue.Models.APIQueueUserRequest;
import com.myqueue.myqueue.Models.APIQueueUserResponse;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by leowirasanto on 5/8/2016.
 */
public abstract class TaskQueueUser extends AsyncTask<APIQueueUserRequest, Void, Boolean> {
    private Context context;
    private RestAdapter restAdapter;
    private APIQueueUserResponse response;
    private TaskBaseLoading mProgressTBD;
    private String errorMessage;

    public TaskQueueUser(Context context) {
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
    protected Boolean doInBackground(APIQueueUserRequest... APIQueueUserRequest) {
        API api = restAdapter.create(API.class);

        com.myqueue.myqueue.Models.APIQueueUserRequest param = APIQueueUserRequest[0];

        try {
            response = api.getQueueUser(param.getUser());
            return true;
        } catch (RetrofitError error) {
            errorMessage = error.getLocalizedMessage();
        } catch (Exception error) {
            if (error.getCause() != null)
                errorMessage = error.getCause().getMessage();
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (mProgressTBD.isShowing())
            mProgressTBD.dismiss();

        boolean success = response.getStatus() == 1 ? true : false;

        onResult(response, response.getMsg(), success);
    }

    public abstract void onResult(APIQueueUserResponse response, String statusMessage, boolean isSuccess);
}
