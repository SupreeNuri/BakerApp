package com.supree.android.bakerapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;

import com.supree.android.bakerapp.R;
import com.supree.android.bakerapp.models.Recipe;
import com.supree.android.bakerapp.models.Step;

import java.util.ArrayList;

import static com.supree.android.bakerapp.share.Constants.TITLE;
import static com.supree.android.bakerapp.views.RecipeStepDetailActivity.STEPS;
import static com.supree.android.bakerapp.views.RecipeStepDetailFragment.SELECTED_STEP;

public class RecipeDetailActivity extends BaseActivity implements RecipeDetailFragment.OnRecipeItemSelectedListener
        , RecipeStepDetailFragment.OnSkipPreviousOrNextListener {

    private static boolean mTwoPane;

    private Recipe selectedRecipe;

    private FragmentManager fragmentManager;

    private int selectedStepPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        setupToolbar();

        selectedRecipe = getIntent().getParcelableExtra(RecipeDetailFragment.SELECTED_RECIPE);

        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {

            RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, recipeDetailFragment)
                    .commit();

            if (findViewById(R.id.recipe_detail_step_fragment) != null) {
                mTwoPane = true;

                RecipeStepDetailFragment recipeStepDetailFragment = new RecipeStepDetailFragment();
                Bundle stepBundle = new Bundle();
                stepBundle.putParcelable(SELECTED_STEP, selectedRecipe.getSteps().get(selectedStepPosition));
                recipeStepDetailFragment.setArguments(stepBundle);

                fragmentManager.beginTransaction()
                        .add(R.id.recipe_detail_step_fragment, recipeStepDetailFragment)
                        .commit();
            } else {
                mTwoPane = false;
            }
        }
    }

    private void setupToolbar() {
        setUpToolbarWithBackPress();

        String tbTitle = getIntent().getStringExtra(TITLE);
        getSupportActionBar().setTitle(tbTitle);
    }

    @Override
    public void OnRecipeItemSelected(int position) {
        selectedStepPosition = position;

        if (mTwoPane) {
            replaceStepFragment();
        } else {
            Intent intent = new Intent(this, RecipeStepDetailActivity.class);
            intent.putExtra(RecipeStepDetailActivity.SELECTED_STEP_POSITION, selectedStepPosition);
            intent.putParcelableArrayListExtra(STEPS, (ArrayList<? extends Parcelable>) selectedRecipe.getSteps());
            startActivity(intent);
        }
    }

    @Override
    public void OnSkipPreviousOrNext(boolean isNext) {
        if (isNext && selectedStepPosition < selectedRecipe.getSteps().size() - 1) {
            selectedStepPosition += 1;
            replaceStepFragment();
        } else if (selectedStepPosition > 0) {
            selectedStepPosition -= 1;
            replaceStepFragment();
        }
    }

    private void replaceStepFragment() {
        Step selectedStep = selectedRecipe.getSteps().get(selectedStepPosition);

        final RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();

        Bundle stepBundle = new Bundle();
        stepBundle.putParcelable(SELECTED_STEP, selectedStep);
        fragment.setArguments(stepBundle);

        fragmentManager.beginTransaction()
                .replace(R.id.recipe_detail_step_fragment, fragment)
                .commit();
    }
}
