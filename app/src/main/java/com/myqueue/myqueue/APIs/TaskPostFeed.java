package com.myqueue.myqueue.APIs;

import android.content.Context;
import android.os.AsyncTask;

import com.myqueue.myqueue.Models.APIBaseResponse;
import com.myqueue.myqueue.Models.APIFeedRequest;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by Penopole on 3/27/2016.
 */
public abstract class TaskPostFeed extends AsyncTask<APIFeedRequest, Void, Boolean> {
    private Context context;
    private RestAdapter restAdapter;
    private APIBaseResponse response;
    private TaskBaseLoading mProgressTBD;
    private String errorMessage;

    public TaskPostFeed(Context context) {
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
    protected Boolean doInBackground(APIFeedRequest... APIFeedRequest) {
        API api = restAdapter.create(API.class);

        APIFeedRequest param = APIFeedRequest[0];
        try {
            response = api.PostFeed(param.getUserid(),param.getUrlPhoto(),param.getPhotoFeed(),param.getDescription());
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
