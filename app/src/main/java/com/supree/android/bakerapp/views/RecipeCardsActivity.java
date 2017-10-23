package com.supree.android.bakerapp.views;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.supree.android.bakerapp.R;

public class RecipeCardsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_cards);

        RecipeCardFragment recipeCardFragment = new RecipeCardFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.fragment_container,recipeCardFragment)
                .commit();
    }
}
