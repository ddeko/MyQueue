package com.myqueue.myqueue.APIs;

import android.content.Context;
import android.os.AsyncTask;

import com.myqueue.myqueue.Models.APIBaseResponse;
import com.myqueue.myqueue.Models.APIUpdateCoverProfile;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by leowirasanto on 4/27/2016.
 */
public abstract class TaskChangeCoverProfilePhoto extends AsyncTask<APIUpdateCoverProfile, Void, Boolean>{
    private Context context;
    private RestAdapter restAdapter;
    private APIBaseResponse response;
    private TaskBaseLoading mProgressTBD;
    private String errorMessage;

    public TaskChangeCoverProfilePhoto(Context context) {
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
    protected Boolean doInBackground(APIUpdateCoverProfile... APIUpdateCoverProfile) {
        API api = restAdapter.create(API.class);

        com.myqueue.myqueue.Models.APIUpdateCoverProfile param = APIUpdateCoverProfile[0];
        try {
            response = api.ChangeCoverPicture(param.getUserid(),param.getCoverProfile(),param.getUrlCoverProfile());
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
