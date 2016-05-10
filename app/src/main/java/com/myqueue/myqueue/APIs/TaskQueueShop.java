package com.myqueue.myqueue.APIs;

import android.content.Context;
import android.os.AsyncTask;

import com.myqueue.myqueue.Models.APIQueueShopRequest;
import com.myqueue.myqueue.Models.APIQueueShopResponse;
import com.myqueue.myqueue.Models.APIQueueUserRequest;
import com.myqueue.myqueue.Models.APIQueueUserResponse;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by leowirasanto on 5/9/2016.
 */
public abstract class TaskQueueShop extends AsyncTask<APIQueueShopRequest, Void, Boolean> {
    private Context context;
    private RestAdapter restAdapter;
    private APIQueueShopResponse response;
    private TaskBaseLoading mProgressTBD;
    private String errorMessage;

    public TaskQueueShop(Context context) {
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
    protected Boolean doInBackground(APIQueueShopRequest... APIQueueShopRequest) {
        API api = restAdapter.create(API.class);

        com.myqueue.myqueue.Models.APIQueueShopRequest param = APIQueueShopRequest[0];

        try {
            response = api.getQueueShop(param.getShopid());
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

    public abstract void onResult(APIQueueShopResponse response, String statusMessage, boolean isSuccess);
}
