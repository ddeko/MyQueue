package com.myqueue.myqueue.APIs;

import android.content.Context;
import android.os.AsyncTask;

import com.myqueue.myqueue.Models.APIBaseResponse;
import com.myqueue.myqueue.Models.APIEditTokenRequest;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by 高橋六羽 on 4/14/2016.
 */
public abstract class TaskEditToken extends AsyncTask<APIEditTokenRequest, Void, Boolean> {
    private Context context;
    private RestAdapter restAdapter;
    private APIBaseResponse response;
    private TaskBaseLoading mProgressTBD;
    private String errorMessage;

    public TaskEditToken(Context context) {
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
    protected Boolean doInBackground(APIEditTokenRequest... APIEditTokenRequest) {
        API api = restAdapter.create(API.class);

        APIEditTokenRequest param = APIEditTokenRequest[0];

        try {
            response = api.EditToken(param.getUserid(), param.getToken());
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

