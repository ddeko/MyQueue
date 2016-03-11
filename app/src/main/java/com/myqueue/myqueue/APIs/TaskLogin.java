package com.myqueue.myqueue.APIs;

import android.content.Context;
import android.os.AsyncTask;

import com.myqueue.myqueue.Models.APILoginRequest;
import com.myqueue.myqueue.Models.APILoginResponse;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by 高橋六羽 on 2016/03/11.
 */
public abstract class TaskLogin extends AsyncTask<APILoginRequest, Void, Boolean> {
    private Context context;
    private RestAdapter restAdapter;
    private APILoginResponse response;
    private TaskBaseLoading mProgressTBD;
    private String errorMessage;

    public TaskLogin(Context context) {
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
    protected Boolean doInBackground(APILoginRequest... APILoginRequest) {
        API api = restAdapter.create(API.class);

        APILoginRequest param = APILoginRequest[0];

        try {
            response = api.Login(param.getEmail(), param.getPassword());
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

    public abstract void onResult(APILoginResponse response, String statusMessage, boolean isSuccess);
}
