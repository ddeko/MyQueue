package com.myqueue.myqueue.APIs;

import android.content.Context;
import android.os.AsyncTask;

import com.myqueue.myqueue.Models.APIBaseResponse;
import com.myqueue.myqueue.Models.APIChangeStatusShopRequest;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by Penopole on 4/25/2016.
 */
public abstract class TaskChangeStatusShop extends AsyncTask<APIChangeStatusShopRequest, Void, Boolean> {
    private Context context;
    private RestAdapter restAdapter;
    private APIBaseResponse response;
    private TaskBaseLoading mProgressTBD;
    private String errorMessage;

    public TaskChangeStatusShop(Context context) {
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
    protected Boolean doInBackground(APIChangeStatusShopRequest... APIChangeStatusShopRequest) {
        API api = restAdapter.create(API.class);

        com.myqueue.myqueue.Models.APIChangeStatusShopRequest param = APIChangeStatusShopRequest[0];

        try {
            response = api.ChangeStatusShop(param.getUserid(), param.getIsfull());
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
