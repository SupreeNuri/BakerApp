package com.supree.android.bakerapp.views;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.supree.android.bakerapp.R;

import static com.supree.android.bakerapp.share.Constants.TITLE;

public class RecipeDetailActivity extends BaseActivity implements RecipeDetailFragment.OnMediaChangeListener {

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        setupToolbar();

        if(savedInstanceState == null){

            FragmentManager fragmentManager = getSupportFragmentManager();

            RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, recipeDetailFragment)
                    .commit();

            if(findViewById(R.id.recipe_detail_step_fragment) != null) {
                mTwoPane = true;

                RecipeStepDetailFragment recipeStepDetailFragment = new RecipeStepDetailFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.recipe_detail_step_fragment, recipeStepDetailFragment)
                        .commit();
            }else{
                mTwoPane = false;
            }
        }else{

        }
    }

    private void setupToolbar(){
        setUpToolbarWithBackPress();

        String tbTitle = getIntent().getStringExtra(TITLE);
        getSupportActionBar().setTitle(tbTitle);
    }

    @Override
    public void OnMediaChanged(int position) {
        Bundle b = new Bundle();
        b.putInt("clickedIndex", position);

        if(mTwoPane){

        }else{

        }
    }
}
