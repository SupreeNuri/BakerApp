package com.supree.android.bakerapp.views;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.supree.android.bakerapp.R;
import com.supree.android.bakerapp.models.Step;

import java.util.ArrayList;

import static com.supree.android.bakerapp.views.RecipeStepDetailFragment.SELECTED_STEP;

public class RecipeStepDetailActivity extends BaseActivity implements RecipeStepDetailFragment.OnSkipPreviousOrNextListener {

    public static final String STEPS = "steps";
    public static final String SELECTED_STEP_POSITION = "selected_step_position";

    private int selectedStepPosition;

    private FragmentManager fragmentManager;

    private ArrayList<Step> steps;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);

        steps = getIntent().getParcelableArrayListExtra(STEPS);
        selectedStepPosition = getIntent().getIntExtra(SELECTED_STEP_POSITION,0);

        setUpToolbarWithBackPress();
        setToolbarTitle();

        fragmentManager = getSupportFragmentManager();

        if(savedInstanceState == null) {
            RecipeStepDetailFragment recipeStepDetailFragment = new RecipeStepDetailFragment();
            Bundle stepBundle = new Bundle();

            stepBundle.putParcelable(SELECTED_STEP, steps.get(selectedStepPosition));
            recipeStepDetailFragment.setArguments(stepBundle);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_detail_step_fragment, recipeStepDetailFragment)
                    .commit();
        }
    }

    @Override
    public void OnSkipPreviousOrNext(boolean isNext) {
        if (isNext && selectedStepPosition < steps.size()-1) {
            selectedStepPosition += 1;
            replaceStepFragment();
        } else if (selectedStepPosition > 0) {
            selectedStepPosition -= 1;
            replaceStepFragment();
        }
    }

    private void replaceStepFragment() {
        Step selectedStep = steps.get(selectedStepPosition);

        final RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();

        Bundle stepBundle = new Bundle();
        stepBundle.putParcelable(SELECTED_STEP, selectedStep);
        fragment.setArguments(stepBundle);

        fragmentManager.beginTransaction()
                .replace(R.id.recipe_detail_step_fragment, fragment)
                .commit();

        setToolbarTitle();
    }

    private void setToolbarTitle(){
        Step selectedStep = steps.get(selectedStepPosition);
        getSupportActionBar().setTitle(selectedStep.getId() + "." + selectedStep.getShortDescription());
    }
}
