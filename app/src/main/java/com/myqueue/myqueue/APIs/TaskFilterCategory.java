package com.myqueue.myqueue.APIs;

import android.content.Context;
import android.os.AsyncTask;

import com.myqueue.myqueue.Models.APIExploreResponse;
import com.myqueue.myqueue.Models.APIFilterCategoryRequest;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by DedeEko on 5/10/2016.
 */
public abstract class TaskFilterCategory extends AsyncTask<APIFilterCategoryRequest, Void, Boolean> {

    private Context context;
    private RestAdapter restAdapter;
    private APIExploreResponse response;
    private String errorMessage;

    public TaskFilterCategory(Context context) {
        this.restAdapter = RestClient.setupRestClient();
        this.response = null;
        this.errorMessage = "";
        this.context = context;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(APIFilterCategoryRequest... APIFilterCategoryRequest) {
        API api = restAdapter.create(API.class);

        APIFilterCategoryRequest param = APIFilterCategoryRequest[0];

        try {
            response = api.filterCategory(param.getCategory_name());
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

        boolean success = response.getStatus()==1?true:false;

        onResult(response, response.getMsg(), success);
    }

    public abstract void onResult(APIExploreResponse response, String statusMessage, boolean isSuccess);

}
