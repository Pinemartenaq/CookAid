package wtmpd.cookaid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class RecipeSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_search);
    }

    private ArrayList<RecipeItemFragment> getListOfRecipeFragmets(RecipeType type, RecipeCuisine cuisine, List<Ingredient> included, List<Ingredient> excluded) {

        SingletonStorage storage = SingletonStorage.getInstance();
        ArrayList<RecipeItemFragment> fragments = new ArrayList<>();

        for (Recipe recipe : storage.getRecipes())
            if (recipe.getFitness(excluded) == 0 && recipe.getCuisine() == cuisine && recipe.getType() == type) {
                //fragments.add(new RecipeItemFragment(recipe, recipe.getFitness(included))); //ToDo: Fix
            }
        return fragments;
    }
}