package com.supree.android.bakerapp.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.supree.android.bakerapp.R;

import butterknife.ButterKnife;

public class RecipeCardsActivity extends AppCompatActivity {

    private RecipeCardFragment recipeCardFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_cards);
        ButterKnife.bind(this);

        recipeCardFragment = new RecipeCardFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.fragment_container, recipeCardFragment)
                .commit();
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        return recipeCardFragment.getIdlingResource();
    }

}