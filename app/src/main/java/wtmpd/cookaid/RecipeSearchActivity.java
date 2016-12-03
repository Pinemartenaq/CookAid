package wtmpd.cookaid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.LinkedList;
import java.util.List;
import java.lang.*;

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

        search.setOnClickListener(btnListener);
    }

    public void searchForRecipe(View view){

        SingletonStorage storage = SingletonStorage.getInstance(this);

        List<Ingredient> include = new LinkedList<> ();
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

        getListOfRecipes(storage.getType(type.getSelectedItem().toString()), storage.getCuisine(cuisine.getSelectedItem().toString()), include, exclude);

        Intent intent = new Intent(getApplicationContext(), ResultsActivity.class);
        startActivityForResult(intent, 0);
    }

    private void getListOfRecipes(RecipeType type, RecipeCuisine cuisine, List<Ingredient> included, List<Ingredient> excluded) {

        SingletonStorage storage = SingletonStorage.getInstance(this);

        //Clean the results
        storage.recipeNames = new LinkedList<String>();
        storage.recipeFitnesses = new LinkedList<String>();

        for (Recipe recipe : storage.getRecipes())
            if (recipe.getFitness(excluded) == 0 && recipe.getCuisine() == cuisine && recipe.getType() == type) {
                String[] retRecipe = new String[2];
                storage.recipeNames.add(recipe.getName());
                storage.recipeFitnesses.add(Integer.toString(recipe.getFitness(included)));
            }
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

    private View.OnClickListener btnListener = new View.OnClickListener()
    {

        public void onClick(View v)
        {

            /* switch (v.getId()){
                case R.id.goButton:
                    searchForRecipe(v);
                    break;
                default:
                    break;
            } */
            // Commented out for testing.

            // the following will return to arrays, one for the ingrediants and one for the NOT
            // ingrediants.

            //======================================================================================
            String s = ingredients.getText().toString();
            String[] ingredientList = s.split(",");

            List<String> trueIngredients = new LinkedList<String>();
            List<String> falseIngredients = new LinkedList<String>();

            for(int i = 0;i<ingredientList.length;i++) {
                if(ingredientList[i].startsWith("NOT")){
                        falseIngredients.add(ingredientList[i].substring(4));
                } else if (ingredientList[i].startsWith(" NOT")) {
                    falseIngredients.add(ingredientList[i].substring(5));
                } else {
                    trueIngredients.add(ingredientList[i]);
                }
            }
            //======================================================================================
            // At this point trueIngredients is a list<String> of ingredients that needs to be in
            // the recipe and falseIngredients is a list<String> of ingredients that should not be
            // in the recipe.
        }

    };
}
