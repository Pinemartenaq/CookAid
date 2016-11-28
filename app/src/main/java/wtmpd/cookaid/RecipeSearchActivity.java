package wtmpd.cookaid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RecipeSearchActivity extends AppCompatActivity implements NavigationBar.OnFragmentInteractionListener{

    EditText ingredients;
    Spinner type, cuisine;
    Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_search);

        ingredients = (EditText)findViewById(R.id.SearchBar);
        type = (Spinner)findViewById(R.id.typeList);
        cuisine = (Spinner)findViewById(R.id.cuisineList);
        search = (Button)findViewById(R.id.goButton);

        //ToDo: Define on click listener
    }

    public void searchForRecipe(View view){

        SingletonStorage storage = SingletonStorage.getInstance(this);

        List<Ingredient> include = new LinkedList<>();
        List<Ingredient> exclude = new LinkedList<>();

        String[] splitString = ingredients.getText().toString().toLowerCase().split("not");
        String includeString = splitString.length > 0 ? splitString[0].trim() : new String();
        String excludeString = splitString.length > 1 ? splitString[1].trim() : new String();

        String[] includeStringArray = includeString.split(",");
        for(String s : includeStringArray)
            include.add(storage.getIngredient(s.trim()));

        String[] excludeStringArray = excludeString.split(",");
        for(String s : excludeStringArray)
            exclude.add(storage.getIngredient(s.trim()));

        List<RecipeItemFragment> fragments = getListOfRecipeFragmets(storage.getType(type.getSelectedItem().toString()), storage.getCuisine(cuisine.getSelectedItem().toString()), include, exclude);

        Log.d("FLAG", "Search");

        //ToDo: Switch views and pass fragments
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
        Intent intent = new Intent(getApplicationContext(), RecipeEditActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onInstructionClick() {
        Intent intent = new Intent(getApplicationContext(), InstructionActivity.class);
        startActivityForResult(intent, 0);
    }
}
