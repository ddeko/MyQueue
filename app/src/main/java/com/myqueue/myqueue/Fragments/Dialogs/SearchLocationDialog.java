package com.myqueue.myqueue.Fragments.Dialogs;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.myqueue.myqueue.APIs.TaskSearchLocationByAddress;
import com.myqueue.myqueue.Adapter.SearchLocationAdapter;
import com.myqueue.myqueue.Models.address.APILocationResult;
import com.myqueue.myqueue.R;

import java.util.List;
import java.util.Vector;

/**
 * Created by dedeeko on 3/29/16.
 */
public abstract class SearchLocationDialog extends DialogFragment {

    private TextView xButton;
    protected Dialog dialog;
    protected ListView addressList;
    protected SearchLocationAdapter adapter;

    protected Vector<APILocationResult> data;
    protected LinearLayout loadingLayout;
    protected ProgressBar loadingBar;
    protected TextView loadingText;
    protected String search;
    protected TaskSearchLocationByAddress searchTask;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        dialog = new Dialog(getActivity());
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_search_address);
        dialog.show();


        initViews();
        data = new Vector<APILocationResult>();

        adapter = new SearchLocationAdapter(getActivity());
        adapter.setData(data);
        addressList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        addressList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                               @Override
                                               public void onItemClick(AdapterView<?> parent, View view,
                                                                       int position, long id) {
                                                   APILocationResult selectedData = data.get(position);
                                                   setLocation(selectedData);
                                                   dismiss();
                                               }
                                           }
        );



        xButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dismiss();
            }
        });

        addressList.setVisibility(View.INVISIBLE);
        loadingLayout.setVisibility(View.VISIBLE);


        searchTask = new TaskSearchLocationByAddress(getActivity()) {
            @Override
            protected void onSearchSuccess(List<APILocationResult> result) {
                data.clear();
                data.addAll(result);
                adapter.notifyDataSetChanged();

                if(data.isEmpty()){
                    loadingBar.setVisibility(View.GONE);
                    loadingText.setText("No Data Found");
                    loadingText.setTextColor(getResources().getColor(R.color.white));
                    addressList.setVisibility(View.INVISIBLE);
                }
                else{
                    addressList.setVisibility(View.VISIBLE);
                    loadingLayout.setVisibility(View.GONE);
                }
            }

            @Override
            protected void onSearchFailed(String message) {
                loadingBar.setVisibility(View.GONE);
                loadingText.setText(message);
                loadingText.setTextColor(getActivity().getResources().getColor(R.color.white));
                addressList.setVisibility(View.INVISIBLE);
            }
        };

        searchTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, search);

        return dialog;
    }

    public void setData(String searchString){
        search = searchString;
    }

    @Override
    public void onSaveInstanceState(Bundle arg0) {

    }

    private void initViews(){
        addressList  = (ListView) dialog.findViewById(R.id.select_location_list);
        xButton = (TextView)dialog.findViewById(R.id.select_location_close_btn);
        loadingLayout = (LinearLayout)dialog.findViewById(R.id.loading_view_layout);
        loadingBar = (ProgressBar)dialog.findViewById(R.id.select_location_progress_bar);
        loadingText = (TextView)dialog.findViewById(R.id.info_text);
    }

    protected abstract void setLocation(APILocationResult model);

}