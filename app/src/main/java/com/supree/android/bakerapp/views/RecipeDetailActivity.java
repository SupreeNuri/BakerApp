package com.supree.android.bakerapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.supree.android.bakerapp.R;
import com.supree.android.bakerapp.models.Recipe;
import com.supree.android.bakerapp.models.Step;

import static com.supree.android.bakerapp.share.Constants.TITLE;
import static com.supree.android.bakerapp.views.RecipeStepDetailFragment.SELECTED_STEP;

public class RecipeDetailActivity extends BaseActivity implements RecipeDetailFragment.OnRecipeItemSelectedListener {

    private boolean mTwoPane;

    private Recipe selectedRecipe;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        setupToolbar();

        selectedRecipe = getIntent().getParcelableExtra(RecipeDetailFragment.SELECTED_RECIPE);

        if(savedInstanceState == null){

            fragmentManager = getSupportFragmentManager();

            RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, recipeDetailFragment)
                    .commit();

            if(findViewById(R.id.recipe_detail_step_fragment) != null) {
                mTwoPane = true;

                RecipeStepDetailFragment recipeStepDetailFragment = new RecipeStepDetailFragment();
                Bundle stepBundle = new Bundle();
                stepBundle.putParcelable(SELECTED_STEP, selectedRecipe.getSteps().get(0));
                recipeStepDetailFragment.setArguments(stepBundle);

                fragmentManager.beginTransaction()
                        .add(R.id.recipe_detail_step_fragment, recipeStepDetailFragment)
                        .commit();
            }else{
                mTwoPane = false;
            }
        }
    }

    private void setupToolbar(){
        setUpToolbarWithBackPress();

        String tbTitle = getIntent().getStringExtra(TITLE);
        getSupportActionBar().setTitle(tbTitle);
    }

    @Override
    public void OnRecipeItemSelected(int position) {
        Step selectedStep = selectedRecipe.getSteps().get(position);

        if(mTwoPane){
            final RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();

            Bundle stepBundle = new Bundle();
            stepBundle.putParcelable(SELECTED_STEP, selectedStep);
            fragment.setArguments(stepBundle);

            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_detail_step_fragment, fragment)
                    .commit();

        }else{
            Intent intent = new Intent(this, RecipeStepDetailActivity.class);
            intent.putExtra(SELECTED_STEP, selectedStep);
            startActivity(intent);
        }
    }
}
