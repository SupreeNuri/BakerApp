package com.supree.android.bakerapp.views;


import android.content.Context;
import android.content.Intent;
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

public class RecipeDetailFragment extends Fragment implements RecipeStepAdapter.ListItemClickListener {

    public static final String SELECTED_RECIPE = "selected_recipe";

    OnMediaChangeListener mCallback;

    public interface OnMediaChangeListener{
        void OnMediaChanged(int position);
    }

    @BindView(R.id.tvRecipeIngredients)
    TextView tvRecipeIngredients;
    @BindView(R.id.rvStepList)
    RecyclerView rvStepList;

    private Unbinder unbinder;

    private ArrayList<Step> stepList;
    private RecipeStepAdapter mAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mCallback = (OnMediaChangeListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()
                    + " must implement OnMediaChangeListener");
        }
    }

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

        mAdapter = new RecipeStepAdapter(stepList,this);
        rvStepList.setAdapter(mAdapter);

        return rootView;
    }

    @OnClick(R.id.tvRecipeIngredients)
    public void showIngredientList() {}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Step selectedStep = stepList.get(clickedItemIndex);

        mCallback.OnMediaChanged(clickedItemIndex);

        Intent intent = new Intent(getContext(), RecipeStepDetailActivity.class);
        intent.putExtra(RecipeStepDetailFragment.SELECTED_STEP,selectedStep);
        startActivity(intent);
    }
}
