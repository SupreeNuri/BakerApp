package com.supree.android.bakerapp.views;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.supree.android.bakerapp.R;

import static com.supree.android.bakerapp.views.RecipeStepDetailFragment.SELECTED_STEP;

public class RecipeStepDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);

        if(savedInstanceState == null) {
            RecipeStepDetailFragment recipeStepDetailFragment = new RecipeStepDetailFragment();
            Bundle stepBundle = new Bundle();
            stepBundle.putParcelable(SELECTED_STEP, getIntent().getParcelableExtra(SELECTED_STEP));
            recipeStepDetailFragment.setArguments(stepBundle);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, recipeStepDetailFragment)
                    .commit();
        } else {

        }
    }
}
