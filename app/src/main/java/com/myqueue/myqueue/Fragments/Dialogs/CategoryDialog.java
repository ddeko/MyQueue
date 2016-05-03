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

    private ArrayList<ExploreSort> currentSortCat;

    protected Dialog dialog;

    private RelativeLayout filterdialogcategory;

    private TextView doneBtn;

    private ProfileFragment profileFragment;

//    public interface ExploreFilterListener {
//        void onFilterCanceled();
//        void onFilterApplied(int sortIndex, String sortName, String minPrice, String maxPrice);
//    }

//    private ExploreFilterListener listener;

    public CategoryDialog(ProfileFragment Fragment)
    {
//        this.listener = listener;
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

        dialog.show();

        doneBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                if (needToRefresh) {
//
//                    if (listener != null) {
//                        String sortName = getSelectedSort() == null ? "NONE" : getSelectedSort().getFilterName();
//                        listener.onFilterApplied(currentSort, sortName, currentMinPrice, currentMaxPrice);
//                    }
//                    exploreFragment.setSelectedCity(currentSortLoc);
//                    exploreFragment.setSelectedCategory(currentSortCat);
//                    exploreFragment.setMinMaxPrice(currentMinPrice, currentMaxPrice, currentMinIndex, currentMaxIndex);
//                    exploreFragment.updateFilterText();
//                    exploreFragment.fetchData(true);
//                    needToRefresh = false;
//                    dialog.dismiss();
//                } else
                    dialog.dismiss();
            }
        });

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

    }

    private void loadCategory() {
        new TaskGetCategories(getActivity()) {
            @Override
            public void onResult(APICategoriesResponse response, String statusMessage, boolean isSuccess) {
                if (isSuccess) {
                    categorySorts.clear();

                    Toast.makeText(getActivity(), "Cannot load categories", Toast.LENGTH_SHORT).show();
                    // mapping
                    for (int i = 0; i < response.getCategories().size(); i++) {
                        Category category = response.getCategories().get(i);

                        ExploreSort sort = new ExploreSort();
                        sort.setFilterName(category.getCategory_name());
                        sort.setId(category.getCategory_id());

                        Toast.makeText(getActivity(), "Can 11," + category.getCategory_name(), Toast.LENGTH_SHORT).show();

                        categorySorts.add(sort);

                        restoreSelectorsState();
                    }
                    Toast.makeText(getActivity(), response.getCategories().size(), Toast.LENGTH_SHORT).show();

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

        loadCategory();

        catadapter.setData(categorySorts);

        categoryList.setAdapter(catadapter);

        catadapter.notifyDataSetChanged();

        categoryList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        if(adapterView==categoryList)
        {
            ExploreSort selectedSort = categorySorts.get(position);
            selectedSort.setIsChecked(!selectedSort.isChecked());

            ExploreSort temp = new ExploreSort();
            temp.setFilterName(selectedSort.getFilterName());
            temp.setId(selectedSort.getId());

            if(selectedSort.isChecked()) {
                currentSortCat.add(temp);
            }
            else {
                for(int i=0;i<currentSortCat.size();i++) {
                    if(currentSortCat.get(i).getFilterName().equalsIgnoreCase(selectedSort.getFilterName()))
                        currentSortCat.remove(i);

                }
            }

            categorySorts.set(position, selectedSort);

            if(selectedSort.getFilterName().equalsIgnoreCase("ALL"))
            {
                currentSortCat.clear();
                selectedSort.setIsChecked(true);
                // update check marker
                for (int i = 0; i < categorySorts.size(); i++) {
                    if(!categorySorts.get(i).getFilterName().equalsIgnoreCase("ALL"))
                        categorySorts.get(i).setIsChecked(false);
                }
            }

            else
            {
                // update check marker
                for (int i = 0; i < categorySorts.size(); i++) {
                    ExploreSort exploreSort = categorySorts.get(i);
                    if(exploreSort.getFilterName().equalsIgnoreCase("ALL")) {
                        exploreSort.setIsChecked(false);
                    }
                    else if (exploreSort == categorySorts.get(i))
                        continue;

                    exploreSort.setIsChecked(false);
                    categorySorts.set(i, exploreSort);
                }
            }

            catadapter.notifyDataSetChanged();
        }
    }

    private void restoreSelectorsState() {
        ArrayList<ExploreSort> lastCategories = currentSortCat;
        if(lastCategories == null || lastCategories.isEmpty()) {
            categorySorts.get(0).setIsChecked(true);
            return;
        }

        for(ExploreSort category : lastCategories) {
            for(ExploreSort exobject : categorySorts)
                if(category.getId() == exobject.getId())
                    exobject.setIsChecked(true);
        }
    }

    public ArrayList<String> getNameExploreSort(ArrayList<ExploreSort> convertTarget)
    {
        ArrayList<String> result = new ArrayList<>();
        for(int i=0;i<convertTarget.size(); i++)
        {
            result.add(convertTarget.get(i).getFilterName());
        }
        return result;
    }

}
