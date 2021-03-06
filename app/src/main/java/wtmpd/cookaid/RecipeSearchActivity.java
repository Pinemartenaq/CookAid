package wtmpd.cookaid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class RecipeSearchActivity extends AppCompatActivity implements NavigationBar.OnFragmentInteractionListener{

    SingletonStorage storage;

    EditText recipeNameText;
    EditText ingredients;
    Spinner type, cuisine;
    Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_search);

        recipeNameText = (EditText)findViewById(R.id.recipeNameText);
        ingredients = (EditText)findViewById(R.id.SearchBar);
        type = (Spinner)findViewById(R.id.typeList);
        cuisine = (Spinner)findViewById(R.id.cuisineList);
        search = (Button)findViewById(R.id.goButton);

        search.setOnClickListener(btnListener);

        storage = SingletonStorage.getInstance(this);

        updateTypesAndCuisines();

        ArrayList<String> typeStringArray = new ArrayList<>();
        ArrayList<String> cuisineStringArray = new ArrayList<>();

        if(storage.getType("Any") == null){
            typeStringArray.add(0, "Any");
        }

        if(storage.getCuisine("Any") == null) {
            cuisineStringArray.add(0, "Any");
        }

        for(RecipeType t : storage.getTypes())
            typeStringArray.add(t.getName());

        for(RecipeCuisine c : storage.getCuisines())
            cuisineStringArray.add(c.getName());


        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, typeStringArray);
        ArrayAdapter<String> cuisineAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, cuisineStringArray);

        type.setAdapter(typeAdapter);
        cuisine.setAdapter(cuisineAdapter);

    }

    public void searchForRecipe(View view){

        SingletonStorage storage = SingletonStorage.getInstance(this);

        List<Ingredient> include = new LinkedList<> ();
        List<Ingredient> exclude = new LinkedList<>();

        String[] splitString = ingredients.getText().toString().toLowerCase().split("not");
        String includeString = splitString.length > 0 ? splitString[0].trim() : new String();
        String excludeString = splitString.length > 1 ? splitString[1].trim() : new String();

        String recipeNameString = recipeNameText.getText().toString().toLowerCase();
        recipeNameString.trim();
        System.out.println(recipeNameString);

        if(!includeString.equals("")) {
            String[] includeStringArray = includeString.split(",");
            for (String s : includeStringArray)
                include.add(storage.getIngredient(s.trim()));
        }
        else{String[] includeStringArray = {};}

        if(!excludeString.equals("")) {
            String[] excludeStringArray = excludeString.split(",");
            for (String s : excludeStringArray)
                exclude.add(storage.getIngredient(s.trim()));
        }
        else{String[] excludeStringArray = {};}

        getListOfRecipes(recipeNameString, storage.getType(type.getSelectedItem().toString()), storage.getCuisine(cuisine.getSelectedItem().toString()), include, exclude);

        Intent intent = new Intent(getApplicationContext(), ResultsActivity.class);
        startActivityForResult(intent, 0);
    }

    private void getListOfRecipes(String recipeNameString, RecipeType type, RecipeCuisine cuisine, final List<Ingredient> included, List<Ingredient> excluded) {

        SingletonStorage storage = SingletonStorage.getInstance(this);

        //Clean the results
        storage.recipeNames = new LinkedList<String>();
        storage.recipeFitnesses = new LinkedList<String>();

        System.out.println(included.size());

        for (Recipe recipe : storage.getRecipes()) {

            if((!(recipe.getFitness(included) == 0) || included.size() == 0) && (recipe.getFitness(excluded) == 0)
                    && (recipe.getName().toLowerCase().equals(recipeNameString) || recipeNameString.equals(""))){

                if (cuisine.getName().toLowerCase().equals("any") && type.getName().toLowerCase().equals("any")){
                    storage.recipeNames.add(recipe.getName());
                    storage.recipeFitnesses.add(Integer.toString(recipe.getFitness(included)));
                }
                else if(cuisine.getName().toLowerCase().equals("any")){
                    if(recipe.getType().getName().toLowerCase().equals(type.getName().toLowerCase())){
                        storage.recipeNames.add(recipe.getName());
                        storage.recipeFitnesses.add(Integer.toString(recipe.getFitness(included)));
                    }
                }
                else if(type.getName().toLowerCase().equals("any")){
                    if(recipe.getCuisine().getName().toLowerCase().equals(cuisine.getName().toLowerCase())){
                        storage.recipeNames.add(recipe.getName());
                        storage.recipeFitnesses.add(Integer.toString(recipe.getFitness(included)));
                    }
                }
                else{
                    if(recipe.getType().getName().toLowerCase().equals(type.getName().toLowerCase())
                            && recipe.getCuisine().getName().toLowerCase().equals(cuisine.getName().toLowerCase()))
                    {
                        storage.recipeNames.add(recipe.getName());
                        storage.recipeFitnesses.add(Integer.toString(recipe.getFitness(included)));
                    }
                }
            }
        }
    }

    private void updateTypesAndCuisines(){
        List<RecipeType> allTypes = storage.getTypes();
        List<RecipeCuisine> allCuisines = storage.getCuisines();
        boolean[] checkTypes = new boolean[allTypes.size()];
        boolean[] checkCuisines = new boolean[allCuisines.size()];
        Arrays.fill(checkTypes, false);
        Arrays.fill(checkCuisines, false);

        for (Recipe r : storage.getRecipes()){
            if(allTypes.contains(r.getType())){checkTypes[allTypes.indexOf(r.getType())] = true;}
            if(allCuisines.contains(r.getCuisine())){checkCuisines[allCuisines.indexOf(r.getCuisine())] = true;}
        }

        for(int i = 0; i <= allTypes.size() - 1; i++){
            if(checkTypes[i] == false){storage.deleteType(allTypes.get(i));}
        }
        for(int i = 0; i<= allCuisines.size() - 1; i++){
            if(checkCuisines[i] == false){storage.deleteCuisine(allCuisines.get(i));}
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
        storage.storedRecipe = null;
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

            switch (v.getId()){
                case R.id.goButton:
                    searchForRecipe(v);
                    break;
                default:
                    break;
            }
        }

    };
}