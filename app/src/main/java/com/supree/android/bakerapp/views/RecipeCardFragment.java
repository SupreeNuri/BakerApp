package com.supree.android.bakerapp.views;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.supree.android.bakerapp.R;
import com.supree.android.bakerapp.adapter.RecipeListAdapter;
import com.supree.android.bakerapp.api.BakerAppAPI;
import com.supree.android.bakerapp.api.BakerAppService;
import com.supree.android.bakerapp.models.Recipe;
import com.supree.android.bakerapp.share.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeCardFragment extends Fragment implements RecipeListAdapter.ListItemClickListener {

    private static final String LOG_TAG = RecipeCardFragment.class.getSimpleName();

    @BindView(R.id.rvRecipe) RecyclerView rvRecipe;
    Unbinder unbinder;

    private List<Recipe> recipeList;
    private RecipeListAdapter mAdapter;

    public RecipeCardFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_card, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        recipeList = new ArrayList<>();

        if (rootView.getTag()!=null && rootView.getTag().equals("phone-land")){
            GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(),4);
            rvRecipe.setLayoutManager(mLayoutManager);
        }
        else {
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            rvRecipe.setLayoutManager(mLayoutManager);
        }

        mAdapter = new RecipeListAdapter(recipeList,this);
        rvRecipe.setAdapter(mAdapter);

        fetchRecipes();

        return rootView;
    }

    private void fetchRecipes() {
        BakerAppAPI bakerAppAPI = BakerAppService.getRecipeAPI();
        Call<ArrayList<Recipe>> call = bakerAppAPI.getRecipes();

        call.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                recipeList.clear();
                recipeList.addAll(response.body());

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                Log.d(LOG_TAG,t.getMessage());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Recipe recipe = recipeList.get(clickedItemIndex);

        Intent intent = new Intent(getContext(), RecipeDetailActivity.class);
        intent.putExtra(Constants.TITLE,recipe.getName());
        intent.putExtra(RecipeDetailFragment.SELECTED_RECIPE,recipe);
        startActivity(intent);
    }
}
