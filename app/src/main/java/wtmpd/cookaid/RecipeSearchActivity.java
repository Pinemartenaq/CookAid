package wtmpd.cookaid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class RecipeSearchActivity extends AppCompatActivity implements NavigationBar.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_search);
    }

    private ArrayList<RecipeItemFragment> getListOfRecipeFragmets(RecipeType type, RecipeCuisine cuisine, List<Ingredient> included, List<Ingredient> excluded) {

        SingletonStorage storage = SingletonStorage.getInstance(this);
        ArrayList<RecipeItemFragment> fragments = new ArrayList<>();

        for (Recipe recipe : storage.getRecipes())
            if (recipe.getFitness(excluded) == 0 && recipe.getCuisine() == cuisine && recipe.getType() == type) {
                fragments.add(recipe.getFragment(included));
            }
        return fragments;
    }

    @Override
    public void onHomeClick() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onSearchClick() {

    }

    @Override
    public void onAddClick() {
        Intent intent = new Intent(getApplicationContext(), RecipeCreationActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onInstructionClick() {
        Intent intent = new Intent(getApplicationContext(), InstructionActivity.class);
        startActivityForResult(intent, 0);
    }
}
