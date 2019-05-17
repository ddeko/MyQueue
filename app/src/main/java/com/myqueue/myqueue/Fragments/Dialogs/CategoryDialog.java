package com.myqueue.myqueue.Fragments.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.myqueue.myqueue.APIs.TaskGetCategories;
import com.myqueue.myqueue.Adapter.CategoryAdapter;
import com.myqueue.myqueue.Fragments.ProfileFragment;
import com.myqueue.myqueue.Models.APICategoriesResponse;
import com.myqueue.myqueue.Models.Category;
import com.myqueue.myqueue.Models.ExploreSort;
import com.myqueue.myqueue.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by DedeEko on 4/17/2016.
 */
public class CategoryDialog extends DialogFragment implements AdapterView.OnItemClickListener{

//    private ExploreFilterListener listener;

    private ListView categoryList;

    private CategoryAdapter catadapter;

    private ArrayList<ExploreSort> categorySorts;

    public String selectCategory;

    public AVLoadingIndicatorView loading;

    protected Dialog dialog;

    private RelativeLayout filterdialogcategory;

    private TextView doneBtn;

    private ProfileFragment profileFragment;


    public CategoryDialog(ProfileFragment Fragment)
    {
        this.profileFragment = Fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = new Dialog(getActivity(),R.style.MaterialDialogSheet);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.view_category_window);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        catadapter = new CategoryAdapter(getActivity());

        categorySorts = new ArrayList<>();

        initView();
        startAnim();
        loadCategory();

        dialog.show();

        return dialog;
    }


    @Override
    public void onResume() {
        super.onResume();
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(android.content.DialogInterface dialog, int keyCode,
                                 android.view.KeyEvent event) {
                if ((keyCode == android.view.KeyEvent.KEYCODE_BACK)) {
                    //This is the filter
                    if (event.getAction() != KeyEvent.ACTION_DOWN)
                        return true;
                    else {
                        dialog.dismiss();
                        return true; // pretend we've processed it
                    }
                } else
                    return false; // pass on to be processed as normal
            }
        });
    }


    private void initView() {
        categoryList = (ListView)dialog.findViewById(R.id.explore_filter_list_category);
        filterdialogcategory = (RelativeLayout)dialog.findViewById(R.id.Filterdialogcategory);
        doneBtn = (TextView)dialog.findViewById(R.id.view_filter_done);
        loading = (AVLoadingIndicatorView) dialog.findViewById(R.id.avloadingIndicatorView);

        doneBtn.setVisibility(View.GONE);

    }

    private void loadCategory() {
        new TaskGetCategories(getActivity()) {
            @Override
            public void onResult(APICategoriesResponse response, String statusMessage, boolean isSuccess) {
                if (isSuccess) {

                    categorySorts.clear();
                    // mapping
                    for (int i = 0; i < response.getCategories().size(); i++) {
                        Category category = response.getCategories().get(i);

                        ExploreSort sort = new ExploreSort();
                        sort.setFilterName(category.getCategory_name());
                        sort.setId(category.getCategory_id());

                        categorySorts.add(sort);

                        catadapter.notifyDataSetChanged();
                        stopAnim();
                    }

                } else {
                    Toast.makeText(getActivity(), "Cannot load categories", Toast.LENGTH_SHORT).show();

                    dismiss();
                }
            }
        }.execute();

    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        catadapter.setData(categorySorts);

        categoryList.setAdapter(catadapter);


        categoryList.setOnItemClickListener(this);
        catadapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        for (int i = 0; i < categorySorts.size(); i++) {
            categorySorts.get(i).setIsChecked(false);
        }
        ExploreSort selectedSort = categorySorts.get(position);
        selectedSort.setIsChecked(!selectedSort.isChecked());

        categorySorts.set(position, selectedSort);

        selectCategory = categorySorts.get(position).getFilterName();

        profileFragment.setSelectedCategory(selectCategory);

        catadapter.notifyDataSetChanged();

        dialog.dismiss();
    }

    void startAnim(){
        loading.setVisibility(View.VISIBLE);
    }

    void stopAnim(){
        loading.setVisibility(View.GONE);
    }
}
