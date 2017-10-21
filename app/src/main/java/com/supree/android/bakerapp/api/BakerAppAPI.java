package com.supree.android.bakerapp.api;


import com.supree.android.bakerapp.models.Recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BakerAppAPI {
    @GET("baking.json")
    Call<ArrayList<Recipe>> getRecipes();
}
