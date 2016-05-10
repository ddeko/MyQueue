package com.myqueue.myqueue.APIs;

import android.content.Context;
import android.os.AsyncTask;

import com.myqueue.myqueue.Models.APIExploreResponse;
import com.myqueue.myqueue.Models.APIFilterCategoryRequest;
import com.myqueue.myqueue.Models.APIFilterNameRequest;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by DedeEko on 5/10/2016.
 */
public abstract class TaskFilterName extends AsyncTask<APIFilterNameRequest, Void, Boolean> {

    private Context context;
    private RestAdapter restAdapter;
    private APIExploreResponse response;
    private TaskBaseLoading mProgressTBD;
    private String errorMessage;

    public TaskFilterName(Context context) {
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
    protected Boolean doInBackground(APIFilterNameRequest... APIFilterNameRequest) {
        API api = restAdapter.create(API.class);

        APIFilterNameRequest param = APIFilterNameRequest[0];

        try {
            response = api.filterName(param.getShop_name());
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

    public abstract void onResult(APIExploreResponse response, String statusMessage, boolean isSuccess);

}
