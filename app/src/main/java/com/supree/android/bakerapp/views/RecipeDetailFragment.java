package com.supree.android.bakerapp.views;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.supree.android.bakerapp.R;
import com.supree.android.bakerapp.adapter.RecipeStepAdapter;
import com.supree.android.bakerapp.models.Recipe;
import com.supree.android.bakerapp.models.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RecipeDetailFragment extends Fragment {

    public static final String SELECTED_RECIPE = "selected_recipe";

    @BindView(R.id.tvRecipeIngredients)
    TextView tvRecipeIngredients;
    @BindView(R.id.rvStepList)
    RecyclerView rvStepList;

    private Unbinder unbinder;

    private ArrayList<Step> stepList;
    private RecipeStepAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        stepList = new ArrayList<>();

        Recipe recipe = getActivity().getIntent().getParcelableExtra(RecipeDetailFragment.SELECTED_RECIPE);
        stepList.addAll(recipe.getSteps());

//        if (rootView.getTag()!=null && rootView.getTag().equals("phone-land")){
//            GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(),4);
//            rvRecipe.setLayoutManager(mLayoutManager);
//        }
//        else {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvStepList.setLayoutManager(mLayoutManager);
//        }

        mAdapter = new RecipeStepAdapter(stepList);
        rvStepList.setAdapter(mAdapter);


        return rootView;
    }

    @OnClick(R.id.tvRecipeIngredients)
    public void showIngredientList() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
